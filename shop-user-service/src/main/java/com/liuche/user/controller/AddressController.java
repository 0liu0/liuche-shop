package com.liuche.user.controller;

import com.liuche.common.util.JsonData;
import com.liuche.user.model.request.AddressAddRequest;
import com.liuche.user.service.AddressService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 刘彻
 * @Date 2023/7/28 18:12
 * @PackageName: com.liuche.user.controller
 * @ClassName: AddressController
 * @Description: 关于地址信息的编写
 */
@RestController
@RequestMapping("/api/v1/address")
@Slf4j
@ApiOperation(value = "地址相关接口")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/save/address")
    @ApiOperation(value = "新增默认地址")
    public JsonData saveAddress(@RequestBody AddressAddRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return JsonData.error("参数错误");
        }
        int i = addressService.addAddress(request);
        return i==1?JsonData.ok("成功添加一个收获地址"):JsonData.error("添加错误");
    }
}
