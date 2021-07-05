package io.amoe.cloud.file.upload.autoconfigure.service.impl;

import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.file.upload.autoconfigure.service.AbstractFileOperation;
import io.amoe.cloud.file.upload.autoconfigure.service.IFileOperatingStrategy;
import io.amoe.cloud.file.upload.autoconfigure.service.callback.IUploadFileCallback;
import io.amoe.cloud.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * ServerFileImpl
 *
 * @author Amoe
 * @date 2021/3/2
 */
@Slf4j
@Component
public class ServerFileImpl extends AbstractFileOperation implements IFileOperatingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerFileImpl.class);

    private static final String DEFAULT_SERVER_PATH = System.getProperty("java.io.tmpdir");

    private FileUploadProperties.Server config = null;

    @Override
    protected void setConfig(FileUploadProperties properties) {
        final FileUploadProperties.Server server = properties.getServer();
        if (server == null) {
            throw new RuntimeException("Cloud file upload server config is not set");
        }
        if (StringUtils.isBlank(server.getStorePath())) {
            log.warn("Cloud file upload server config is not set store path,will set to default path {}", DEFAULT_SERVER_PATH);
            server.setStorePath(DEFAULT_SERVER_PATH);
        }
        if (!server.getStorePath().endsWith(File.separator)) {
            server.setStorePath(server.getStorePath() + File.separator);
        }
        File storePath = new File(server.getStorePath());
        if (!storePath.exists()) {
            final boolean mkdirs = storePath.mkdirs();
            if (mkdirs) {
                log.info("Make dir {} done", server.getStorePath());
            }
        }
        if (StringUtils.isBlank(server.getPrefixDomain())) {
            throw new NullPointerException("Cloud file upload server config is not set prefix domain");
        }
        config = server;
    }

    @Override
    protected FileUploadType getFileUploadType() {
        return FileUploadType.SERVER;
    }

    @Override
    public UploadFile doUploadFile(File file, IUploadFileCallback callback) throws IOException {
        return doUploadFile(file, null, callback);
    }

    @Override
    public UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback) throws IOException {
        Assert.notNull(file, "Upload file should not be null");

        final String fileHash = DigestUtils.md5DigestAsHex(new FileInputStream(file));
        final String fileType = FileUtils.getFileType(file);
        if (StringUtils.isBlank(fileName)) {
            fileName = fileHash.concat(".").concat(fileType);
        }
        if (!fileName.contains(".")) {
            fileName = fileName.concat(".").concat(fileType);
        }
        String newFilePath = config.getStorePath().concat(fileName);
        final int fileSize = FileCopyUtils.copy(file, new File(newFilePath));

        UploadFile dto = new UploadFile();
        dto.setIntranetUrl(config.getPrefixDomain().concat("/").concat(fileName));
        dto.setInternetUrl(config.getPrefixDomain().concat("/").concat(fileName));
        dto.setFileHash(fileHash);
        dto.setFileSize((long) fileSize);
        dto.setName(fileName);
        dto.setType(fileType);
        dto.setOriginalName(file.getName());
        if (FileUtils.isImage(fileType)) {
            BufferedImage image = ImageIO.read(file);
            dto.setImageWidth(image.getWidth());
            dto.setImageHeight(image.getHeight());
        }
        try {
            if (callback != null) {
                callback.doCallback(dto);
            }
        } catch (Exception e) {
            log.error("File upload callback run with error,please check,cause [{}] [{}]", e.getClass().getName(), e.getMessage(), e);
        }
        return dto;
    }

    @Override
    public UploadFile doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback) {
        return null;
    }
}
