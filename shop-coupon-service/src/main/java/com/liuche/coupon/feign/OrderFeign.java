package com.liuche.coupon.feign;

import com.liuche.common.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 刘彻
 * @Date 2023/8/5 19:40
 * @PackageName: com.liuche.coupon.feign
 * @ClassName: OrderFeign
 * @Description: TODO
 */
@FeignClient(name = "shop-order-service")
public interface OrderFeign {
    @GetMapping("/api/v1/order/query_state/{outTradeNo}")
    JsonData queryState(@PathVariable String outTradeNo);
}
