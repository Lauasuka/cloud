package io.amoe.cloud.file.upload.autoconfigure.enums;

import io.amoe.cloud.enums.IStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Lauasuka
 */
@Getter
@AllArgsConstructor
public enum FileUploadResponseStatus implements IStatusEnum {
    UPLOAD_FILE_ERROR(500, "上传文件错误"),

    UPLOAD_FILE_NULL(400, "上传文件不能为空"),
    UPLOAD_FILE_STORE_FOLDER_NULL(400, "上传文件存储目录不能为空"),
    UPLOAD_FILE_STORE_PATH_CREATE_ERROR(400, "上传文件存储目录创建失败")
    ;

    private final Integer code;
    private final String message;

    @Override
    public String getName() {
        return name();
    }
}
