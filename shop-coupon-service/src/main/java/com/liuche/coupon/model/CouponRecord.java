package com.liuche.coupon.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @TableName coupon_record
 */
@TableName(value = "coupon_record")
@Data
public class CouponRecord implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券id
     */
    @TableField(value = "coupon_id")
    private Long couponId;

    /**
     * 创建时间获得时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 使用状态  可用 NEW,已使用USED,过期 EXPIRED;
     */
    @TableField(value = "use_state")
    private String useState;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 优惠券标题
     */
    @TableField(value = "coupon_title")
    private String couponTitle;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 抵扣价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 满多少才可以使用
     */
    @TableField(value = "condition_price")
    private BigDecimal conditionPrice;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 订单唯一编号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}