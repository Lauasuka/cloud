package io.amoe.cloud.file.upload.autoconfigure.service.impl;

import io.amoe.cloud.constant.SymbolConstants;
import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.file.upload.autoconfigure.service.AbstractFileOperation;
import io.amoe.cloud.file.upload.autoconfigure.service.IFileOperatingStrategy;
import io.amoe.cloud.file.upload.autoconfigure.service.callback.IUploadFileCallback;
import io.amoe.cloud.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ServerFileImpl
 *
 * @author Amoe
 * @date 2021/3/2
 */
@Slf4j
@Component
public class ServerFileImpl extends AbstractFileOperation implements IFileOperatingStrategy {
    private static final String DEFAULT_SERVER_PATH = System.getProperty("user.home").concat(File.separator).concat("Upload_File_Temp").concat(File.separator);

    private FileUploadProperties.Server config = null;

    @Override
    protected void checkAndInitConfig(FileUploadProperties properties) {
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
        return doUploadFileWithFolder(file, fileName, null, callback);
    }

    @Override
    public UploadFile doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback) throws IOException {
        Assert.notNull(file, "Upload file should not be null");

        // init filename
        final FileBasicInfo fbi = initFileBasicInfo(file, fileName);

        // check file store dir
        String fileStorePath = StringUtils.isBlank(folder) ? config.getStorePath() : config.getStorePath().concat(File.separator).concat(folder);
        if (!Files.exists(Paths.get(fileStorePath))) {
            Files.createDirectories(Paths.get(fileStorePath));
        }

        String newFilePath = fileStorePath.concat(File.separator).concat(fileName);
        log.info("File new store path is [{}]", newFilePath);
        final File newFile = new File(newFilePath);
        final int fileSize = FileCopyUtils.copy(file, newFile);

        String suffix = StringUtils.isBlank(folder) ? fileName : folder.concat(SymbolConstants.SLASH).concat(fileName);
        UploadFile dto = new UploadFile();
        dto.setIntranetUrl(config.getPrefixDomain().concat(SymbolConstants.SLASH).concat(suffix));
        dto.setInternetUrl(config.getPrefixDomain().concat(SymbolConstants.SLASH).concat(suffix));
        dto.setFileHash(fbi.getFileHash());
        dto.setFileSize(fbi.getFileSize());
        dto.setName(fbi.getFileName());
        dto.setType(fbi.getFileType());
        dto.setOriginalName(file.getName());
        if (FileUtils.isImage(fbi.getFileType())) {
            BufferedImage image = ImageIO.read(file);
            dto.setImageWidth(image.getWidth());
            dto.setImageHeight(image.getHeight());
        }
        try {
            if (callback != null) {
                callback.doCallback(dto);
            }
        } catch (Exception e) {
            log.warn("File upload callback run with error,please check,cause [{}] [{}]", e.getClass().getName(), e.getMessage(), e);
        }
        return dto;
    }
}
