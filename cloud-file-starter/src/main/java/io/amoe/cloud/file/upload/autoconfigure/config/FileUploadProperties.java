package io.amoe.cloud.file.upload.autoconfigure.config;

import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * FileComponentConfig
 *
 * @author Amoe
 * @date 2021/2/19
 */
@Data
@ConfigurationProperties(prefix = "cloud.file")
public class FileUploadProperties {
    private FileUploadType uploadType;
    private FileUploadProperties.Server server;
    private FileUploadProperties.Aliyun aliyun;

    public interface FileUploadPropertiesConfig extends Serializable {}

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Server implements FileUploadPropertiesConfig {
        private String storePath;
        private String prefixDomain;

        public String getStorePath() {
            return storePath;
        }

        public void setStorePath(String storePath) {
            this.storePath = storePath;
        }

        public String getPrefixDomain() {
            return prefixDomain;
        }

        public void setPrefixDomain(String prefixDomain) {
            this.prefixDomain = prefixDomain;
        }
    }

    public static class Aliyun implements FileUploadPropertiesConfig {
        private boolean intranetEnabled;
        private String endpointInternet;
        private String endpointIntranet;
        private String key;
        private String secret;
        private String bucket;
        private String object;

        public boolean isIntranetEnabled() {
            return intranetEnabled;
        }

        public void setIntranetEnabled(boolean intranetEnabled) {
            this.intranetEnabled = intranetEnabled;
        }

        public String getEndpointInternet() {
            return endpointInternet;
        }

        public void setEndpointInternet(String endpointInternet) {
            this.endpointInternet = endpointInternet;
        }

        public String getEndpointIntranet() {
            return endpointIntranet;
        }

        public void setEndpointIntranet(String endpointIntranet) {
            this.endpointIntranet = endpointIntranet;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }
    }
}
