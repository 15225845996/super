package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.service.sys.ISysResourceService;
import com.zs.admin.service.mapper.SysResourceMapper;
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
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Override
    public List<SysResource> getByParentId(Long parentId) {
        if(parentId != null){
            QueryWrapper<SysResource> query = new QueryWrapper<>();
            query.lambda().eq(SysResource::getParentId,parentId);
            return super.list(query);
        }
        return null;
    }
}
