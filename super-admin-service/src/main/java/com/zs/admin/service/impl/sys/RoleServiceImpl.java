package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.Role;
import com.zs.admin.api.service.sys.IRoleService;
import com.zs.admin.service.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2019-08-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
