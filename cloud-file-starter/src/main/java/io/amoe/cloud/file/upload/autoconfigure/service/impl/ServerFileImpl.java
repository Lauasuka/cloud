package io.amoe.cloud.file.upload.autoconfigure.service.impl;

import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.file.upload.autoconfigure.service.AbstractFileOperation;
import io.amoe.cloud.file.upload.autoconfigure.service.IFileOperatingStrategy;
import io.amoe.cloud.file.upload.autoconfigure.service.callback.IUploadFileCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * ServerFileImpl
 *
 * @author Amoe
 * @date 2021/3/2
 */
@Component
public class ServerFileImpl extends AbstractFileOperation implements IFileOperatingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerFileImpl.class);

    private static final String DEFAULT_SERVER_PATH = System.getProperty("java.io.tmpdir");
    private static final String DEFAULT_DOMAIN = "localhost";

    private FileUploadProperties.Server config = null;

    @Override
    protected void setConfig(FileUploadProperties properties) {
        final FileUploadProperties.Server server = properties.getServer();
        if (server == null) {
            throw new RuntimeException("Cloud file upload server config is not set");
        }
        config = server;
    }

    @Override
    protected FileUploadType getFileUploadType() {
        return FileUploadType.SERVER;
    }

    @Override
    public UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback) {
        return null;
    }

    @Override
    public UploadFile doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback) {
        return null;
    }
}
