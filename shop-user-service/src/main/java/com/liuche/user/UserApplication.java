package com.liuche.user;

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
 * @Date 2023/7/23 22:51
 * @PackageName: com.liuche.user
 * @ClassName: UserApplication
 * @Description: User启动类
 */
@SpringBootApplication
@ComponentScan("com.liuche")
@MapperScan("com.liuche.user.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UserApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("启动成功！！");
        log.info("地址: \thttp://127.0.0.1:{}/doc.html", env.getProperty("server.port"));
    }
}
