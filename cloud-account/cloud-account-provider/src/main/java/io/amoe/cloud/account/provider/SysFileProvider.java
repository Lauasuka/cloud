package io.amoe.cloud.account.provider;

import io.amoe.cloud.account.entity.SysFile;
import io.amoe.cloud.account.service.ISysFileService;
import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.enums.StatusType;
import io.amoe.cloud.exception.BizException;
import io.amoe.cloud.file.upload.autoconfigure.entity.UploadFile;
import io.amoe.cloud.file.upload.autoconfigure.service.FileOperatingFactory;
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
    private ISysFileService sysFileService;

    @Autowired
    private FileOperatingFactory fileOperatingFactory;

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<UploadFile> fileUpload(@RequestPart("file") MultipartFile file) throws IOException {
        if (file == null) {
            throw new BizException(BizResponseStatus.PARAM_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = RandomStringUtils.randomAlphanumeric(32);

        String tempFilePath = TEMP_PATH + originalFilename;
        File tempFile = new File(tempFilePath);
        file.transferTo(tempFile);
        UploadFile fileEntry = fileOperatingFactory.doUploadFile(tempFile);
        return success(fileEntry);
    }
}
