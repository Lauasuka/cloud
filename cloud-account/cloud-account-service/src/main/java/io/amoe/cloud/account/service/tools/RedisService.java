package io.amoe.cloud.account.service.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Amoe
 * @date 2020/7/7 14:53
 */
@Component
public class RedisService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    GenericObjectPool<StatefulRedisConnection<String, String>> redisConnectionPool;

    @Autowired
    private ObjectMapper mapper;

    @Resource
    private RedisClient redisClient;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @PostConstruct
    public void init() {
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        this.redisConnectionPool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), poolConfig);
    }

    @PreDestroy
    public void shutdown() {
        Optional.ofNullable(redisConnectionPool).ifPresent(GenericObjectPool::close);
        Optional.ofNullable(redisClient).ifPresent(AbstractRedisClient::shutdown);
    }

    public String set(String key, String value) {
        return this.setex(key, value, -1);
    }

    public String set(String key, String value, long seconds) {
        return this.setex(key, value, seconds);
    }

    public <T> String set(String key, T value) {
        return this.set(key, value, -1);
    }

    public <T> String set(String key, T value, long seconds) {
        if (value != null) {
            try {
                String json = mapper.writeValueAsString(value);
                return this.set(key, json, seconds);
            } catch (JsonProcessingException e) {
                LOGGER.warn("entity to json string fail", e);
                return null;
            }
        }
        return null;
    }

    public String setex(String key, String value, long seconds) {
        if (seconds <= 0) {
            return executeSync(commands -> commands.set(key, value));
        }
        return executeSync(commands -> commands.setex(key, seconds, value));
    }

    public String get(String key) {
        return executeSync(commands -> commands.get(key));
    }

    public <T> T get(String key, Class<T> clazz) {
        String str = executeSync(commands -> commands.get(key));
        if (StringUtils.isNotBlank(str)) {
            try {
                return mapper.readValue(str, clazz);
            } catch (JsonProcessingException e) {
                LOGGER.warn("redis string entity to class fail", e);
                return null;
            }
        }
        return null;
    }

    public Long del(String... key) {
        return executeSync(commands -> commands.del(key));
    }

    public boolean exists(String... key) {
        Long length = executeSync(commands -> commands.exists(key));
        return length > 0;
    }

    public <V> V executeSync(SyncCommandCallback<V> callback) {
        try (StatefulRedisConnection<String, String> connection = redisConnectionPool.borrowObject()) {
            connection.setAutoFlushCommands(true);
            RedisCommands<String, String> commands = connection.sync();
            return callback.doInConnection(commands);
        } catch (Exception e) {
            LOGGER.warn("executeSync redis failed.", e);
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface SyncCommandCallback<T> {
        T doInConnection(RedisCommands<String, String> commands);
    }
}
