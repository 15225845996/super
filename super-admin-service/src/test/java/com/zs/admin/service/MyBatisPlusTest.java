package com.zs.admin.service;

import com.zs.admin.api.entry.Account;
import com.zs.admin.api.service.sys.IAccountService;
import com.zs.admin.service.mapper.AccountMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/8/24 21:13
 * @Description:
 */
public class MyBatisPlusTest extends SuperAdminServiceApplicationTests {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IAccountService accountService;
    @Test
    public void contextLoads() {
        List<Account> sysAccounts = accountMapper.selectList(null);
        sysAccounts.forEach(System.out::println);
    }

    @Test
    public void del(){
        int i = accountMapper.deleteById(5);
        List<Account> sysAccounts = accountMapper.selectList(null);
        sysAccounts.forEach(System.out::println);
    }

    @Test
    public void add(){
        Account account = new Account();
        account.setAccount("123");
        account.setPassword("123");
        account.setCategoryId(1L);
        account.setCategoryName("123");
        /*account.setIsDeleted(false);*/
       /* account.setCreateTime(new Date());*/
        int insert = accountMapper.insert(account);
        System.out.println(insert);
    }

    @Test
    public void service(){
        int count = accountService.count();
        System.out.println(count);
    }

    @Test
    public void query(){

    }
}
