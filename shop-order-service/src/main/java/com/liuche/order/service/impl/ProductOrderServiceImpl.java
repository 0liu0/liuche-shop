package com.liuche.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.common.util.JsonData;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.feign.AddressFeign;
import com.liuche.order.mapper.ProductOrderMapper;
import com.liuche.order.model.ProductOrder;
import com.liuche.order.service.ProductOrderService;
import com.liuche.order.vo.AddressInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * @param dto
     * @return
     */
    public boolean confirmOrder(OrderDTO dto) {
        log.info("前端传来的信息："+dto);
        // 得到用户收货地址地址
        JsonData address = addressFeign.getOneAddress(dto.getAddressId());
        log.info("address:{}",address);
        AddressInfoResp addressInfo = address.getData(new TypeReference<>(){});
        log.info("得到用户收货地址信息：{}",addressInfo);
        return true;
    }

    @Override
    public String queryProductStatus(String outTradeNo) {
        return this.baseMapper.queryProductStatus(outTradeNo);
    }
}




