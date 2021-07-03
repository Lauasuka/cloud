package io.amoe.cloud.file.upload.autoconfigure.service.callback;


import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;

/**
 * IUploadFileCallback
 *
 * @author Amoe
 * @date 2021/3/2
 */
public interface IUploadFileCallback {
    void doCallback(UploadFile file);
}
