package io.amoe.cloud.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysFile;
import io.amoe.cloud.account.mapper.SysFileMapper;
import io.amoe.cloud.account.service.ISysFileService;
import io.amoe.cloud.enums.StatusType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Amoe
 * @date 2020/7/24 16:28
 */
@Service
public class SysFileService extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {
    @Transactional
    public void saveIfHashAbsent(SysFile file) {
        SysFile sysFile = getByHash(file.getHash());
        if (null == sysFile) {
            save(file);
        } else {
            sysFile.setInternetUrl(file.getInternetUrl());
            sysFile.setIntranetUrl(file.getIntranetUrl());
            sysFile.setName(file.getName());
            sysFile.setStatus(StatusType.ENABLE.name());
            updateById(sysFile);
        }
    }

    public SysFile getByHash(String hash) {
        return lambdaQuery().eq(SysFile::getHash, hash).one();
    }
}
