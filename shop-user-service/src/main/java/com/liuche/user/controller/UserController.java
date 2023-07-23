package com.liuche.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 刘彻
 * @Date 2023/7/24 0:08
 * @PackageName: com.liuche.user.controller
 * @ClassName: UserController
 * @Description: TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/test")
    public String test() {
        return "这是测试哦";
    }
}
