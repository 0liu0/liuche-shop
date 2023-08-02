package com.liuche.product.controller;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.JsonData;
import com.liuche.product.dto.AddProductDTO;
import com.liuche.product.dto.UpdateProductDTO;
import com.liuche.product.service.CartService;
import com.liuche.product.vo.CartVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public JsonData addProduct(@Valid @RequestBody AddProductDTO dto) {
        // 校验参数 todo 校验参数的数量是否符合规范，例如不可小于0
        if (ObjectUtils.isEmpty(dto)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR);
        }
        // 添加商品至购物车
        cartService.addProduct(dto);
        return JsonData.ok("添加商品成功！");
    }

    @ApiOperation("清除用户购物车")
    @GetMapping("/clear")
    public JsonData clearCart() {
        cartService.clearCart();
        return JsonData.ok("清除用户购物车成功！");
    }

    @ApiOperation("查看用户购物车")
    @GetMapping("/user/cart")
    public JsonData getUserCart() {
        CartVO cartVO = cartService.getUserCartInfo();
        return JsonData.ok(cartVO);
    }

    @ApiOperation("从购物车删除商品")
    @GetMapping("/del/product/{id}")
    public JsonData delProduct(@PathVariable String id) {
        boolean flag = cartService.delProduct(id);
        return flag?JsonData.ok("删除成功"): JsonData.error("未找到此商品，或以删除");
    }

    @ApiOperation("从购物车修改商品信息")
    @PostMapping("/update/product")
    public JsonData updateProduct(@Valid @RequestBody UpdateProductDTO productDTO) {
        if (productDTO==null) {
            return JsonData.error("错误！");
        }
        // 参数校验 todo 购买数量必须大于等于1
        cartService.updateCartProductInfo(productDTO);
        return JsonData.ok("修改成功！");
    }

}
