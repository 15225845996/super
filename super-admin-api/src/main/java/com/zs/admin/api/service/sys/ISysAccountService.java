package com.zs.admin.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.admin.api.entry.SysAccount;

/**
 * @Auther: zs
 * @Date: 2019/8/25 15:47
 * @Description:
 */
public interface ISysAccountService extends IService<SysAccount> {

    SysAccount findByAccountAndPassword(String account,String password);

    /**
     * 是否存在管理员
     * @return
     */
    boolean isExistAdmin();
}
