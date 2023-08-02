package com.liuche.product.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author 刘彻
 * @Date 2023/8/2 16:46
 * @PackageName: com.liuche.product.dto
 * @ClassName: UpdateProductDTO
 * @Description: TODO
 */
@ApiModel(value = "修改商品信息实体类")
@Data
public class UpdateProductDTO implements Serializable {
    private static final long serialVersionUID = -7479286180041823704L;
    /**
     * 商品id
     */
    @NotNull(message = "【商品id】不能为空")
    private Long productId;
    /**
     * 商品欲购买数量
     */
    @Min(value = 1, message = "【商品数量】不能小于1")
    private Integer buyNum;
}
