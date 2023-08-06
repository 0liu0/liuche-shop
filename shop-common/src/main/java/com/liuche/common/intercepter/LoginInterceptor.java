package com.liuche.common.intercepter;

import com.liuche.common.util.CommonUtil;
import com.liuche.common.util.JWTUtil;
import com.liuche.common.util.JsonData;
import com.liuche.common.util.RequestContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 刘彻
 * @Date 2023/7/27 16:45
 * @PackageName: com.liuche.common.interceptor
 * @ClassName: LoginInterceptor
 * @Description: 登录拦截器
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String accessToken = request.getHeader("token");
            if (accessToken == null) {
                accessToken = request.getParameter("token");
            }
            if (StringUtils.isNotBlank(accessToken)) {
                Claims claims = JWTUtil.checkJWT(accessToken); // 解析token解析不了，返回null
                if (claims == null) {
                    //告诉登录过期，重新登录
                    CommonUtil.sendJsonMessage(response, JsonData.error("登录过期，重新登录"));
                    return false;
                }

                long id = Long.parseLong(claims.get("id").toString());
                String ip = (String) claims.get("ip");
                String mail = (String) claims.get("mail");
                String name = (String) claims.get("name");
                // 判断地址是否正确
                String remoteIp = CommonUtil.getRemoteIp(request);
//                if (!remoteIp.equals(ip)) {
//                    log.info("ip==remoteIp?"+false);
//                    CommonUtil.sendJsonMessage(response, JsonData.error("当前登录已失效请重新登录"));
//                    return false;
//                }
                // 保存用户id到ThreadLocal
                RequestContext.setUserId(id);
                log.info("拦截到了如下信息");
                log.info("id:" + id);
                log.info("ip:" + ip);
                log.info("mail:" + mail);
                log.info("name:" + name);
                return true;

            }

        } catch (Exception e) {
            log.error("拦截器错误:{}", e);
        }
        CommonUtil.sendJsonMessage(response, JsonData.error("token不存在，请重新登录"));
        return false;

    }
}
