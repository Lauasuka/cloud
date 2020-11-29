package io.amoe.cloud.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.amoe.cloud.account.entity.SysRelation;
import io.amoe.cloud.account.mapper.SysRelationMapper;
import io.amoe.cloud.account.service.ISysRelationService;
import org.springframework.stereotype.Service;

/**
 * @author Amoe
 * @date 2020/4/9 16:50
 */
@Service
public class SysRelationService extends ServiceImpl<SysRelationMapper, SysRelation> implements ISysRelationService {
}
