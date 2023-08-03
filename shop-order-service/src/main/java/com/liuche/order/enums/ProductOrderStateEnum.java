package com.liuche.order.enums;

/**
 * @Author 刘彻
 * @Date 2023/8/3 11:56
 * @PackageName: com.liuche.order.enums
 * @ClassName: ProductOrderStateEnum
 * @Description: TODO
 */
public enum ProductOrderStateEnum {

    /**
     * 未支付订单
     */
    NEW,


    /**
     * 已经支付订单
     */
    PAY,

    /**
     * 超时取消订单
     */
    CANCEL
}
