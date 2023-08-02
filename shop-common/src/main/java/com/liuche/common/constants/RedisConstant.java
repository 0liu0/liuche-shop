package com.liuche.common.constants;

/**
 * @Author 刘彻
 * @Date 2023/8/1 18:34
 * @PackageName: com.liuche.common.constants
 * @ClassName: RedisConstant
 * @Description: TODO
 */
public interface RedisConstant {
    // 优惠券的分布式锁，后面加的是优惠券id
    String COUPON_LOCK = "shop:coupon:";
    // 用户购物车的前缀后面加上用户id
    String USER_CART = "shop:cart:";
}
