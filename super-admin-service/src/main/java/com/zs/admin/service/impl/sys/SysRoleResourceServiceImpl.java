package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.SysRoleResource;
import com.zs.admin.api.service.sys.ISysRoleResourceService;
import com.zs.admin.service.mapper.SysRoleResourceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2019-08-30
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements ISysRoleResourceService {

    @Override
    public List<SysRoleResource> findByRoleIds(List<Long> ids) {
        if(ids != null && ids.size() > 0){
            QueryWrapper<SysRoleResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(SysRoleResource::getRoleId,ids);
            return super.list(queryWrapper);
        }
        return null;
    }
}
