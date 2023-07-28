package com.liuche.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import com.liuche.user.model.Address;
import com.liuche.user.model.request.AddressAddRequest;
import com.liuche.user.model.response.AddressInfoResp;
import com.liuche.user.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
public class AddressController {
    @Resource
    private AddressService addressService;

    @PostMapping("/save/address")
    @ApiOperation(value = "新增默认地址")
    public JsonData saveAddress(@RequestBody AddressAddRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return JsonData.error("参数错误");
        }
        int i = addressService.addAddress(request);
        return i == 1 ? JsonData.ok("成功添加一个收获地址") : JsonData.error("添加错误");
    }

    @GetMapping("/get-pne/address/{id}")
    @ApiOperation(value = "查询指定的收货地址")
    public JsonData getOneAddress(@PathVariable @ApiParam(value = "需要查询的地址ID") String id) {
        Address address = addressService.getOne(new QueryWrapper<Address>()
                .eq("user_id", RequestContext.getUserId())
                .eq("id", Long.parseLong(id)));
        AddressInfoResp infoResp = CopyUtil.copy(address, AddressInfoResp.class);
        return address == null ? JsonData.error("未查到") : JsonData.ok(infoResp);
    }

    @PostMapping("/list/address")
    @ApiOperation(value = "查询该用户所有的收货地址")
    public JsonData addressList() {
        // 直接返回该用户下所有的地址
        List<Address> list = addressService.list();
        // 脱敏
        List<AddressInfoResp> respList = CopyUtil.copyList(list, AddressInfoResp.class);
        return JsonData.ok(respList);
    }

    @GetMapping("/del/address/{id}")
    @ApiOperation(value = "删除指定的收货地址")
    public JsonData deleteAddress(@PathVariable @ApiParam(value = "需要删除的地址") String id) {
        // 删除指定地址
        boolean flag = addressService.remove(new QueryWrapper<Address>()
                .eq("user_id", RequestContext.getUserId())
                .eq("id", Long.parseLong(id)));
        return flag ? JsonData.ok("删除成功！") : JsonData.error("删除失败");
    }
}