package com.liuche.user.controller;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.wf.captcha.SpecCaptcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 刘彻
 * @Date 2023/7/24 0:08
 * @PackageName: com.liuche.user.controller
 * @ClassName: UserController
 * @Description: TODO
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping("/test")
    public String test() {
        return "这是测试哦";
    }
}
