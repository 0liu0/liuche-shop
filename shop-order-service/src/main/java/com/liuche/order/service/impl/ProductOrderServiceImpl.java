package com.liuche.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.order.mapper.ProductOrderMapper;
import com.liuche.order.model.ProductOrder;
import com.liuche.order.service.ProductOrderService;
import org.springframework.stereotype.Service;

/**
* @author 70671
* @description 针对表【product_order】的数据库操作Service实现
* @createDate 2023-08-03 11:43:32
*/
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder>
    implements ProductOrderService {

}




