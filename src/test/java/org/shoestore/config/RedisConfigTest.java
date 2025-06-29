package org.shoestore.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void redis_연결_확인(){
        String ping = redisTemplate.getConnectionFactory().getConnection().ping();
        assertTrue(ping.contains("PONG"));
    }
}