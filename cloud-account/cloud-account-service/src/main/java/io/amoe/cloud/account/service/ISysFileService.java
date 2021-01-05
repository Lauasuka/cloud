package io.amoe.cloud.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.amoe.cloud.account.entity.SysFile;

/**
 * @author Amoe
 */
public interface ISysFileService extends IService<SysFile> {
    /**
     * 文件hash不存在时保存
     * @param file 文件对象
     */
    void saveIfHashAbsent(SysFile file);

    /**
     * Get by hash value
     * @param hash Hash
     * @return {@link SysFile}
     */
    SysFile getByHash(String hash);
}
