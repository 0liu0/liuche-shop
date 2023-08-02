package com.liuche.product.controller;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.JsonData;
import com.liuche.product.dto.AddProductDTO;
import com.liuche.product.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 刘彻
 * @Date 2023/8/2 14:38
 * @PackageName: com.liuche.product.controller
 * @ClassName: CartController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add/product")
    public JsonData addProduct(@RequestBody AddProductDTO dto) {
        // 校验参数 todo 校验参数的数量是否符合规范，例如不可小于0
        if (ObjectUtils.isEmpty(dto)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR);
        }
        // 添加商品至购物车
        cartService.addProduct(dto);
        return JsonData.ok("添加商品成功！");
    }

}
