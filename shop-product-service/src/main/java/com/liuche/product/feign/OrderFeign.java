package com.liuche.product.feign;

import com.liuche.common.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 刘彻
 * @Date 2023/8/5 21:24
 * @PackageName: com.liuche.product.feign
 * @ClassName: OrderFeign
 * @Description: 查询订单状态的远程调用
 */
@FeignClient(name = "shop-order-service")
public interface OrderFeign {
    @GetMapping("/api/v1/order/query_state/{outTradeNo}")
    JsonData queryState(@PathVariable String outTradeNo);
}
