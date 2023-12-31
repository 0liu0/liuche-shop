package com.liuche.common.enums;

import lombok.Getter;

/**
 * @Author 刘彻
 * @Date 2023/7/24 22:50
 * @PackageName: com.liuche.common.enums
 * @ClassName: ExceptionCode
 * @Description: TODO
 */
@Getter
public enum ExceptionCode {
    /**
     * 通用操作码
     */
    OPS_REPEAT(110001, "重复操作"),
    PARAMS_ERROR(110002, "参数错误"),
    /**
     * 验证码
     */
    CODE_TO_ERROR(240001, "接收号码不合规"),
    CODE_LIMITED(240002, "验证码发送过快"),
    CODE_ERROR(240003, "验证码错误"),
    CODE_CAPTCHA(240101, "图形验证码错误"),

    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001, "账号已经存在"),
    ACCOUNT_UNREGISTER(250002, "账号不存在"),
    ACCOUNT_PWD_ERROR(250003, "账号或者密码错误"),
    /**
     * 地址
     */
    USER_ADDRESS_ERROR(280000,"用户地址不存在"),
    /**
     * 系统
     */
    SYSTEM_ERROR(260000, "系统内部异常"),
    /**
     * 优惠券
     */
    COUPON_OUT_OF_TIME(2700001,"优惠券已过期"),
    COUPON_NO_EXITS(2700002,"优惠券不存在"),
    COUPON_NO_STOCK(2700002,"已被领取完，或已达最大限制"),
    /**
     * 订单
     */
    ORDER_OUT_OF_PAY_TIME(280001,"订单已超时，不可支付"),
    ORDER_ITEM_SAVE_ERROR(280004,"订单项保存失败"),
    ORDER_INIT_FAILED(280002,"订单构建失败"),
    ORDER_MESSAGE_QUERY_ERROR(280003,"查询订单失败"),
    ORDER_EXIST_NO(280005,"订单不存在"),
    ORDER_HAD_OUT_TIME(280006,"订单超时"),
    ORDER_TOKEN_NOT_EXIST(280007,"没有订单令牌"),
    ORDER_TOKEN_NOT_MATE(280008,"token令牌不匹配"),
    ;
    private String msg;
    private int code;

    ExceptionCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
