package com.zs.admin.web.controller.entry;

import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysAccountRole;
import com.zs.admin.api.entry.SysRole;
import com.zs.admin.api.entry.SysRoleResource;
import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.service.sys.*;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.api.vo.activiti.HistoricProcessInstanceVo;
import com.zs.admin.web.controller.BaseController;
import com.zs.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: zs
 * @Date: 2019/8/25 16:14
 * @Description:
 */
@Api("系统首页功能接口")
@Controller
public class IndexController extends BaseController {

    @Autowired
    private ISysAccountService accountService;
    @Autowired
    private IActivitiService activitiService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysAccountRoleService accountRoleService;
    @Autowired
    private ISysResourceService resourceService;
    @Autowired
    private ISysRoleResourceService roleResourceService;

    @ApiOperation("系统首页")
    @RequestMapping(value = {"/","/index"})
    public String index(HttpServletRequest request, Model model){
        return "index";
    }

    @ApiOperation("系统登录")
    @ApiParam(name = "参数",value = "这是描述参数")
    @ApiImplicitParam(name = "telephone", value = "电话号码", paramType = "query", required = true, dataType = "Integer")
    @RequestMapping("/login")
    @ResponseBody
    public ResultVo login(HttpServletRequest request,Model model, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            String password = MD5Utils.getPassWord(account.getPassword());
            account = accountService.findByAccountAndPassword(account.getAccount(), password);
            return loginMethod(request, account);
        }
        return ResultVo.fail("信息异常！");
    }

    @RequestMapping("/region")
    @ResponseBody
    public ResultVo region(HttpServletRequest request,Model model, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            boolean existAdmin = accountService.isExistAdmin();
            if(existAdmin){//存在管理员用户
                account.setCategoryId(AccountCategoryEnum.USER.getCategoryId());
                account.setCategoryName(AccountCategoryEnum.USER.getCategoryName());
            }else{
                account.setCategoryId(AccountCategoryEnum.ADMIN.getCategoryId());
                account.setCategoryName(AccountCategoryEnum.ADMIN.getCategoryName());
            }
            return regionMethod(request,account);
        }
        return ResultVo.fail("注册失败！");
    }


    @RequestMapping("/checkAccount")
    @ResponseBody
    public Map<String,Boolean> isExistByAccount(HttpServletRequest request, SysAccount account, @RequestParam(name = "checkType" , defaultValue = "login") String checkType){
        Map<String,Boolean> result = new HashMap<>();
        result.put("valid",false);
        if(StringUtils.isNotBlank(account.getAccount())){
            if(StringUtils.isNotBlank(account.getAccount())){
                boolean isExist = accountService.isExistByAccount(account.getAccount());
                switch (checkType){
                    case "login"://登录：查到为true
                        result.put("valid",isExist);
                        break;
                    case "region"://注册：查不到为true
                        result.put("valid",!isExist);
                        break;
                }
            }
        }
        return result;
    }

    private ResultVo loginMethod(HttpServletRequest request, SysAccount account){
        if(account != null && StringUtils.isNotBlank(account.getAccount())){
            //获取用户角色id
            List<SysAccountRole> roles = accountRoleService.findByAccount(account.getAccount());
            List<Long> roleIds = roles.stream().map(r -> r.getId()).distinct().collect(Collectors.toList());
            List<SysRoleResource> resources = null;
            if(roleIds != null && roleIds.size() > 0){
                //获取角色信息
                roles = (List)roleService.listByIds(roleIds);
                resources = roleResourceService.findByRoleIds(roleIds);
                List<Long> sourceIds = resources.stream().map(r -> r.getSourceId()).distinct().collect(Collectors.toList());
                if(sourceIds != null && sourceIds.size() > 0){
                    //获取资源信息
                    resources = (List)resourceService.listByIds(sourceIds);
                }

            }
            HttpSession session = request.getSession();
            session.setAttribute(Constant.CURRENT_USER_INFO_KEY,account);
            session.setAttribute(Constant.CURRENT_USER_ROLES_KEY,roles);
            session.setAttribute(Constant.CURRENT_USER_SOURCES_KEY,resources);
            return ResultVo.success();
        }
        return ResultVo.fail("登录失败！");
    }

    private ResultVo regionMethod(HttpServletRequest request, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            account.setPassword(MD5Utils.getPassWord(account.getPassword()));
            boolean save = accountService.save(account);
            if(save){
                return ResultVo.success("注册成功！");
            }
        }
        return ResultVo.fail("注册失败！");
    }
}
