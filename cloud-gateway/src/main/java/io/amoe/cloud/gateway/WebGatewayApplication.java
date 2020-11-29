package io.amoe.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Amoe
 * @date 2020/4/17 17:27
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.amoe.cloud"})
public class WebGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebGatewayApplication.class, args);
    }
}
