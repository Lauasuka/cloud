package io.amoe.cloud.account.provider;

import io.amoe.cloud.account.dto.PostUserDTO;
import io.amoe.cloud.account.entity.SysUser;
import io.amoe.cloud.account.service.SysUserService;
import io.amoe.cloud.annotation.WebLog;
import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.R;
import io.amoe.cloud.enums.BizResponseStatus;
import io.amoe.cloud.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Amoe
 * @date 2020/4/10 10:40
 */
@Slf4j
@WebLog("账户模块")
@RestController
public class SysUserProvider extends AbstractProvider {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("user/{id}")
    public R<SysUser> getUserById(@PathVariable  Long id) {
        SysUser user = sysUserService.getById(id);
        Optional.ofNullable(user).orElseThrow(() -> new BizException(BizResponseStatus.NOT_FOUND));
        return success(user);
    }

    @PostMapping("user")
    public R<?> postUser(@RequestBody @Validated PostUserDTO dto) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        sysUserService.addUser(user);
        return success();
    }
}
