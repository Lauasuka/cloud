package io.amoe.cloud.account.service.file;

import io.amoe.cloud.account.dto.UploadFileDTO;

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

    UploadFileDTO doUploadFile(File file, String fileName, IUploadFileCallback callback);

    UploadFileDTO doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback);


    void doDownloadFile(IDownloadFileCallback callback);

    void doDeleteFile(IDeleteFileCallback callback);

    void listFiles();

    interface IUploadFileCallback {
        void doCallback(UploadFileDTO file);
    }

    interface IDownloadFileCallback {
        void doCallback();
    }

    interface IDeleteFileCallback {
        void doCallback();
    }
}
