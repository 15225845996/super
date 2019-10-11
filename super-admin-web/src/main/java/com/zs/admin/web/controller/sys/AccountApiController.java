package com.zs.admin.web.controller.sys;

import com.zs.admin.annotation.SysLog;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.constant.sys.AccountStatusEnum;
import com.zs.admin.api.constant.sys.SourcesCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysRole;
import com.zs.admin.api.service.sys.ISysAccountRoleService;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.api.service.sys.ISysRoleService;
import com.zs.admin.api.vo.ResultVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/10/7 13:02
 * @Description:
 */
@RequestMapping("/api/account")
@RestController
@Api(value = "用户接口")
public class AccountApiController {

    @Autowired
    private ISysAccountService accountService;
    @Autowired
    private ISysAccountRoleService accountRoleService;
    @Autowired
    private ISysRoleService roleService;

    @PostMapping("/info")
    @SysLog(desc = "账号信息")
    public ResultVo addOrEdit(HttpServletRequest request, Long id,@RequestParam(defaultValue = "info") String method){
        Map<String,Object> result = new HashMap<>();
        if(id != null){
            SysAccount account = accountService.getById(id);
            result.put("account",account);
            if(account != null && account.getAccount() != null){
                result.put("accountRoles",accountRoleService.findByAccount(account.getAccount()));
            }
        }
        //获取权限类型
        result.put("category", AccountCategoryEnum.toJson());
        result.put("status", AccountStatusEnum.toJson());
        //获取角色列表
        result.put("roles", roleService.list());
        //方法（详情、编辑、新增）
        result.put("method", method);
        return ResultVo.data(result);
    }
}
