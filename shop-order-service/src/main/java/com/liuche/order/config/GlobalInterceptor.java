package com.liuche.order.config;

import com.liuche.common.intercepter.LoginInterceptor;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 刘彻
 * @Date 2023/7/27 17:07
 * @PackageName: com.liuche.common.config
 * @ClassName: GlobalInterceptor
 * @Description: 全局拦截器
 */
@Configuration
@Slf4j
public class GlobalInterceptor implements WebMvcConfigurer {
    @Autowired
    public LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/*/order/callback/**",
                        "/api/*/order/query_state/**",
                        "/api/*/order/test/**"
                );
    }
}
