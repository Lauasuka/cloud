package io.amoe.cloud.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysUser;
import io.amoe.cloud.account.mapper.SysUserMapper;
import io.amoe.cloud.account.service.ISysUserService;
import io.amoe.cloud.exception.BizException;
import io.amoe.cloud.tools.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

import static io.amoe.cloud.enums.BizResponseStatus.ACCOUNT_OR_PASSWORD_ERROR;
import static io.amoe.cloud.enums.BizResponseStatus.USER_ACCOUNT_EXIST;
import static io.amoe.cloud.enums.BizResponseStatus.USER_ACCOUNT_NOT_EXIST;

/**
 * @author Amoe
 * @date 2020/4/9 16:43
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public IPage<SysUser> getUserPage(Long currentPage, Long pageSize) {
        Page<SysUser> page = new Page<>(currentPage, pageSize);
        return this.page(page);
    }

    @Override
    public SysUser addUser(SysUser user) {
        String account = user.getAccount();
        SysUser tempUser = this.getByAccount(account);
        if (tempUser != null) {
            throw new BizException(USER_ACCOUNT_EXIST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        this.save(user);
        return user;
    }

    @Override
    public SysUser getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public SysUser getByAccount(String account) {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        return this.lambdaQuery().eq(SysUser::getAccount, account).one();
    }

    @Override
    public SysUser getByAccountAndPassword(String account, String password) {
        SysUser user = this.getByAccount(account);
        if (user == null) {
            throw new BizException(USER_ACCOUNT_NOT_EXIST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            throw new BizException(ACCOUNT_OR_PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delUser(Long id) {
        if (null == id || id < 0) {
            return;
        }
        SysUser userFromDb = this.getById(id);
        Optional.ofNullable(userFromDb).ifPresent(user -> this.removeById(id));
    }
}
