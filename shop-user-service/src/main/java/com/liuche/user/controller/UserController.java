package com.liuche.user.controller;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CopyUtil;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import com.liuche.user.model.User;
import com.liuche.user.model.request.UserLoginVO;
import com.liuche.user.model.request.UserRegisterVO;
import com.liuche.user.model.response.UserInfoRespVO;
import com.liuche.user.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private UserService userService;
    @GetMapping("/test")
    public String test() {
        return "这是测试哦，用户id为:"+ RequestContext.getUserId();
    }
    @PostMapping("/register")
    public JsonData register(@RequestBody UserRegisterVO userRegisterVO, HttpServletRequest request) {
        // 校验参数
        if (ObjectUtils.isEmpty(userRegisterVO)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR,"参数不符合要求");
        }
        boolean flag = userService.register(userRegisterVO, request);
        if (flag) return JsonData.ok("注册成功！");
        return JsonData.error("注册失败，请稍后再试");
    }

    @PostMapping("/login")
    public JsonData login(@RequestBody UserLoginVO userLoginVO, HttpServletRequest request) {
        // 校验参数
        if (ObjectUtils.isEmpty(userLoginVO)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR,"参数不符合要求");
        }
        String token = userService.login(userLoginVO, request);
        return JsonData.ok(token);
    }
}
