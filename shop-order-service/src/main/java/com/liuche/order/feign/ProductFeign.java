package com.liuche.order.feign;

import com.liuche.common.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author 刘彻
 * @Date 2023/8/6 21:28
 * @PackageName: com.liuche.order.feign
 * @ClassName: ProductFeign
 * @Description: TODO
 */
@FeignClient("shop-product-service")
public interface ProductFeign {
    @PostMapping("/api/v1/cart/user/cart/list")
    JsonData getUserCartProductByIds(@RequestBody List<Long> prductIdList);

    @PostMapping("/api/v1/cart/user/cart/reduce")
    JsonData reduceCartOps(@RequestBody List<Long> idList);
}
