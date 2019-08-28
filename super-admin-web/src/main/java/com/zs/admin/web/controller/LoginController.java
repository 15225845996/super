package com.zs.admin.web.controller;

import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.service.sys.ISysAccountService;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.api.vo.sys.SysAccountVo;
import com.zs.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/8/25 16:14
 * @Description:
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    private IActivitiService activitiService;

    @RequestMapping("/login")
    public ResultVo login(HttpServletRequest request, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            String password = MD5Utils.getPassWord(account.getPassword());
            account = sysAccountService.findByAccountAndPassword(account.getAccount(), password);
            if(account != null){
                return ResultVo.success();
            }
        }
        return ResultVo.fail("信息异常！");
    }

    @RequestMapping("/region")
    public ResultVo region(HttpServletRequest request, SysAccountVo account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            boolean existAdmin = sysAccountService.isExistAdmin();
            if(existAdmin){//存在管理员用户
                boolean isDeployment = activitiService.isDeploymentByKey(Constant.REGISTER_PROCESS_KEY);
                if(isDeployment){
                    //启动流程
                    Map<String,Object> map = new HashMap<>();
                    map.put(Constant.START_PROCESS_KEY,account.getAccount());
                    map.put("account",account);
                    String processId = activitiService.startByKey(Constant.REGISTER_PROCESS_KEY, map);
                    if(processId != null){
                        return ResultVo.success("已注册成功，正在等待管理员审批！");
                    }
                }else{//未部署则直接注册
                    return regionMethod(request,account);
                }
            }else{//不存在管理员用户则直接注册成功
                return regionMethod(request,account);
            }
        }
        return ResultVo.fail("注册失败！");
    }


    @RequestMapping("/checkAccount")
    public Map<String,Boolean> isExistByAccount(HttpServletRequest request, SysAccountVo account,@RequestParam(name = "checkType" , defaultValue = "login") String checkType){
        Map<String,Boolean> result = new HashMap<>();
        result.put("valid",false);
        if(StringUtils.isNotBlank(account.getAccount())){
            if(StringUtils.isNotBlank(account.getAccount())){
                boolean isExist = sysAccountService.isExistByAccount(account.getAccount());
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


    private ResultVo regionMethod(HttpServletRequest request, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            account.setPassword(MD5Utils.getPassWord(account.getPassword()));
            account.setCategoryId(AccountCategoryEnum.ADMIN.getCategoryId());
            account.setCategoryName(AccountCategoryEnum.ADMIN.getCategoryName());
            if("".equals(account.getSex())){
                account.setSex(null);
            }
            boolean save = sysAccountService.save(account);
            if(save){
                return ResultVo.success("注册成功！");
            }
        }
        return ResultVo.fail("注册失败！");
    }
}
