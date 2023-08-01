package com.liuche.common.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 刘彻
 * @Date 2023/8/1 18:29
 * @PackageName: com.liuche.common.config
 * @ClassName: RedissonConfig
 * @Description: TODO
 */
@Configuration
@Data
public class RedissonConfig {
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.redisson-database}")
    private int database;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s",host,port)).setDatabase(database).setPassword(password);
        // Sync and Async API
        return Redisson.create(config);
    }

}
