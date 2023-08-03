package com.liuche.order.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName product_order
 */
@TableName(value ="product_order")
@Data
public class ProductOrder implements Serializable {
    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单唯一标识
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    /**
     * NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单
     */
    @TableField(value = "state")
    private String state;

    /**
     * 订单总金额
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单实际支付价格
     */
    @TableField(value = "pay_amount")
    private BigDecimal payAmount;

    /**
     * 支付类型，微信-银行-支付宝
     */
    @TableField(value = "pay_type")
    private String payType;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField(value = "head_img")
    private String headImg;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 订单类型 DAILY普通单，PROMOTION促销订单
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 收货地址 json存储
     */
    @TableField(value = "receiver_address")
    private String receiverAddress;

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