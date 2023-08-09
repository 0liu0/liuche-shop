package com.liuche.order.dto;

import lombok.Data;

/**
 * @Author 刘彻
 * @Date 2023/8/10 0:29
 * @PackageName: com.liuche.order.dto
 * @ClassName: RepayOrderDTO
 * @Description: TODO
 */
@Data
public class RepayOrderDTO {
    /**
     * 支付平台
     */
    private String payType;
    /**
     * 支付的客户端类型
     */
    private String clientType;
    /**
     * 订单唯一id
     */
    private String outTradeNo;
}