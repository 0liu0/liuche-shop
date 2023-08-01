package com.liuche.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuche.product.mapper.ProductMapper;
import com.liuche.product.model.Product;
import com.liuche.product.service.ProductService;
import org.springframework.stereotype.Service;

/**
* @author 70671
* @description 针对表【product】的数据库操作Service实现
* @createDate 2023-08-01 22:23:53
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService {

}




