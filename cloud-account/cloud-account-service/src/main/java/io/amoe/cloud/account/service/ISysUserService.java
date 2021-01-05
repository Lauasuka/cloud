package io.amoe.cloud.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.amoe.cloud.account.entity.SysUser;

import java.io.Serializable;

/**
 * @author Amoe
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * Query system user paging data
     * @param currentPage current page index
     * @param pageSize Current page capacity
     * @return {@link IPage<SysUser>}
     */
    IPage<SysUser> getUserPage(Long currentPage, Long pageSize);

    /**
     * Adding system users
     * @param user {@link SysUser}
     * @return {@link SysUser}
     */
    SysUser addUser(SysUser user);

    /**
     * Querying users by user id
     * @param id id
     * @return {@link SysUser}
     */
    @Override
    SysUser getById(Serializable id);

    /**
     * Querying users by user account
     * @param account account
     * @return {@link SysUser}
     */
    SysUser getByAccount(String account);

    /**
     * Querying users by user account and password
     * @param account user account
     * @param password user password
     * @return {@link SysUser}
     */
    SysUser getByAccountAndPassword(String account, String password);

    /**
     * Delete user by id
     * @param id user id
     */
    void delUser(Long id);
}
