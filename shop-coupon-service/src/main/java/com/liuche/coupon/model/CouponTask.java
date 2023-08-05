package com.liuche.coupon.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName coupon_task
 */
@TableName(value ="coupon_task")
@Data
public class CouponTask implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券记录id
     */
    @TableField(value = "coupon_record_id")
    private Long couponRecordId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 订单号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    /**
     * 锁定状态 锁定LOCK-完成FINISH 取消CANCEL
     */
    @TableField(value = "lock_state")
    private String lockState;

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