package io.amoe.cloud.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Amoe
 * @date 2020/4/10 11:22
 */
@EnableScheduling
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = "io.amoe.cloud")
@SpringBootApplication(scanBasePackages = {"io.amoe.cloud"})
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
