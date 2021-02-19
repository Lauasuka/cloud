package io.amoe.cloud.properties;

import io.amoe.cloud.enums.FileUploadType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FileComponentConfig
 *
 * @author 519001
 * @date 2021/2/19
 */
@ConfigurationProperties(prefix = "cloud.file")
public class FileUploadProperties {
    private FileUploadType defaultUploadType;
    private FileUploadType uploadType;
}
