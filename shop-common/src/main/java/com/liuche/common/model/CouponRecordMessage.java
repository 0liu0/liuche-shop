package com.liuche.common.model;

import lombok.Data;

/**
 * @Author 刘彻
 * @Date 2023/8/5 18:19
 * @PackageName: com.liuche.common.model
 * @ClassName: CouponRecordMessage
 * @Description: TODO
 */
@Data
public class CouponRecordMessage {

    /**
     * 消息队列id
     */
    private Long messageId;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 库存锁定工作单id
     */
    private Long taskId;
}