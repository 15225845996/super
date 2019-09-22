package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.SysAccountRole;
import com.zs.admin.api.service.sys.ISysAccountRoleService;
import com.zs.admin.service.mapper.SysAccountRoleMapper;
import org.apache.commons.lang3.StringUtils;
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
public class SysAccountRoleServiceImpl extends ServiceImpl<SysAccountRoleMapper, SysAccountRole> implements ISysAccountRoleService {

    @Override
    public List<SysAccountRole> findByAccount(String account) {
        if(StringUtils.isNotBlank(account)){
            QueryWrapper<SysAccountRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysAccountRole::getAccount,account);
            List<SysAccountRole> list = super.list(queryWrapper);
            return list;
        }
        return null;
    }
}
