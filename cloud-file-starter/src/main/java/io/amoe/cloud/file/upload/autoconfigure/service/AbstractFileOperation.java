package io.amoe.cloud.file.upload.autoconfigure.service;

import io.amoe.cloud.constant.SymbolConstants;
import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.tools.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractFileOperation {
    protected abstract FileUploadType getFileUploadType();

    protected abstract void checkAndInitConfig(FileUploadProperties properties);

    protected FileBasicInfo initFileBasicInfo(File file, String fileName) throws IOException {
        final String fileHash = DigestUtils.md5DigestAsHex(new FileInputStream(file));
        final String fileType = FileUtils.getFileType(file);
        if (StringUtils.isBlank(fileName)) {
            fileName = fileHash.concat(SymbolConstants.DOT).concat(fileType);
        }
        if (!fileName.contains(SymbolConstants.DOT)) {
            fileName = fileName.concat(SymbolConstants.DOT).concat(fileType);
        }
        final long fileSize = file.getTotalSpace();
        return new FileBasicInfo(fileSize, fileHash, fileType, fileName);
    }

    @Data
    @AllArgsConstructor
    protected static class FileBasicInfo implements Serializable {
        private final Long fileSize;
        private final String fileHash;
        private final String fileType;
        private final String fileName;
    }
}
