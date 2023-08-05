package com.liuche.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuche.common.dto.LockProductDTO;
import com.liuche.common.model.ProductMessage;
import com.liuche.common.util.JsonData;
import com.liuche.product.model.Product;

import java.util.Map;

/**
* @author 70671
* @description 针对表【product】的数据库操作Service
* @createDate 2023-08-01 22:23:53
*/
public interface ProductService extends IService<Product> {

    Map<String,Object> selectByPage(int page, int size);

    JsonData lockProductStock(LockProductDTO dto);

    boolean checkOrderOfProduct(ProductMessage msg);
}
