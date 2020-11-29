package io.amoe.cloud.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.amoe.cloud.account.entity.SysFile;

public interface ISysFileService extends IService<SysFile> {
    void saveIfHashAbsent(SysFile file);

    SysFile getByHash(String hash);
}
