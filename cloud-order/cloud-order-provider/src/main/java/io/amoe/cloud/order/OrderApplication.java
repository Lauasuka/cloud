package io.amoe.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Amoe
 * @date 2020/4/10 11:22
 */
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"io.amoe.cloud"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
