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
    ACCOUNT_PWD_ERROR(250003, "账号或者密码错误");;
    private String msg;
    private int code;

    ExceptionCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
