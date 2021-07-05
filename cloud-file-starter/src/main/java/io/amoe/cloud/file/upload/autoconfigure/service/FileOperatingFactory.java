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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FileOperatingFactory
 *
 * @author Amoe
 * @date 2021/3/2
 */
@Slf4j
@Component
@DependsOn("SpringContextUtils")
public class FileOperatingFactory implements InitializingBean {


    private final FileUploadProperties properties;

    private Map<String, IFileOperatingStrategy> strategies = new ConcurrentHashMap<>(8);

    public FileOperatingFactory(FileUploadProperties properties) {
        this.properties = properties;
    }

    public UploadFile doUploadFile(File file) throws IOException {
        return doUploadFile(file, null, null);
    }

    public UploadFile doUploadFile(File file, String fileName) throws IOException {
        return doUploadFile(file, fileName, null);
    }

    public UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback) throws IOException {
        Collection<IFileOperatingStrategy> values = strategies.values();
        for (IFileOperatingStrategy strategy : values) {
            AbstractFileOperation operation = (AbstractFileOperation) strategy;
            if (operation.getFileUploadType().equals(properties.getUploadType())) {
                return strategy.doUploadFile(file, fileName, callback);
            }
        }
        throw new BizException(BizResponseStatus.ERROR);
    }

    @Override
    public void afterPropertiesSet() {
        strategies = SpringContextUtils.getBeansByType(IFileOperatingStrategy.class);
        final FileUploadType type = properties.getUploadType();
        if (type == null) {
            throw new RuntimeException("File upload type is not config, Please check your config file");
        }
        if (strategies == null || strategies.isEmpty()) {
            throw new RuntimeException("File upload strategy was not init");
        }
        final Collection<IFileOperatingStrategy> values = strategies.values();
        values.forEach(item -> {
            AbstractFileOperation operation = (AbstractFileOperation) item;
            if (type.equals(operation.getFileUploadType())) {
                operation.setConfig(properties);
            }
        });
    }
}
