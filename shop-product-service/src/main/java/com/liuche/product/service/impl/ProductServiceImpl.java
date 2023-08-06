package com.liuche.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.dto.LockProductDTO;
import com.liuche.common.dto.OrderItemDTO;
import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.enums.ProductOrderStateEnum;
import com.liuche.common.enums.StockTaskStateEnum;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.model.CouponRecordMessage;
import com.liuche.common.model.ProductMessage;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import com.liuche.product.config.MQConfig;
import com.liuche.product.dto.AddProductDTO;
import com.liuche.product.feign.OrderFeign;
import com.liuche.product.mapper.ProductMapper;
import com.liuche.product.mapper.ProductTaskMapper;
import com.liuche.product.model.Product;
import com.liuche.product.model.ProductTask;
import com.liuche.product.service.CartService;
import com.liuche.product.service.ProductService;
import com.liuche.product.service.ProductTaskService;
import com.liuche.product.vo.CartItemVO;
import com.liuche.product.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

/**
 * @author 70671
 * @description 针对表【product】的数据库操作Service实现
 * @createDate 2023-08-01 22:23:53
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {
    @Resource
    private ProductTaskService productTaskService;
    @Resource
    private ProductTaskMapper productTaskMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private MQConfig mqConfig;
    @Resource
    private OrderFeign orderFeign;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private CartService cartService;

    /**
     * 分页查询商品信息
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> selectByPage(int page, int size) {
        Page<Product> newPage = new Page<>(page, size);
        Page<Product> productPage = this.baseMapper.selectPage(newPage, new QueryWrapper<Product>());
        HashMap<String, Object> map = new HashMap<>();
        List<Product> records = productPage.getRecords();
        List<ProductVO> list = CopyUtil.copyList(records, ProductVO.class);
        // 对商品信息进行脱敏
        map.put("total", productPage.getTotal());
        map.put("pages", productPage.getPages());
        map.put("records", list);
        return map;
    }

    @Override
    @Transactional
    public JsonData lockProductStock(LockProductDTO dto) {
        // 得到商品参数
        List<OrderItemDTO> orderItemList = dto.getOrderItemList(); // 每件商品的参数
        String outTradeNo = dto.getOrderOutTradeNo(); // 订单编号
        // 修改订单对应的商品的锁定库存
        // 先得到全部的商品，根据商品id
        List<Long> productIdList = orderItemList.stream().map(OrderItemDTO::getProductId).collect(Collectors.toList()); // 查询所有的id
        List<Product> products = this.baseMapper.selectBatchIds(productIdList); // 得到所有的订单上的产品
        Map<Long, OrderItemDTO> productMap = orderItemList.stream().collect(Collectors.toMap(OrderItemDTO::getProductId, Function.identity())); // 分组
        // 锁定商品的库存,并且插入记录在product_task中
        for (Product item : products) {
            // 锁定商品的库存
            int buyNum = productMap.get(item.getId()).getBuyNum();
            int num = this.baseMapper.lockStock(item.getId(), buyNum);
            if (num == 1) {
                // 插入记录到product_task中
                ProductTask productTask = new ProductTask();
                productTask.setProductId(item.getId());
                productTask.setProductName(item.getTitle());
                productTask.setLockState(StockTaskStateEnum.LOCK.name());
                productTask.setBuyNum(buyNum);
                productTask.setOutTradeNo(outTradeNo);
                // 保存至数据库
                productTaskService.save(productTask);
                // 发送延迟消息
                ProductMessage message = new ProductMessage();
                message.setOutTradeNo(outTradeNo);
                message.setTaskId(productTask.getId());
                message.setUserId(RequestContext.getUserId()); // 新增用户id
                rabbitTemplate.convertAndSend(mqConfig.getEventExchange(), mqConfig.getStockReleaseDelayRoutingKey(), message);
                log.info("商品锁定消息发送成功！{}", message);
            } else {
                throw new BusinessException(ExceptionCode.PARAMS_ERROR, "锁定商品的库存失败");
            }
        }
        return null;
    }

    @Override
    public boolean checkOrderOfProduct(ProductMessage msg) {
        // 查找当前的product_task是否存在
        ProductTask task = productTaskService.getById(msg.getTaskId());
        if (task == null) {
            log.warn("工作单不存在，消息：{}", msg);
            return true;
        }
        // 当当前task状态为LOCK才走流程
        if (StockTaskStateEnum.LOCK.name().equals(task.getLockState())) {
            // 调用远程接口，查看订单状态
            JsonData jsonData = orderFeign.queryState(msg.getOutTradeNo());
            if (jsonData.getCode() == 0) { // 响应正常
                // 得到响应的状态信息
                String status = jsonData.getData().toString();
                if (status.equals(ProductOrderStateEnum.PAY.name())) {
                    // 如果订单已经支付了，更新product_task表为FINISH
                    Long taskId = msg.getTaskId();
                    int num = productTaskMapper.confirmProduct(taskId);
                    log.info("订单已完成：{}", msg);
                    return num > 0;
                } else if (status.equals(ProductOrderStateEnum.NEW.name())) {
                    // 订单状态为还未消费
                    log.info("还未消费哦");
                    return false;
                }
            }
            // 修改couponTask为CANCEL
            productTaskMapper.updateProductTaskCancel(msg.getTaskId());
            productMapper.releaseProduct(task.getProductId(), task.getBuyNum());
            // 将取消支付的商品恢复至用户的购物车中
            AddProductDTO dto = new AddProductDTO();
            dto.setProductId(task.getProductId());
            dto.setBuyNum(task.getBuyNum());
            // 恢复至用户购物车中
            boolean flag = cartService.addProduct(dto);
            log.info("单子失效，恢复商品至购物车");
            if (!flag) {
                log.info("商品恢复至购物车失败，请重试！");
                return false;
            }
            log.info("订单不存在或已取消：{}", msg);
        } else {
            log.warn("工作单状态不是LOCK，state={}，消息体={}", task.getLockState(), msg);
        }
        return true;
    }
}




