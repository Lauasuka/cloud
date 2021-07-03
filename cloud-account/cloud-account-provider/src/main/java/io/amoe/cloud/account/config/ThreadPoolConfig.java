package io.amoe.cloud.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolConfig
 *
 * @author Amoe
 * @date 2021/1/20
 */
@Configuration
public class ThreadPoolConfig {

    @Resource
    private ThreadPoolProperties properties;

    @Bean(CloudThreadPoolBeanName.CLOUD_POOL)
    public ThreadPoolTaskExecutor cloudThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCoreSize());
        executor.setMaxPoolSize(properties.maxSize);
        executor.setKeepAliveSeconds(properties.getAliveSeconds());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setThreadNamePrefix(properties.getPrefixName());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    @Data
    @Component
    @EnableConfigurationProperties
    @ConfigurationProperties(prefix = "thread.pool")
    @PropertySource(value = "classpath:config.properties", encoding = "utf-8")
    static class ThreadPoolProperties{
        private int coreSize;
        private int maxSize;
        private int aliveSeconds;
        private String prefixName;
        private int queueCapacity;
    }

    public interface CloudThreadPoolBeanName{
        String CLOUD_POOL = "account.thread.pool";
    }
}
