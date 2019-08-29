package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.entry.Account;
import com.zs.admin.api.service.sys.IAccountService;
import com.zs.admin.service.mapper.AccountMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findByAccountAndPassword(String account, String password) {
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)){
            QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Account::getAccount,account).eq(Account::getPassword,password);
            return super.getOne(queryWrapper);
        }
        return null;
    }

    @Override
    public boolean isExistAdmin() {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Account::getCategoryId, AccountCategoryEnum.ADMIN.getCategoryId());
        int count = super.count(queryWrapper);
        return count > 0;
    }

    @Override
    public boolean isExistByAccount(String account) {
        if(StringUtils.isNotBlank(account)){
            QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Account::getAccount,account);
            int count = super.count(queryWrapper);
            return count > 0;
        }
        return false;
    }
}
