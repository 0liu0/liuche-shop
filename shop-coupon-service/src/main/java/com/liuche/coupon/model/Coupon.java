package com.liuche.coupon.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName coupon
 */
@TableName(value ="coupon")
@Data
public class Coupon implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠卷类型[NEW_USER注册赠券，TASK任务卷，PROMOTION促销劵]
     */
    @TableField(value = "category")
    private String category;

    /**
     * 发布状态, PUBLISH发布，DRAFT草稿，OFFLINE下线
     */
    @TableField(value = "publish")
    private String publish;

    /**
     * 优惠券图片
     */
    @TableField(value = "coupon_img")
    private String couponImg;

    /**
     * 优惠券标题
     */
    @TableField(value = "coupon_title")
    private String couponTitle;

    /**
     * 抵扣价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 每人限制张数
     */
    @TableField(value = "user_limit")
    private Integer userLimit;

    /**
     * 优惠券开始有效时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 优惠券失效时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 优惠券总量
     */
    @TableField(value = "publish_count")
    private Integer publishCount;

    /**
     * 库存
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

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
     * 是否删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}