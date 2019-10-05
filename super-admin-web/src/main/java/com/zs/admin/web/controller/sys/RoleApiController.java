package com.zs.admin.web.controller.sys;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.constant.sys.RoleCategoryEnum;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.entry.SysRole;
import com.zs.admin.api.entry.SysRoleResource;
import com.zs.admin.api.service.sys.ISysResourceService;
import com.zs.admin.api.service.sys.ISysRoleResourceService;
import com.zs.admin.api.service.sys.ISysRoleService;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.param.Tree;
import com.zs.admin.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: zs
 * @Date: 2019/10/5 12:42
 * @Description:
 */
@RestController
@RequestMapping("/api/role")
public class RoleApiController extends BaseController{

    @Autowired
    private ISysRoleResourceService roleResourceService;
    @Autowired
    private ISysResourceService resourceService;
    @Autowired
    private ISysRoleService roleService;

    @PostMapping("/sourcesById")
    public Object sourcesById(HttpServletRequest request,Long id){
        List<SysRoleResource> roleResource = roleResourceService.findByRoleId(id);
        if(roleResource != null){
            List<Long> sourceIds = roleResource.stream().map(i -> i.getSourceId()).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(sourceIds)){
                List<SysResource> sysResources = (List<SysResource>)resourceService.listByIds(sourceIds);
                List<Tree> trees = sourceToTree(sysResources, null,true,null);
                return trees;
            }
        }
        return null;
    }

    @PostMapping("/addOrEdit")
    public ResultVo addOrEdit(HttpServletRequest request,Long id){
        Map<String,Object> result = new HashMap<>();
        List<Long> roleSourceIds = null;
        if(id != null){
            //获取角色信息
            result.put("role",roleService.getById(id));
            //获取角色权限
            List<SysRoleResource> roleResource = roleResourceService.findByRoleId(id);
            if(roleResource != null){
                roleSourceIds = roleResource.stream().map(i -> i.getSourceId()).collect(Collectors.toList());
            }
        }
        //获取角色类型
        result.put("roleCategory", RoleCategoryEnum.toJson());
        //获取权限
        List<SysResource> all = resourceService.list();
        List<Tree> trees = sourceToTree(all, null, true,null);
        result.put("trees",trees);
        result.put("roleSourceIds",roleSourceIds);
        return ResultVo.data(result);
    }
}
