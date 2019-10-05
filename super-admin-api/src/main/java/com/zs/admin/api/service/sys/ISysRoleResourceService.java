package com.zs.admin.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.admin.api.entry.SysRoleResource;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2019-08-30
 */
public interface ISysRoleResourceService extends IService<SysRoleResource> {

    List<SysRoleResource> findByRoleIds(List<Long> ids);

    List<SysRoleResource> findByRoleId(Long id);
}
