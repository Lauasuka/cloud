package io.amoe.cloud.account.service.file.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Amoe
 * @date 2020/7/28 14:03
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun")
@PropertySource(value = "classpath:config.properties", encoding = "utf-8")
public class AliyunProperties {
    private boolean intranetEnabled;
    private String endpointInternet;
    private String endpointIntranet;
    private String key;
    private String secret;
    private String bucket;
    private String object;
}

