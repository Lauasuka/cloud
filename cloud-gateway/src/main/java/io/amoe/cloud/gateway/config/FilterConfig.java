package io.amoe.cloud.gateway.config;

import io.amoe.cloud.gateway.filter.LoggerFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Amoe
 * @date 2020/8/6 16:48
 */
@Configuration
public class FilterConfig {
    @Bean
    public LoggerFilter loggerFilter() {
        return new LoggerFilter();
    }
}
