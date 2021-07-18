package io.amoe.cloud.file.upload.autoconfigure.exceptions;

import io.amoe.cloud.enums.IStatusEnum;
import io.amoe.cloud.exception.AbstractException;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadResponseStatus;

/**
 * @author lauasuka
 */
public class FileUploadException extends AbstractException {
    public FileUploadException() {
        super(FileUploadResponseStatus.UPLOAD_FILE_ERROR);
    }

    public FileUploadException(IStatusEnum status) {
        super(status);
    }
}
