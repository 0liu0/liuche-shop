package com.liuche.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author 刘彻
 * @Date 2023/8/2 14:46
 * @PackageName: com.liuche.product.dto
 * @ClassName: AddProductDTO
 * @Description: TODO
 */
@Data
@ApiModel(value = "用户购物车添加商品实体类")
public class AddProductDTO implements Serializable {
    private static final long serialVersionUID = -3386350172251547389L;
    /**
     * 商品id
     */
    @NotNull(message = "【商品id】不能为空")
    private Long productId;
    /**
     * 添加至购物车的商品数量
     */
    @Min(value = 1,message = "【商品数量】不能小于1")
    private Integer buyNum;
}
