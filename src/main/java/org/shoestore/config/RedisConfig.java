package org.shoestore.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = String.format("redis://%s:%d", redisHost, redisPort);
        config.useSingleServer()
                .setAddress(address)
                .setDatabase(0); // 사용할 Redis DB 인덱스 (기본 0)
        return Redisson.create(config);
    }
}
