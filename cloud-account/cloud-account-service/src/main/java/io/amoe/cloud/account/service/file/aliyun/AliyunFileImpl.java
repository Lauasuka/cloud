package io.amoe.cloud.account.service.file.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import io.amoe.cloud.account.dto.UploadFileDTO;
import io.amoe.cloud.account.service.file.IFileOperatingStrategy;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.BizException;
import io.amoe.cloud.tools.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author Amoe
 * @date 2020/7/24 17:45
 */
@Slf4j
@Component
public class AliyunFileImpl implements IFileOperatingStrategy {
    @Resource
    private AliyunProperties properties;

    private OSS getClient() {
        if (properties.isIntranetEnabled()) {
            return new OSSClientBuilder().build(properties.getEndpointIntranet(), properties.getKey(), properties.getSecret());
        }
        return new OSSClientBuilder().build(properties.getEndpointInternet(), properties.getKey(), properties.getSecret());
    }

    private String genUrl(String endpoint, String fileName) {
        return HTTPS + properties.getBucket() +
                WEB_DOT +
                endpoint +
                WEB_SEPARATOR +
                properties.getObject() +
                WEB_SEPARATOR +
                fileName;
    }

    @Override
    public UploadFileDTO doUploadFile(File file, String fileName, IUploadFileCallback callback) {
        OSS client = getClient();
        try (FileInputStream fis = new FileInputStream(file)) {
            String fileType = FileUtils.getFileType(fis);
            if (StringUtils.isBlank(fileType)) {
                throw new BizException(BizResponseStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            String originalFilename = file.getName();
            String fullFileName = fileName + WEB_DOT + fileType;

            String objectKey = properties.getObject() + WEB_SEPARATOR + fullFileName;
            PutObjectResult result = client.putObject(properties.getBucket(), objectKey, file);

            UploadFileDTO dto = new UploadFileDTO();
            dto.setInternetUrl(genUrl(properties.getEndpointInternet(), fullFileName));
            dto.setIntranetUrl(genUrl(properties.getEndpointIntranet(), fullFileName));
            dto.setName(fullFileName);
            dto.setOriginalName(originalFilename);
            dto.setFileSize(file.length());
            dto.setFileHash(result.getETag());
            dto.setType(fileType);

            if (FileUtils.isImage(fileType)) {
                BufferedImage image = ImageIO.read(file);
                dto.setImageHeight(image.getHeight());
                dto.setImageWidth(image.getWidth());
            }

            try {
                if (callback != null) {
                    callback.doCallback(dto);
                }
            } catch (Exception e) {
                log.error("File upload call back run with error,please check,cause [{}] [{}]", e.getClass().getName(), e.getMessage());
            }

            return dto;
        } catch (Exception e) {
            if (e instanceof BizException) {
                throw (BizException)e;
            }
            log.error("File upload error, cause [{}] [{}]", e.getClass().getName(), e.getMessage());
            throw new BizException(BizResponseStatus.ERROR);
        }
    }

    @Override
    public UploadFileDTO doUploadFileWithFolder(File file, String fileName, String folder, IUploadFileCallback callback) {
        fileName = folder + WEB_SEPARATOR + fileName;
        return this.doUploadFile(file, fileName, callback);
    }

    @Override
    public void doDownloadFile(IDownloadFileCallback callback) {

    }

    @Override
    public void doDeleteFile(IDeleteFileCallback callback) {

    }

    @Override
    public void listFiles() {

    }
}
