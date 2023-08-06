package com.liuche.order.feign;

import com.liuche.common.util.JsonData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 刘彻
 * @Date 2023/8/6 19:46
 * @PackageName: com.liuche.order.feign
 * @ClassName: AddressFeign
 * @Description: TODO
 */
@FeignClient(name = "shop-user-service")
public interface AddressFeign {
    @GetMapping("/api/v1/address/get-one/address/{id}")
    JsonData getOneAddress(@PathVariable @ApiParam(value = "需要查询的地址ID") Long id);
}
