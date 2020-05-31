package io.amoe.cloud.account.provider;

import io.amoe.cloud.account.dto.UploadFileDTO;
import io.amoe.cloud.account.entity.SysFile;
import io.amoe.cloud.account.service.SysFileService;
import io.amoe.cloud.account.service.file.IFileOperatingStrategy;
import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.enums.StatusType;
import io.amoe.cloud.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Amoe
 * @date 2020/7/24 16:32
 */
@Slf4j
@RestController
@RequestMapping("file")
public class SysFileProvider extends AbstractProvider {

    private static final String TEMP_PATH = System.getProperty("java.io.tmpdir");

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private IFileOperatingStrategy fileStrategy;

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<UploadFileDTO> fileUpload(@RequestPart("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new BizException(BizResponseStatus.PARAM_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = RandomStringUtils.randomAlphanumeric(32);

        String tempFilePath = TEMP_PATH + originalFilename;
        File tempFile = new File(tempFilePath);
        file.transferTo(tempFile);
        UploadFileDTO fileEntry = fileStrategy.doUploadFile(tempFile, fileName, (dto) -> {
            SysFile sysFile = new SysFile();
            sysFile.setName(dto.getName());
            sysFile.setSize(dto.getFileSize());
            sysFile.setImageWidth(dto.getImageWidth());
            sysFile.setImageHeight(dto.getImageHeight());
            sysFile.setType(dto.getType());
            sysFile.setHash(dto.getFileHash());
            sysFile.setInternetUrl(dto.getInternetUrl());
            sysFile.setIntranetUrl(dto.getIntranetUrl());
            sysFile.setStatus(StatusType.ENABLE.name());
            sysFileService.saveIfHashAbsent(sysFile);
        });
        return success(fileEntry);
    }
}
