package com.liuche.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/8/5 17:28
 * @PackageName: com.liuche.common.dto
 * @ClassName: LockCouponRecordDTO
 * @Description: 订单服务调用优惠券服务的请求体
 */
@Data
@NoArgsConstructor
public class LockCouponRecordDTO implements Serializable {
    private static final long serialVersionUID = 4275631747614415214L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 记录id
     */
    private List<Long> lockCouponRecordIds;

    /**
     * 订单号
     */
    private String orderOutTradeNo;

    public LockCouponRecordDTO(Long userId, List<Long> lockCouponRecordIds, String orderOutTradeNo) {
        this.userId = userId;
        this.lockCouponRecordIds = lockCouponRecordIds;
        this.orderOutTradeNo = orderOutTradeNo;
    }
}
