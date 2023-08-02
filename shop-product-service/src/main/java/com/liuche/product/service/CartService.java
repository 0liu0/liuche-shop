package com.liuche.product.service;

import com.liuche.product.dto.AddProductDTO;

/**
 * @Author 刘彻
 * @Date 2023/8/2 14:38
 * @PackageName: com.liuche.product.service
 * @ClassName: CartService
 * @Description: TODO
 */
public interface CartService {
    boolean addProduct(AddProductDTO dto);
}
