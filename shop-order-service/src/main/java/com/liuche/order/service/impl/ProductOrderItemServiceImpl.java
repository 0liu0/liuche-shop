package com.liuche.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.order.mapper.ProductOrderItemMapper;
import com.liuche.order.model.ProductOrderItem;
import com.liuche.order.service.ProductOrderItemService;
import org.springframework.stereotype.Service;

/**
* @author 70671
* @description 针对表【product_order_item】的数据库操作Service实现
* @createDate 2023-08-03 11:43:36
*/
@Service
public class ProductOrderItemServiceImpl extends ServiceImpl<ProductOrderItemMapper, ProductOrderItem>
    implements ProductOrderItemService {

}




