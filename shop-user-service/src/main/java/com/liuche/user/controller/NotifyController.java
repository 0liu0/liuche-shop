package com.liuche.user.controller;

import com.liuche.common.util.CommonUtil;
import com.liuche.user.constant.RedisConstant;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author 刘彻
 * @Date 2023/7/25 15:25
 * @PackageName: com.liuche.user.controller
 * @ClassName: NotifyController
 * @Description: 关于用户校验的一些请求
 */
@RestController
@RequestMapping("/api/v1/notify")
@Slf4j
public class NotifyController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // png类型
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        // 获取验证码的字符
        String code = captcha.text();
        // 将验证码写入redis
        String captchaKey = getCaptchaKey(request);
        stringRedisTemplate.opsForValue().set(RedisConstant.USER_REGISTE_CODE_REDIS_KEY + captchaKey, code, RedisConstant.USER_REGISTE_CODE_REDIS_OUTTIME, TimeUnit.MILLISECONDS);
        log.info("captchaKey:" + captchaKey);
        log.info("验证码：" + code);
        // 输出验证码
        captcha.out(response.getOutputStream());
    }

    private String getCaptchaKey(HttpServletRequest request) {
        // 根据用户的ip以及浏览器指纹获取唯一值
        String ipAddr = CommonUtil.getRemoteIp(request);
        String userAgent = request.getHeader("User-Agent");// 相当于浏览器指纹
        // userAgent这个信息很长不适合做key，使用md5加密变短点儿返回即可，保证了key的唯一性
        return CommonUtil.MD5(userAgent + ipAddr);
    }
}
