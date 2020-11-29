package io.amoe.cloud.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.amoe.cloud.account.entity.SysUser;

import java.io.Serializable;

public interface ISysUserService extends IService<SysUser> {
    IPage<SysUser> getUserPage(Long currentPage, Long pageSize);

    SysUser addUser(SysUser user);

    SysUser getById(Serializable id);

    SysUser getByAccount(String account);

    SysUser getByAccountAndPassword(String account, String password);

    void delUser(Long id);
}
