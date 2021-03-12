package io.amoe.cloud.product;

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
@SpringBootApplication(scanBasePackages = {"io.amoe"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
