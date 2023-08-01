package com.liuche.coupon.config;

import com.liuche.common.intercepter.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                        "/api/*/coupon/list/**",
                        "/api/*/coupon/init-coupon/**"
                );
    }
}
