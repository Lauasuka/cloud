package io.amoe.cloud.account.feign;

import io.amoe.cloud.account.entity.SysUser;
import io.amoe.cloud.account.feign.impl.SysUserFeignImpl;
import io.amoe.cloud.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Amoe
 * @date 2020/4/16 17:02
 */
@FeignClient(value = "cloud-account", fallback = SysUserFeignImpl.class)
public interface ISysUserFeign {

    @GetMapping("user/{id}")
    R<SysUser> getUserById(@PathVariable Long id);
}
