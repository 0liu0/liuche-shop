package com.liuche.product.service;

import com.liuche.product.dto.AddProductDTO;
import com.liuche.product.dto.UpdateProductDTO;
import com.liuche.product.vo.CartItemVO;
import com.liuche.product.vo.CartVO;
import com.liuche.product.vo.ProductVO;

import java.util.List;
import java.util.Map;

/**
 * @Author 刘彻
 * @Date 2023/8/2 14:38
 * @PackageName: com.liuche.product.service
 * @ClassName: CartService
 * @Description: TODO
 */
public interface CartService {
    boolean addProduct(AddProductDTO dto);

    boolean clearCart();

    CartVO getUserCartInfo();

    boolean delProduct(String id);

    boolean updateCartProductInfo(UpdateProductDTO productDTO);

    CartVO getUserCartProductInfo(List<Long> prductIdList);

    boolean reduceCartOps(List<Long> idList);
}
