package com.zs.admin.service;

import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.api.service.sys.ISysResourceService;
import com.zs.admin.service.mapper.SysAccountMapper;
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
    private SysAccountMapper accountMapper;
    @Autowired
    private ISysAccountService accountService;
    @Autowired
    private ISysResourceService resourceService;

    @Test
    public void query1(){
        List<SysResource> byParentId = resourceService.getByParentId(13L);
        byParentId.forEach(System.out::println);
    }

    @Test
    public void contextLoads() {
        List<SysAccount> sysAccounts = accountMapper.selectList(null);
        sysAccounts.forEach(System.out::println);
    }

    @Test
    public void del(){
        int i = accountMapper.deleteById(5);
        List<SysAccount> sysAccounts = accountMapper.selectList(null);
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
