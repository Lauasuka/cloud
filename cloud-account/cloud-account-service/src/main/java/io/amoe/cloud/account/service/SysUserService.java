package io.amoe.cloud.account.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysUser;
import io.amoe.cloud.account.mapper.SysUserMapper;
import io.amoe.cloud.exception.BizException;
import io.amoe.cloud.tools.EncryptUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static io.amoe.cloud.enums.BizResponseStatus.USER_ACCOUNT_EXIST;

/**
 * @author Amoe
 * @date 2020/4/9 16:43
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    public SysUser addUser(SysUser user) {
        String account = user.getAccount();
        SysUser tempUser = this.getByAccount(account);
        if (tempUser != null) {
            throw new BizException(USER_ACCOUNT_EXIST);
        }
        String salt = RandomStringUtils.randomAlphabetic(12);
        user.setSalt(salt);
        user.setPassword(EncryptUtils.getEncryptPassword(user.getPassword(), salt));
        this.save(user);
        return user;
    }


    public SysUser getByAccount(String account) {
        if (StringUtils.isBlank(account)) {
            return null;
        }
        return this.lambdaQuery().eq(SysUser::getAccount, account).one();
    }
}
