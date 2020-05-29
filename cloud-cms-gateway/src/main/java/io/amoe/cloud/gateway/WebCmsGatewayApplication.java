package io.amoe.cloud.gateway;

import io.amoe.cloud.gateway.filter.LoggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author Amoe
 * @date 2020/4/17 17:27
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.amoe.cloud"})
public class WebCmsGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebCmsGatewayApplication.class, args);
    }

    @Bean
    public LoggerFilter loggerFilter() {
        return new LoggerFilter();
    }
}
