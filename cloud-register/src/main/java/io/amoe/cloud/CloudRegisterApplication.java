package io.amoe.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author amoe
 */
@EnableEurekaServer
@SpringBootApplication
public class CloudRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudRegisterApplication.class, args);
    }

}
