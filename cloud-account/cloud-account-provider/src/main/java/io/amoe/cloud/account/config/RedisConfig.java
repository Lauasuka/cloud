package io.amoe.cloud.account.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author Amoe
 * @date 2020/7/6 15:53
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public RedisClient initRedisClient() {
        RedisURI uri = RedisURI.Builder.redis(this.host, this.port)
                .withDatabase(database)
                .withPassword(password)
                .withTimeout(Duration.ofMillis(timeout))
                .build();
        return RedisClient.create(uri);
    }
}
