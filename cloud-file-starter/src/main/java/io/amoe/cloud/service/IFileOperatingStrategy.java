package io.amoe.cloud.service;

import io.amoe.cloud.entity.UploadFile;
import io.amoe.cloud.service.callback.IDeleteFileCallback;
import io.amoe.cloud.service.callback.IDownloadFileCallback;
import io.amoe.cloud.service.callback.IUploadFileCallback;

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

    UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback);

    UploadFile doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback);

    void doDownloadFile(IDownloadFileCallback callback);

    void doDeleteFile(IDeleteFileCallback callback);

    void listFiles();
}
