package com.liuche.common.model;

import lombok.Data;

/**
 * @Author 刘彻
 * @Date 2023/8/7 2:11
 * @PackageName: com.liuche.common.model
 * @ClassName: OrderMessage
 * @Description: TODO
 */
@Data
public class OrderMessage {

    /**
     * 消息队列id
     */
    private Long messageId;

    /**
     * 订单唯一编号
     */
    private String outTradeNo;
}