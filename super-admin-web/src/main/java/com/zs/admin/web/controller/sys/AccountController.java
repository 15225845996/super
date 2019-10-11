package com.zs.admin.web.controller.sys;

import cn.hutool.core.collection.CollUtil;
import com.zs.admin.annotation.SysLog;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysAccountRole;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.entry.SysRole;
import com.zs.admin.api.service.sys.ISysAccountRoleService;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.api.service.sys.ISysRoleService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.constant.SysLogTypeEnum;
import com.zs.admin.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    private ISysAccountRoleService accountRoleService;
    @Autowired
    private ISysRoleService roleService;

    @GetMapping("/list")
    @SysLog(desc = "账号列表")
    public Object list(HttpServletRequest request,SysAccount account,
                       @RequestParam(name = "page",defaultValue = "1")Integer pageNum,
                       @RequestParam(name = "limit",defaultValue = "10")Integer pageSize){
        if("".equals(account.getAccount())){
            account.setAccount(null);
        }
        PageVO page = accountService.page(account, pageNum, pageSize);
        return page;
    }


    @ApiOperation(value = "保存用戶信息")
    @PostMapping("/save")
    @SysLog(desc = "账号保存",type = SysLogTypeEnum.U)
    public Object save(HttpSession session, SysAccount account,String roleIds){
        SysAccount user = getLoginUser(session);
        if(account == null){
            return ResultVo.fail("信息不完整！");
        }
        if(account.getId() == null){//新增， 设置默认信息
            account.setCreator(getLoginUser(session).getAccount());
        }else{
            //删除原角色信息
            Map<String, Object> removeMap = new HashMap<>();
            removeMap.put("account",account.getAccount());
            accountRoleService.removeByMap(removeMap);
        }
        boolean result = accountService.saveOrUpdate(account);
        if(result && StringUtils.isNotBlank(roleIds)){
            String[] ids = roleIds.split(",");
            List<SysRole> roles = (List<SysRole>)roleService.listByIds(Arrays.asList(ids));
            if(CollUtil.isNotEmpty(roles)){
                List<SysAccountRole> accountRoles = new ArrayList<>();
                roles.forEach(i -> {
                    SysAccountRole accountRole = new SysAccountRole();
                    accountRole.setAccount(account.getAccount());
                    accountRole.setAccountName(account.getName());
                    accountRole.setRoleId(i.getId());
                    accountRole.setRoleName(i.getRoleName());
                    accountRole.setCreator(user.getAccount());
                    accountRoles.add(accountRole);
                });
                accountRoleService.saveBatch(accountRoles);
            }
            //添加新的角色信息
        }
        return ResultVo.success("成功！");
    }


    @ApiOperation(value = "删除用户信息")
    @DeleteMapping("/del/{id}")
    @SysLog(desc = "删除用户信息",type = SysLogTypeEnum.D)
    public Object del(HttpSession session,@ApiParam(name = "id", value = "用户ID")@PathVariable(name = "id") Long id){
        if(id != null){
            SysAccount account = accountService.getById(id);
            if(account != null){
                boolean b = accountService.removeById(id);
                if(b){
                    // 删除角色信息
                    Map<String, Object> removeMap = new HashMap<>();
                    removeMap.put("account",account.getAccount());
                    boolean b1 = accountRoleService.removeByMap(removeMap);
                    return ResultVo.success("成功！");
                }
            }else{
                return ResultVo.fail("ID异常！");
            }
        }
        return ResultVo.fail("ID不能为空！");
    }
}
