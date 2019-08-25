package com.zs.admin.service.mybatis;

import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.service.SuperAdminServiceApplicationTests;
import com.zs.admin.service.mapper.SysAccountMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/8/24 21:13
 * @Description:
 */
public class MyBatisPlusTest extends SuperAdminServiceApplicationTests {

    @Autowired
    private SysAccountMapper sysAccountMapper;
    @Test
    public void contextLoads() {
        List<SysAccount> sysAccounts = sysAccountMapper.selectList(null);
        sysAccounts.forEach(System.out::println);
    }

    @Test
    public void del(){
        int i = sysAccountMapper.deleteById(5);
        List<SysAccount> sysAccounts = sysAccountMapper.selectList(null);
        sysAccounts.forEach(System.out::println);
    }

    @Test
    public void add(){
        SysAccount account = new SysAccount();
        account.setAccount("123");
        account.setPassword("123");
        account.setCategoryId(1L);
        account.setCategoryName("123");
        /*account.setIsDeleted(false);*/
       /* account.setCreateTime(new Date());*/
        int insert = sysAccountMapper.insert(account);
        System.out.println(insert);
    }
}
