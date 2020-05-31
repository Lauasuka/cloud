package io.amoe.cloud.account.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysFile;
import io.amoe.cloud.account.mapper.SysFileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Amoe
 * @date 2020/7/24 16:28
 */
@Service
public class SysFileService extends ServiceImpl<SysFileMapper, SysFile> {
    @Transactional
    public void saveIfHashAbsent(SysFile file) {
        if (null == getByHash(file.getHash())) {
            save(file);
        }
    }

    public SysFile getByHash(String hash) {
        return lambdaQuery().eq(SysFile::getHash, hash).one();
    }
}
