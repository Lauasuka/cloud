package io.amoe.cloud.service.callback;

import io.amoe.cloud.entity.UploadFile;

/**
 * IUploadFileCallback
 *
 * @author 519001
 * @date 2021/3/2
 */
public interface IUploadFileCallback {
    void doCallback(UploadFile file);
}
