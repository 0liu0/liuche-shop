package com.liuche.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 刘彻
 * @Date 2023/8/3 11:51
 * @PackageName: com.liuche.order.controller
 * @ClassName: ProductOrderController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/order")
public class ProductOrderController {
    @GetMapping("/test")
    public String test() {
        return "当你看到这条消息就证明整合成功了！";
    }
}
