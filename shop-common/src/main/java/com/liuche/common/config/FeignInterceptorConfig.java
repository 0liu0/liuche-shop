package com.liuche.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.encoding.BaseRequestInterceptor;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 刘彻
 * @Date 2023/8/6 20:14
 * @PackageName: com.liuche.common.config
 * @ClassName: FeignInterceptorConfig
 * @Description: TODO
 */
@Configuration
@Slf4j
public class FeignInterceptorConfig {
    /**
     * 解决feign调用丢失token的问题，配置feign的拦截器，加上token即可
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if(attributes!=null){
                HttpServletRequest request = attributes.getRequest();
                log.info(request.getHeaderNames().toString());
                template.header("token", request.getHeader("token"));
            }else {
                log.warn("requestInterceptor获取Header空指针异常");
            }
        };
    }

}
