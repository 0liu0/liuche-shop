package com.liuche.user.constant;

/**
 * @Author 刘彻
 * @Date 2023/7/25 16:03
 * @PackageName: com.liuche.user.constant
 * @ClassName: RedisConstant
 * @Description: TODO
 */
public class RedisConstant {
    public static final String USER_REGISTE_CODE_REDIS_KEY = "user:captcha:code:";
    public static final int USER_REGISTE_CODE_REDIS_OUTTIME = 60*1000*3; // 三分钟
}
