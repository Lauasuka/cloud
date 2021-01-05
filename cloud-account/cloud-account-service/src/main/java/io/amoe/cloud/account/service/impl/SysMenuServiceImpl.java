package io.amoe.cloud.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysMenu;
import io.amoe.cloud.account.mapper.SysMenuMapper;
import io.amoe.cloud.account.service.ISysMenuService;
import org.springframework.stereotype.Service;

/**
 * @author Amoe
 * @date 2020/4/9 16:50
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
}
