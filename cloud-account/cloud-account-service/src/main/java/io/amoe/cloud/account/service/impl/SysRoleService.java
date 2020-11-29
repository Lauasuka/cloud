package io.amoe.cloud.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysRole;
import io.amoe.cloud.account.mapper.SysRoleMapper;
import io.amoe.cloud.account.service.ISysRoleService;
import org.springframework.stereotype.Service;

/**
 * @author Amoe
 * @date 2020/4/9 16:50
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
}
