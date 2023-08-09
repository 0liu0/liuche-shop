package com.liuche.order.service.impl;

import java.util.Date;

import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.constants.OrderConstants;
import com.liuche.common.dto.LockCouponRecordDTO;
import com.liuche.common.dto.LockProductDTO;
import com.liuche.common.dto.OrderItemDTO;
import com.liuche.common.enums.CouponStateEnum;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.enums.ProductOrderStateEnum;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.model.OrderMessage;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import com.liuche.order.component.PayFactory;
import com.liuche.order.config.MQConfig;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.feign.AddressFeign;
import com.liuche.order.feign.CouponFeign;
import com.liuche.order.feign.ProductFeign;
import com.liuche.order.mapper.ProductOrderItemMapper;
import com.liuche.order.mapper.ProductOrderMapper;
import com.liuche.order.model.ProductOrder;
import com.liuche.order.model.ProductOrderItem;
import com.liuche.order.service.ProductOrderItemService;
import com.liuche.order.service.ProductOrderService;
import com.liuche.order.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 70671
 * @description 针对表【product_order】的数据库操作Service实现
 * @createDate 2023-08-03 11:43:32
 */
@Service
@Slf4j
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder>
        implements ProductOrderService {
    @Resource
    private AddressFeign addressFeign;
    @Resource
    private CouponFeign couponFeign;
    @Resource
    private ProductFeign productFeign;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MQConfig mqConfig;
    @Resource
    private PayFactory payFactory;
    @Resource
    private ProductOrderItemMapper productOrderItemMapper;
    @Resource
    private ProductOrderItemService productOrderItemService;

    /**
     * service编写伪代码
     * 防重提交
     * 用户微服务-确认收货地址
     * 商品微服务-获取最新购物项和价格
     * 订单验价
     * 优惠券微服务-获取优惠券
     * 验证价格
     * 锁定优惠券
     * 锁定商品库存
     * 创建订单对象
     * 创建子订单对象
     * 发送延迟消息-用于自动关单
     * 创建支付信息-对接三方支付
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public JsonData confirmOrder(OrderDTO dto) {
        log.info("前端传来的信息：" + dto);
        // 创建订单id
        String orderOutTradeNo = CommonUtil.getStringNumRandom(32);
        // 得到用户收货地址地址
        JsonData address = addressFeign.getOneAddress(dto.getAddressId());
        if (address.getCode() != 0) throw new BusinessException(ExceptionCode.USER_ADDRESS_ERROR);
        // 得到用户的收货地址
        AddressInfoResp addressInfo = address.getData(new TypeReference<>() {
        });
        // 得到对应的优惠券
        ArrayList<CouponRecordVO> recordList = new ArrayList<>();
        // 批量获取 todo
        for (Long id : dto.getCouponRecordIds()) {
            JsonData jsonData = couponFeign.findUserCouponRecordById(id);
            if (jsonData.getCode() != 0) throw new BusinessException(ExceptionCode.COUPON_NO_EXITS);
            CouponRecordVO recordVO = jsonData.getData(new TypeReference<>() {
            });
            recordList.add(recordVO);
        }
        this.checkRecord(recordList);
        // 从用户的购物车根据id得到要买的商品
        List<Long> idList = Arrays.asList(dto.getProductIds());
        JsonData cartProduct = productFeign.getUserCartProductByIds(idList);
        if (cartProduct.getCode() != 0) throw new BusinessException(ExceptionCode.PARAMS_ERROR, "商品不存在或已下架");
        // 验证商品是否可以消费
        CartVO cartMini = cartProduct.getData(new TypeReference<>() {
        }); // 用户购买的商品
        this.checkCartMini(cartMini, dto, recordList); // 经过了这层说明该订单准确无误
        // 生成订单
        ProductOrder order = new ProductOrder();
        order.setOutTradeNo(orderOutTradeNo);
        order.setState(ProductOrderStateEnum.NEW.name());
        order.setTotalAmount(dto.getTotalAmount());
        order.setPayAmount(dto.getRealPayAmount());
        order.setPayType(dto.getPayType());
        order.setNickname("刘彻");
        order.setHeadImg("https://www.baidu.com");
        order.setUserId(RequestContext.getUserId());
        order.setOrderType("PROMOTION");
        order.setReceiverAddress(addressInfo.toString());
        this.save(order);
        boolean flag = this.saveOrderItem(order, cartMini);
        if (!flag) {
            throw new BusinessException(ExceptionCode.ORDER_ITEM_SAVE_ERROR);
        }
        // 锁定优惠券和商品库存信息
        couponFeign.lockCouponRecords(new LockCouponRecordDTO(RequestContext.getUserId(), Arrays.asList(dto.getCouponRecordIds()), orderOutTradeNo));
        List<CartItemVO> cartItems = cartMini.getCartItems();
        List<OrderItemDTO> orderItemList = cartItems.stream().map(item -> {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setProductId(item.getProductId());
            orderItemDTO.setBuyNum(item.getBuyNum());
            return orderItemDTO;
        }).collect(Collectors.toList());
        productFeign.lockStockRecords(new LockProductDTO(orderOutTradeNo, orderItemList));
        // 减购物车中的库存
        JsonData data = productFeign.reduceCartOps(idList);
        if (data.getCode() != 0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "购物车商品状态报错");
        }
        // 发送延迟队，判断持久未支付的订单
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOutTradeNo(orderOutTradeNo);
        log.info("订单号：{}", orderOutTradeNo);
        rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getOrderReleaseDelayRoutingKey(), orderMessage);
        log.info("发送消息成功，{}", orderMessage);
        // 调用支付宝扣费
        PayInfoVO payInfoVO = new PayInfoVO();
        payInfoVO.setPayType(dto.getPayType());
        payInfoVO.setPayFee(dto.getRealPayAmount());
        payInfoVO.setTitle("这是一个订单");
        payInfoVO.setDescription("没有描述");
        payInfoVO.setOutTradeNo(orderOutTradeNo);
        payInfoVO.setClientType(dto.getClientType());
        payInfoVO.setOrderPayTimeoutMills(new Date(new Date().getTime() + OrderConstants.Order_Pay_Time).getTime());
        String form = payFactory.unifiedOrder(payInfoVO);
        if (StringUtils.isNotBlank(form)) {
            log.info("创建支付订单成功，订单号：{}", payInfoVO.getOutTradeNo());
            return JsonData.ok(form);
        } else {
            log.error("创建支付订单失败！");
            return JsonData.error("支付订单创建失败");
        }
    }

    private boolean saveOrderItem(ProductOrder order, CartVO cartMini) {
        LinkedList<ProductOrderItem> list = new LinkedList<>();
        // 遍历购物车中每一件商品
        for (CartItemVO cartItem : cartMini.getCartItems()) {
            ProductOrderItem item = new ProductOrderItem();
            item.setProductOrderId(order.getId());
            item.setOutTradeNo(order.getOutTradeNo());
            item.setProductId(cartItem.getProductId());
            item.setProductName(cartItem.getProductTitle());
            item.setProductImg(cartItem.getProductImg());
            item.setBuyNum(cartItem.getBuyNum());
            item.setTotalAmount(cartItem.getTotalAmount());
            item.setAmount(cartItem.getAmount());
            list.add(item);
        }
        // 根据商品id，将用户购买的商品信息保存至product_order_item表中
        return productOrderItemService.saveBatch(list);
    }

    private void checkCartMini(CartVO cartMini, OrderDTO dto, List<CouponRecordVO> recordList) {
        // 查看库存数量是否符合 （在product模块已经检测了）
        // 查看后端计算的总价和前端是否相同，不相同则报错
        BigDecimal totalPrice = cartMini.getTotalPrice();
        BigDecimal realPrice;
        // 查看总价格是否正确不正确则两个realPrice相比较必不同
        if (totalPrice.compareTo(dto.getTotalAmount()) != 0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "价格变更，禁止该操作");
        }
        // 得到所有优惠券加起来满减的价格
        BigDecimal reduceMoney = new BigDecimal("0");
        for (CouponRecordVO recordVO : recordList) {
            BigDecimal conditionPrice = recordVO.getConditionPrice();
            if (totalPrice.compareTo(conditionPrice) < 0) {
                throw new BusinessException(ExceptionCode.PARAMS_ERROR, "优惠券不可用，未到满减价格");
            }
            reduceMoney = reduceMoney.add(recordVO.getPrice());
        }
        // 满减的情况
        if (totalPrice.compareTo(reduceMoney) < 0) {
            realPrice = BigDecimal.ZERO;
        } else {
            realPrice = totalPrice.subtract(reduceMoney);
        }
        if (realPrice.compareTo(dto.getRealPayAmount()) != 0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "价格变更，禁止该操作");
        }
    }

    private void checkRecord(List<CouponRecordVO> recordList) {
        // 校验
        for (CouponRecordVO recordVO : recordList) {
            if (!Objects.equals(recordVO.getUseState(), CouponStateEnum.NEW.name())) {
                throw new BusinessException(ExceptionCode.PARAMS_ERROR, "优惠券不可用");
            }
            long startTime = recordVO.getStartTime().getTime();
            long endTime = recordVO.getEndTime().getTime();
            long now = new Date().getTime();
            if (now > endTime || now < startTime) {
                throw new BusinessException(ExceptionCode.COUPON_OUT_OF_TIME);
            }
        }
    }

    @Override
    public String queryProductStatus(String outTradeNo) {
        return this.baseMapper.queryProductStatus(outTradeNo);
    }

    @Override
    public boolean checkOrderMessage(OrderMessage msg) {
        // 得到账单的状态
        ProductOrder order = this.getOne(new QueryWrapper<ProductOrder>().eq("out_trade_no", msg.getOutTradeNo()));
        if (ObjectUtils.isEmpty(order)) {
            log.warn("该订单不存在");
            return true;
        }
        String state = order.getState();
        // 查看当前订单的状态
        // 1.状态为NEW，查询远程支付系统看该账单支付了没，没有支付修改账单为取消账单CANCEL，支付了修改账单为已支付FINISH
        if (StringUtils.isNotBlank(state) && state.equals(ProductOrderStateEnum.NEW.name())) {
            // 远程查询：
            String require;
            PayInfoVO payInfoVO = new PayInfoVO();
            payInfoVO.setOutTradeNo(msg.getOutTradeNo());
            payInfoVO.setClientType(order.getPayType());
            payInfoVO.setPayType(order.getPayType());
            try {
                require = payFactory.queryPaySuccess(payInfoVO);
                log.info("require:{}", require);
            } catch (AlipayApiException e) {
                log.warn("订单状态查询失败，订单号：{}", order.getOutTradeNo());
                return false;
            }
            if (require != null) { // 修改为已支付
                log.warn("网络延迟太高，未及时更新，{}", msg.getOutTradeNo());
                this.baseMapper.updateStateOrder(msg.getOutTradeNo(), ProductOrderStateEnum.PAY.name());
            } else { // 修改为未支付
                log.warn("该订单未支付，{}", msg.getOutTradeNo());
                this.baseMapper.updateStateOrder(msg.getOutTradeNo(), ProductOrderStateEnum.CANCEL.name());
            }
            return true;
        }
        log.warn("该订单已完成，{}", msg.getOutTradeNo());
        return true;
    }

    @Override
    public int updateOrderByTradeNo(String outTradeNo) {
        return this.baseMapper.updateOrderByTradeNo(outTradeNo);
    }

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer size, String type) {
        Page<ProductOrder> p = new Page<>(page, size);
        QueryWrapper<ProductOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", RequestContext.getUserId());
        queryWrapper.eq("state", type);
        Page<ProductOrder> orderPage = this.baseMapper.selectPage(p, queryWrapper);
        // 得到orderId
        List<ProductOrder> records = orderPage.getRecords();
        List<OrderQueryVO> voList = CopyUtil.copyList(records, OrderQueryVO.class);
        List<Long> idList = voList.stream().map(OrderQueryVO::getId).collect(Collectors.toList());
        // 得到所有的orderItems
        List<ProductOrderItem> orderItems = productOrderItemMapper.selectList(new QueryWrapper<ProductOrderItem>().in("product_order_id",idList));
        // 转换
        List<OrderItemQueryVO> itemQueryVOS = CopyUtil.copyList(orderItems, OrderItemQueryVO.class);
        // 将orderItems封装为map集合，productOrderId为键
        Map<Long, List<OrderItemQueryVO>> map = itemQueryVOS.stream().collect(Collectors.groupingBy(OrderItemQueryVO::getProductOrderId));
        for (OrderQueryVO vo : voList) {
            vo.setItemList(map.get(vo.getId()));
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPage", orderPage.getPages());
        hashMap.put("currentData", voList);
        hashMap.put("totalRecord", orderPage.getTotal());
        return hashMap;
    }
}




