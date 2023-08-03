package com.liuche.coupon;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @Author 刘彻
 * @Date 2023/7/28 20:51
 * @PackageName: com.liuche.coupon
 * @ClassName: CouponApplication
 * @Description: 主启动类
 */
@SpringBootApplication
@ComponentScan("com.liuche")
@MapperScan("com.liuche.coupon.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class CouponApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CouponApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("启动成功！！");
        log.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}