package com.liuche.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

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
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
