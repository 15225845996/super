package com.zs.admin.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.admin.api.entry.SysAccountRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2019-08-30
 */
public interface ISysAccountRoleService extends IService<SysAccountRole> {

    List<SysAccountRole> findByAccount(String account);

}
