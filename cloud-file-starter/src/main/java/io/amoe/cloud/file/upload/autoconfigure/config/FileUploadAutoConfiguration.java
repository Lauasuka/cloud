package io.amoe.cloud.file.upload.autoconfigure.config;

import io.amoe.cloud.file.upload.autoconfigure.service.FileOperatingFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * FileUploadConfiguration
 *
 * @author 519001
 * @date 2021/3/2
 */
@Configuration
@ConditionalOnClass(FileOperatingFactory.class)
@EnableConfigurationProperties(FileUploadProperties.class)
public class FileUploadAutoConfiguration {

    @Resource
    private FileUploadProperties properties;

    @Bean
    @ConditionalOnMissingBean(FileOperatingFactory.class)
    public FileOperatingFactory getFileOperatingFactory() {
        return new FileOperatingFactory(properties);
    }
}
