package io.amoe.cloud.account.feign.impl;

import io.amoe.cloud.account.feign.ISysUserFeign;
import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Amoe
 * @date 2020/4/17 16:58
 */
@Slf4j
@Component
public class SysUserFeignImpl extends AbstractProvider implements ISysUserFeign {
    @Override
    public R<?> getUserById(Long id) {
        return error();
    }
}
