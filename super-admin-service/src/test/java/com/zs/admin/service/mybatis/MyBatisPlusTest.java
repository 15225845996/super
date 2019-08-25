package com.zs.admin.service.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.service.SuperAdminServiceApplicationTests;
import com.zs.admin.service.mapper.SysAccountMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/8/24 21:13
 * @Description:
 */
public class MyBatisPlusTest extends SuperAdminServiceApplicationTests {

    @Autowired
    private SysAccountMapper sysAccountMapper;
    @Autowired
    private ISysAccountService sysAccountService;
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

    @Test
    public void service(){
        int count = sysAccountService.count();
        System.out.println(count);
    }

    @Test
    public void query(){
        QueryWrapper<SysAccount> query = new QueryWrapper<>();
        /*query.lambda().eq(SysAccount::getName,"用户2").eq(SysAccount::getPassword,"123");*/
        query.lambda().eq(SysAccount::getPassword,"123");
        Map<String, Object> map = sysAccountService.getMap(query);
        List<SysAccount> list = sysAccountService.list(query);
        SysAccount one = sysAccountService.getOne(query);
        System.out.println(map);
    }
}
