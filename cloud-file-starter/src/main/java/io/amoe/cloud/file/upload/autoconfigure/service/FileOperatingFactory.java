package io.amoe.cloud.file.upload.autoconfigure.service;


import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.BizException;
import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.file.upload.autoconfigure.service.callback.IUploadFileCallback;
import io.amoe.cloud.tools.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * FileOperatingFactory
 *
 * @author 519001
 * @date 2021/3/2
 */
@Slf4j
@DependsOn("SpringContextUtils")
public class FileOperatingFactory implements InitializingBean {
    private static final String DEFAULT_SERVER_PATH = System.getProperty("java.io.tmpdir");
    private static final String DEFAULT_DOMAIN = "localhost";

    private final FileUploadProperties properties;

    private Map<String, IFileOperatingStrategy> STRATEGIES = new HashMap<>(8);

    public FileOperatingFactory(FileUploadProperties properties) {
        this.properties = properties;
    }

    public UploadFile doUploadFile(File file, String fileName) {
        return doUploadFile(file, fileName, null);
    }

    public UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback) {
        Collection<IFileOperatingStrategy> values = STRATEGIES.values();
        for (IFileOperatingStrategy strategy : values) {
            if (strategy.getFileUploadType().equals(properties.getUploadType())) {
                return strategy.doUploadFile(file, fileName, callback);
            }
        }
        throw new BizException(BizResponseStatus.ERROR);
    }

    @Override
    public void afterPropertiesSet() {
        STRATEGIES = SpringContextUtils.getBeansByType(IFileOperatingStrategy.class);
        final FileUploadType uploadType = properties.getUploadType();
        if (uploadType == null) {
            properties.setUploadType(FileUploadType.SERVER);
            if (null == properties.getServer()) {
                final FileUploadProperties.Server server = new FileUploadProperties.Server(DEFAULT_SERVER_PATH, DEFAULT_DOMAIN);
                properties.setServer(server);
            }
        }
    }
}
