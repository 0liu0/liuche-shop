package com.liuche.order.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName product_order_item
 */
@TableName(value ="product_order_item")
@Data
public class ProductOrderItem implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField(value = "product_order_id")
    private Long productOrderId;

    /**
     * 
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    /**
     * 产品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品图片
     */
    @TableField(value = "product_img")
    private String productImg;

    /**
     * 购买数量
     */
    @TableField(value = "buy_num")
    private Integer buyNum;

    /**
     * 购物项商品总价格
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 购物项商品单价
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

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