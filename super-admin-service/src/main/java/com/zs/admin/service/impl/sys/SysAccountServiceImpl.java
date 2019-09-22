package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.service.mapper.SysAccountMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2019-08-30
 */
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {


    @Override
    public SysAccount findByAccountAndPassword(String account, String password) {
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)){
            QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysAccount::getAccount,account).eq(SysAccount::getPassword,password);
            return super.getOne(queryWrapper);
        }
        return null;
    }

    @Override
    public boolean isExistAdmin() {
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysAccount::getCategoryId, AccountCategoryEnum.ADMIN.getCategoryId());
        int count = super.count(queryWrapper);
        return count > 0;
    }

    @Override
    public boolean isExistByAccount(String account) {
        if(StringUtils.isNotBlank(account)){
            QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysAccount::getAccount,account);
            int count = super.count(queryWrapper);
            return count > 0;
        }
        return false;
    }
}
