package com.liuche.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author 刘彻
 * @Date 2023/8/8 16:20
 * @PackageName: com.liuche.order.vo
 * @ClassName: PayInfoVO
 * @Description: 支付的请求模板类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInfoVO {
    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 订单总额
     */
    private BigDecimal payFee;
    /**
     * 支付类型 微信-支付宝-银行-其他
     */
    private String payType;
    /**
     * 客户端类型 H5-PC—APP
     */
    private String clientType;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 剩余支付时间
     */
    private Long orderPayTimeoutMills;
}
