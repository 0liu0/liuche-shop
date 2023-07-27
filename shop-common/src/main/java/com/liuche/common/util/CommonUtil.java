package com.liuche.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @Author 刘彻
 * @Date 2023/7/25 15:45
 * @PackageName: com.liuche.common.util
 * @ClassName: CommonUtil
 * @Description: TODO
 */
@Slf4j
public class CommonUtil {
    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static String MD5(String data) {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;
    }

    public static String getRandomCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }

    /**
     * 生成指定长度随机字母和数字
     *
     * @param length
     * @return
     */
    private static final String ALL_CHAR_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String getStringNumRandom(int length) {
        //生成随机数字和字母,
        Random random = new Random();
        StringBuilder saltString = new StringBuilder(length);
        for (int i = 1; i <= length; ++i) {
            saltString.append(ALL_CHAR_NUM.charAt(random.nextInt(ALL_CHAR_NUM.length())));
        }
        return saltString.toString();
    }

    public static String getCaptchaKey(HttpServletRequest request) {
        // 根据用户的ip以及浏览器指纹获取唯一值
        String ipAddr = CommonUtil.getRemoteIp(request);
        String userAgent = request.getHeader("User-Agent");// 相当于浏览器指纹
        // userAgent这个信息很长不适合做key，使用md5加密变短点儿返回即可，保证了key的唯一性
        return CommonUtil.MD5(userAgent + ipAddr);
    }

    public static void sendJsonMessage(HttpServletResponse response, Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(objectMapper.writeValueAsString(obj));
            response.flushBuffer();
        } catch (IOException e) {
            log.warn("响应json数据给前端异常：{}", e);
        }
    }
}
