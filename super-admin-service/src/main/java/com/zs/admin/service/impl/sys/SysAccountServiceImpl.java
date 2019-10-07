package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.service.mapper.SysAccountMapper;
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

    @Override
    public List<SysAccount> query(SysAccount account) {
        return super.list(getQuery(account));
    }

    @Override
    public PageVO page(SysAccount account, Integer pageNum, Integer pageSize) {
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        IPage<SysAccount> page = super.page(new Page<>(pageNum, pageSize), getQuery(account));
        return PageVO.page(page.getRecords(), page.getTotal());
    }

    protected QueryWrapper getQuery(SysAccount account){
        QueryWrapper<SysAccount> quer = new QueryWrapper<>();
        if(account != null){
            LambdaQueryWrapper<SysAccount> lambda = quer.lambda();
            if(account.getAccount() != null){
                lambda.eq(SysAccount::getAccount,account.getAccount());
            }
            if(StringUtils.isNotBlank(account.getName())){
                lambda.like(SysAccount::getName,"%"+account.getName()+"%");
            }
            if(account.getCategoryId() != null){
                lambda.eq(SysAccount::getCategoryId,account.getCategoryId());
            }
        }
        return quer;
    }

}
