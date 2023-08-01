package com.liuche.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/8/1 22:46
 * @PackageName: com.liuche.product.vo
 * @ClassName: ProductVO
 * @Description: TODO
 */
@Data
public class ProductVO implements Serializable {
    private static final long serialVersionUID = -5135912453904494335L;
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 封面图
     */
    @TableField(value = "cover_img")
    private String coverImg;

    /**
     * 详情
     */
    @TableField(value = "detail")
    private String detail;

    /**
     * 老价格
     */
    @TableField(value = "old_price")
    private BigDecimal oldPrice;

    /**
     * 新价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 锁定库存
     */
    @TableField(value = "lock_stock")
    private Integer lockStock;
}
