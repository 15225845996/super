package com.zs.admin.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.vo.PageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2019-08-30
 */
public interface ISysAccountService extends IService<SysAccount> {

    SysAccount findByAccountAndPassword(String account,String password);

    /**
     * 是否存在管理员
     * @return
     */
    boolean isExistAdmin();


    /**
     * 账后是否已存在
     */
    boolean isExistByAccount(String account);

    List<SysAccount> query(SysAccount account);

    PageVO page(SysAccount account,Integer pageNum,Integer pageSize);

    SysAccount saveOrUpdate2(SysAccount account);
}
