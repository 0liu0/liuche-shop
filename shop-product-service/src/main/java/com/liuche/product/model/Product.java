package com.liuche.product.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
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
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 锁定库存
     */
    @TableField(value = "lock_stock")
    private Integer lockStock;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}