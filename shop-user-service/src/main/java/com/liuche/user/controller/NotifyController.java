package com.liuche.user.controller;

import com.liuche.common.enums.ExceptionCode;
import com.liuche.common.exception.BusinessException;
import com.liuche.common.util.CheckUtil;
import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.JsonData;
import com.liuche.user.constant.RedisConstant;
import com.liuche.user.util.SendMsgUtil;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private SendMsgUtil sendMsgUtil;
    private static final Long ONE_MINUTE = 60 * 1000L;

    @ApiOperation("获取图形验证码")
    @GetMapping("/captcha/{id}")
    public void getCaptcha(@ApiParam(value = "0是注册的图形验证，1是登录的图形验证") @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // png类型
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        // 获取验证码的字符
        String code = captcha.text();
        // 将验证码写入redis
        String captchaKey = CommonUtil.getCaptchaKey(request);
        String prev;
        // 校验是什么品种的图形验证码
        if (id == 0)
            prev = RedisConstant.USER_REGISTER_CODE_IMG_REDIS_KEY;
        else
            prev = RedisConstant.USER_LOGIN_CODE_IMG_REDIS_KEY;
        stringRedisTemplate.opsForValue().set(prev + captchaKey, code, RedisConstant.USER_REGISTER_CODE_REDIS_OUT_TIME, TimeUnit.MILLISECONDS);
        log.info("captchaKey:" + captchaKey);
        log.info("验证码：" + code);
        // 输出验证码
        captcha.out(response.getOutputStream());
    }

    /**
     * 发送邮箱验证码
     *
     * @param to
     * @param captchaCode
     * @param request
     * @return
     */
    @ApiOperation("发送六位随机验证码至用户邮箱")
    @GetMapping("/send-code")
    public JsonData sendCode(String to, String captchaCode, HttpServletRequest request) {
        // 校验参数是否符合要求
        if (StringUtils.isAnyBlank(to, captchaCode)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR);
        }
        // 验证邮箱格式是否正确
        if (!CheckUtil.isEmail(to)) {
            throw new BusinessException(ExceptionCode.PARAMS_ERROR, "邮箱格式不符合规范");
        }
        // 验证验证码是否正确
        String captchaKey = CommonUtil.getCaptchaKey(request);
        String str = stringRedisTemplate.opsForValue().get(RedisConstant.USER_REGISTER_CODE_IMG_REDIS_KEY + captchaKey);
        if (StringUtils.isBlank(str) || !str.equals(captchaCode)) {
            throw new BusinessException(ExceptionCode.CODE_ERROR, "验证码错误");
        }
        // 校验是否在一分钟之内以发过验证码：在redis中查询邮箱验证码，查到：判断时间是否在一分钟之内发过？因为这个验证码有效时间为3分钟。 没有查到直接发送验证码
        String flagCode = stringRedisTemplate.opsForValue().get(RedisConstant.USER_REGISTER_CODE_MAIL_REDIS_KEY + captchaKey);
        if (!StringUtils.isBlank(flagCode)) {
            // 得到发送的时间戳
            long oldTime = Long.parseLong(flagCode.split("_")[1]);
            if ((System.currentTimeMillis() - oldTime) < ONE_MINUTE) {
                return JsonData.error("请勿重复发送验证码");
            }
        }
        // 发送随机的六位验证码至用户邮箱，并保存到redis缓存，
        String code = CommonUtil.getRandomCode();
        // 保存redis的code拼接一个时间戳用来在后面校验是否在一分钟以内发送重复的请求
        String redisCode = code + "_" + System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set(RedisConstant.USER_REGISTER_CODE_MAIL_REDIS_KEY + captchaKey, redisCode, RedisConstant.USER_REGISTER_CODE_REDIS_OUT_TIME, TimeUnit.MILLISECONDS);
        sendMsgUtil.sendMsg(to, "刘彻商城注册验证码", "您的验证码为：" + code + "。为了您和家人的安全，请勿外泄！");
        return JsonData.ok("验证码已发送");
    }
}
