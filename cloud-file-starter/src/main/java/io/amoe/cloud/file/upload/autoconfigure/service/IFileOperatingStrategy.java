package io.amoe.cloud.file.upload.autoconfigure.service;


import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.file.upload.autoconfigure.service.callback.IUploadFileCallback;

import java.io.File;

/**
 * @author Amoe
 * @date 2020/7/24 17:00
 */
public interface IFileOperatingStrategy {
    String HTTPS = "https://";
    String HTTP = "http://";
    String WEB_SEPARATOR = "/";
    String WEB_DOT = ".";

    FileUploadType getFileUploadType();

    UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback);

    UploadFile doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback);
}