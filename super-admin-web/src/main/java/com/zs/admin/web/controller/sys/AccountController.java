package com.zs.admin.web.controller.sys;

import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.web.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zs
 * @Date: 2019/10/7 13:01
 * @Description:
 */
@RequestMapping("/account")
@RestController
@Api(value = "用户管理")
public class AccountController extends BaseController {

    @Autowired
    private ISysAccountService accountService;

    @GetMapping("/list")
    public Object list(HttpServletRequest request,SysAccount account,
                       @RequestParam(name = "page",defaultValue = "1")Integer pageNum,
                       @RequestParam(name = "limit",defaultValue = "10")Integer pageSize){
        return accountService.page(account, pageNum, pageSize);
    }
}
