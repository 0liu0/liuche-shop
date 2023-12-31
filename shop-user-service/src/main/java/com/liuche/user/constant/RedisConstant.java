package com.liuche.user.constant;

/**
 * @Author 刘彻
 * @Date 2023/7/25 16:03
 * @PackageName: com.liuche.user.constant
 * @ClassName: RedisConstant
 * @Description: TODO
 */
public class RedisConstant {
    public static final String USER_REGISTER_CODE_IMG_REDIS_KEY = "user:register:captcha:graph:code:";
    public static final String USER_REGISTER_CODE_MAIL_REDIS_KEY = "user:register:captcha:mail:code:";
    public static final String USER_LOGIN_CODE_IMG_REDIS_KEY = "user:login:captcha:graph:code:";
    public static final String USER_LOGIN_CODE_MAIL_REDIS_KEY = "user:login:captcha:mail:code:";
    public static final int USER_REGISTER_CODE_REDIS_OUT_TIME = 60 * 1000 * 30; // 三分钟
}
