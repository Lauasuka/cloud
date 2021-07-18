package io.amoe.cloud.file.upload.autoconfigure.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.amoe.cloud.constant.SymbolConstants;
import io.amoe.cloud.file.upload.autoconfigure.config.FileUploadProperties;
import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadResponseStatus;
import io.amoe.cloud.file.upload.autoconfigure.enums.FileUploadType;
import io.amoe.cloud.file.upload.autoconfigure.exceptions.FileUploadException;
import io.amoe.cloud.file.upload.autoconfigure.service.AbstractFileOperation;
import io.amoe.cloud.file.upload.autoconfigure.service.IFileOperatingStrategy;
import io.amoe.cloud.file.upload.autoconfigure.service.callback.IUploadFileCallback;
import io.amoe.cloud.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Lauasuka
 */
@Slf4j
@Component
public class AliyunFileImpl extends AbstractFileOperation implements IFileOperatingStrategy {
    private FileUploadProperties.Aliyun config = null;

    @Override
    protected FileUploadType getFileUploadType() {
        return FileUploadType.ALIYUN_OSS;
    }

    @Override
    protected void checkAndInitConfig(FileUploadProperties properties) {
        final FileUploadProperties.Aliyun aliyun = properties.getAliyun();
        if (aliyun == null) {
            throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
        }
        if (StringUtils.isBlank(aliyun.getKey())) {
            log.error("Cloud file upload aliyun config [key] is not set!");
            throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
        }
        if (StringUtils.isBlank(aliyun.getSecret())) {
            log.error("Cloud file upload aliyun config [secret] is not set!");
            throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
        }
        if (StringUtils.isBlank(aliyun.getBucket())) {
            log.error("Cloud file upload aliyun config [bucket] is not set!");
            throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
        }
        if (StringUtils.isBlank(aliyun.getObject())) {
            log.error("Cloud file upload aliyun config [object] is not set!");
            throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
        }
        if (StringUtils.isBlank(aliyun.getEndpointInternet())) {
            log.error("Cloud file upload aliyun config [endpoint-internet] is not set!");
            throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
        }
        if (aliyun.isIntranetEnabled()) {
            if (StringUtils.isBlank(aliyun.getEndpointIntranet())) {
                log.error("Cloud file upload aliyun config [endpoint-intranet] is not set!");
                throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_CONFIG_ERROR);
            }
        }
        config = aliyun;
    }

    @Override
    public UploadFile doUploadFile(File file, IUploadFileCallback callback) throws IOException {
        return doUploadFileWithFolder(file, null, null, callback);
    }

    @Override
    public UploadFile doUploadFile(File file, String fileName, IUploadFileCallback callback) throws IOException {
        return doUploadFileWithFolder(file, fileName, null, callback);
    }

    @Override
    public UploadFile doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback) throws IOException {
        OSS client = null;
        try {
            client = new OSSClientBuilder().build(config.getEndpointInternet(), config.getKey(), config.getSecret());
            final FileBasicInfo fbi = initFileBasicInfo(file, fileName);

            String fullObject = config.getObject();
            if (StringUtils.isBlank(folder)) {
                fullObject = fullObject.concat(SymbolConstants.SLASH).concat(fbi.getFileName());
            } else {
                fullObject = fullObject.concat(SymbolConstants.SLASH).concat(folder).concat(SymbolConstants.SLASH).concat(fileName);
            }

            final PutObjectRequest request = new PutObjectRequest(config.getBucket(), fullObject, file);
            final PutObjectResult result = client.putObject(request);
            log.info("File upload to Aliyun result: RequestId:{}, ETag:{}, VersionId:{}, ClientCRC:{}, ServerCRC:{}, Response:{}",
                    result.getRequestId(), result.getETag(), result.getVersionId(), result.getClientCRC(), result.getServerCRC(), result.getResponse());
            final ResponseMessage response = result.getResponse();
            if (response == null || !response.isSuccessful()) {
                log.error("File upload to Aliyun error: {}, full message: {}", response == null ? "Response is null" : response.getErrorResponseAsString(), response);
                throw new FileUploadException(FileUploadResponseStatus.UPLOAD_FILE_ERROR);
            }
            log.info("File upload to Aliyun response: {}", response);
            UploadFile dto = new UploadFile();
            dto.setIntranetUrl(config.getEndpointInternet().concat(SymbolConstants.SLASH).concat(fullObject));
            dto.setInternetUrl(config.getEndpointInternet().concat(SymbolConstants.SLASH).concat(fullObject));
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
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }
}
