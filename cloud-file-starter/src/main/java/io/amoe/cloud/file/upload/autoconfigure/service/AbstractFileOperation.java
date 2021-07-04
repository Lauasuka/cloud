package io.amoe.cloud.file.upload.autoconfigure.service;

import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;

public abstract class AbstractFileOperation {
    protected abstract FileUploadType getFileUploadType();

    protected abstract void setConfig(FileUploadProperties properties);
}
