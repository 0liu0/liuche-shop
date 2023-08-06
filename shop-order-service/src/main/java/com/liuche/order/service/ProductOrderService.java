package com.liuche.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuche.common.model.OrderMessage;
import com.liuche.order.dto.OrderDTO;
import com.liuche.order.model.ProductOrder;

/**
* @author 70671
* @description 针对表【product_order】的数据库操作Service
* @createDate 2023-08-03 11:43:32
*/
public interface ProductOrderService extends IService<ProductOrder> {

    boolean confirmOrder(OrderDTO dto);

    String queryProductStatus(String outTradeNo);

    boolean checkOrderMessage(OrderMessage msg);
}
