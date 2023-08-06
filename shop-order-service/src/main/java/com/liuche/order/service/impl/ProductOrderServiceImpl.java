package com.liuche.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.enums.CouponStateEnum;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.JsonData;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.feign.AddressFeign;
import com.liuche.order.feign.CouponFeign;
import com.liuche.order.feign.ProductFeign;
import com.liuche.order.mapper.ProductOrderMapper;
import com.liuche.order.model.ProductOrder;
import com.liuche.order.service.ProductOrderService;
import com.liuche.order.vo.AddressInfoResp;
import com.liuche.order.vo.CartVO;
import com.liuche.order.vo.CouponRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
    public boolean confirmOrder(OrderDTO dto) {
        log.info("前端传来的信息：" + dto);
        // 创建订单id
        String orderOutTradeNo = CommonUtil.getStringNumRandom(32);
        // 得到用户收货地址地址
        JsonData address = addressFeign.getOneAddress(dto.getAddressId());
        if (address.getCode() != 0) {
            throw new BusinessException(ExceptionCode.USER_ADDRESS_ERROR);
        }
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
        Long[] productIds = dto.getProductIds();
        List<Long> idList = Arrays.asList(productIds);
        JsonData cartProduct = productFeign.getUserCartProductByIds(idList);
        if (cartProduct.getCode() != 0) throw new BusinessException(ExceptionCode.PARAMS_ERROR, "商品不存在或已下架");
        // 验证商品是否可以消费
        CartVO cartMini = cartProduct.getData(new TypeReference<>() {
        });
        log.info("cartMini->{}", cartMini.toString());
        this.checkCartMini(cartMini, dto, recordList); // 经过了这层说明该订单准确无误
        // 减购物车中的库存
        JsonData data = productFeign.reduceCartOps(idList);
        if (data.getCode()!=0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR,"购物车商品状态报错");
        }
        return true;
    }

    private void checkCartMini(CartVO cartMini, OrderDTO dto, List<CouponRecordVO> recordList) {
        // 查看库存数量是否符合 （在product模块已经检测了）
        // 查看后端计算的总价和前端是否相同，不相同则报错
        BigDecimal totalPrice = cartMini.getTotalPrice();
        BigDecimal realPrice;
        // 查看总价格是否正确不正确则两个realPrice相比较必不同
        if (totalPrice.compareTo(dto.getTotalAmount())!=0) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "价格变更，禁止该操作");
        }
        // 得到所有优惠券加起来满减的价格
        BigDecimal reduceMoney = new BigDecimal("0");
        for (CouponRecordVO recordVO : recordList) {
            BigDecimal conditionPrice = recordVO.getConditionPrice();
            if (totalPrice.compareTo(conditionPrice) < 0) {
                throw new BusinessException(ExceptionCode.PARAMS_ERROR, "优惠券不可用，未到满减价格");
            }
            reduceMoney.add(recordVO.getPrice());
        }
        // 满减的情况
        if (totalPrice.compareTo(reduceMoney) < 0) {
            realPrice = BigDecimal.ZERO;
        }else {
            realPrice = totalPrice.subtract(reduceMoney);
        }
        if (realPrice.compareTo(dto.getRealPayAmount())!=0) {
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
}




