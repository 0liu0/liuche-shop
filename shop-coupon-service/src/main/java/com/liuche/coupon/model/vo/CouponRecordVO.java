package com.liuche.coupon.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 刘彻
 * @Date 2023/8/6 21:05
 * @PackageName: com.liuche.coupon.model.vo
 * @ClassName: CouponRecordVO
 * @Description: TODO
 */
@Data
public class CouponRecordVO implements Serializable {
    private static final long serialVersionUID = 1473743647255423074L;
    /**
     * id
     */
    private Long id;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 创建时间/获得时间
     */
    private Date createTime;

    /**
     * 使用状态  可用 NEW,已使用USED,过期 EXPIRED;
     */
    private String useState;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 优惠券标题
     */
    private String couponTitle;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 抵扣价格
     */
    private BigDecimal price;

    /**
     * 满多少才可以使用
     */
    private BigDecimal conditionPrice;
}
