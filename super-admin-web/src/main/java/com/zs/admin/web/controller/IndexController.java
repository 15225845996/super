package com.zs.admin.web.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.sun.xml.internal.ws.api.model.MEP;
import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.constant.sys.AccountCategoryEnum;
import com.zs.admin.api.constant.sys.SourcesCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysAccountRole;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.entry.SysRoleResource;
import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.service.sys.*;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.param.HomeInfo;
import com.zs.admin.param.InitMenu;
import com.zs.admin.param.Menu;
import com.zs.utils.DozerUtils;
import com.zs.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: zs
 * @Date: 2019/8/25 16:14
 * @Description:
 */
@Api("系统登录注册页面")
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

    @ApiOperation("登录注册页面")
    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request, Model model){
        return "index";
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(HttpServletRequest request,Model model, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            String password = MD5Utils.getPassWord(account.getPassword());
            account = accountService.findByAccountAndPassword(account.getAccount(), password);
            return loginMethod(request, account);
        }
        return ResultVo.fail("信息异常！");
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    @ResponseBody
    public ResultVo register(HttpServletRequest request,Model model, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            boolean existAdmin = accountService.isExistAdmin();
            if(existAdmin){//存在管理员用户
                account.setCategoryId(AccountCategoryEnum.USER.getCategoryId());
                account.setCategoryName(AccountCategoryEnum.USER.getCategoryName());
            }else{
                account.setCategoryId(AccountCategoryEnum.ADMIN.getCategoryId());
                account.setCategoryName(AccountCategoryEnum.ADMIN.getCategoryName());
            }
            return registerMethod(request, account);
        }
        return ResultVo.fail("注册失败！");
    }


    @GetMapping("/accountIsExist/{account}")
    @ResponseBody
    public ResultVo accountIsExist(HttpServletRequest request, @PathVariable("account") String account){
        boolean isExist = accountService.isExistByAccount(account);
        return ResultVo.data(isExist);
    }

    private ResultVo loginMethod(HttpServletRequest request, SysAccount account){
        if(account != null && StringUtils.isNotBlank(account.getAccount())){
            //获取用户角色id
            List<SysAccountRole> roles = accountRoleService.findByAccount(account.getAccount());
            List<Long> roleIds = roles.stream().map(r -> r.getId()).distinct().collect(Collectors.toList());
            List<SysResource> resources = null;
            List<SysRoleResource> roleResources = null;
            //封装菜单信息
            InitMenu initMenu = new InitMenu();
            if(roleIds != null && roleIds.size() > 0){
                //获取角色信息
                roles = (List)roleService.listByIds(roleIds);
                roleResources = roleResourceService.findByRoleIds(roleIds);
                List<Long> sourceIds = roleResources.stream().map(r -> r.getSourceId()).distinct().collect(Collectors.toList());
                if(sourceIds != null && sourceIds.size() > 0){
                    //获取资源信息
                    resources = (List)resourceService.listByIds(sourceIds);
                }
                //获取home页面
                SysResource home = resourceService.getById(Constant.BACKSTAGE_HOME_ID);
                if(home != null){
                    initMenu.setHomeInfo(DozerUtils.dozer(home,HomeInfo.class));
                }
                //菜单信息
                if(resources != null){
                    List<Menu> menuInfo = getMenuInfo(resources, null);
                    if(menuInfo != null){
                        //map排序
                        Map<String,Menu> linked = new LinkedHashMap<>();
                        menuInfo.stream().collect(Collectors.toMap(i -> i.getTitle(), i -> i)).entrySet().stream().sorted(Comparator.comparing(i -> i.getValue().getOrdinal()))
                                .forEachOrdered(e -> linked.put(e.getKey(), e.getValue()));
                        initMenu.setMenuInfo(linked);
                    }
                }
            }
            HttpSession session = request.getSession();
            session.setAttribute(Constant.USER_INFO_KEY,account);
            session.setAttribute(Constant.USER_ROLES_KEY,roles);
            session.setAttribute(Constant.USER_SOURCES_KEY,resources);
            session.setAttribute(Constant.USER_SOURCES_MENU_KEY,initMenu);
            return ResultVo.data(Constant.BACKSTAGE_INDEX_PAGE);
        }
        return ResultVo.fail("登录失败！");
    }

    private ResultVo registerMethod(HttpServletRequest request, SysAccount account){
        if(StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getPassword())){
            account.setPassword(MD5Utils.getPassWord(account.getPassword()));
            boolean save = accountService.save(account);
            if(save){
                return ResultVo.success("注册成功！");
            }
        }
        return ResultVo.fail("注册失败！");
    }

    protected List<Menu> getMenuInfo(List<SysResource> resources,Long parentId){
        Long pId = parentId == null?0L:parentId;
        List<SysResource> collect = resources.stream()
                .filter(i -> SourcesCategoryEnum.NAV.getCategoryId().equals(i.getCategoryId()))
                .filter(i -> pId.equals(i.getParentId()))
                .collect(Collectors.toList());

        List<Menu> menuCategorys = DozerUtils.dozer(collect, Menu.class);
        if(menuCategorys != null && menuCategorys.size() > 0){
            menuCategorys.stream().forEach(m -> {
                List<Menu> menuInfo = getMenuInfo(resources, m.getId());
                if(menuInfo != null){
                    //list排序
                    menuInfo.sort((s1,s2) -> s1.getOrdinal().compareTo(s2.getOrdinal()));
                }
                m.setChild(menuInfo);
            });
            return menuCategorys;
        }
        return new ArrayList<>();
    }
}
