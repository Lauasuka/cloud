package io.amoe.cloud.account.provider;

import io.amoe.cloud.account.dto.PostMenuDTO;
import io.amoe.cloud.account.service.SysMenuService;
import io.amoe.cloud.base.AbstractProvider;
import io.amoe.cloud.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Amoe
 * @date 2020/4/10 13:34
 */
@Slf4j
@RestController
public class SysMenuProvider extends AbstractProvider {

    @Resource
    private SysMenuService sysMenuService;

    @PostMapping("menu")
    public R<?> postSysMenu(@RequestBody PostMenuDTO dto) {
        // TODO should complete
        return null;
    }

}
