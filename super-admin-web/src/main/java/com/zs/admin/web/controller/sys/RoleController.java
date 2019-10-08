package com.zs.admin.web.controller.sys;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.zs.admin.api.entry.SysRole;
import com.zs.admin.api.entry.SysRoleResource;
import com.zs.admin.api.service.sys.ISysRoleResourceService;
import com.zs.admin.api.service.sys.ISysRoleService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.param.Tree;
import com.zs.admin.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.font.TrueTypeFont;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/10/5 12:19
 * @Description:
 */
@Api(value = "角色权限")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysRoleResourceService roleResourceService;

    @ApiOperation(value = "获取角色列表数据")
    @GetMapping("/list")
    public Object list(){
        List<SysRole> list = roleService.list();
        return PageVO.page(list, (long) list.size());
    }


    @ApiOperation(value = "编辑角色信息")
    @PostMapping("/save")
    public Object save(HttpSession session, SysRole sysRole,
                       @ApiParam(name = "sourcesInfo", value = "角色权限") String sourcesInfo){
        if(sysRole == null){
            return ResultVo.fail("信息不完整！");
        }
        if(sysRole.getId() != null){
            // 删除原角色信息
            Map<String, Object> removeMap = new HashMap<>();
            removeMap.put("role_id",sysRole.getId());
            roleResourceService.removeByMap(removeMap);
        }
        // 更改角色信息
        sysRole = roleService.saveOrUpdate2(sysRole);
        // 跟新角色权限
        JSONArray array = JSONUtil.parseArray(sourcesInfo);
        List<Tree> roleResource = new ArrayList<>();
        if(array != null){
            array.forEach(i -> {
                roleResource.add(JSONUtil.toBean(i.toString(), Tree.class));
            });
            addRoleResource(sysRole,getLoginUser(session).getAccount(),roleResource,null);
        }
        return ResultVo.success("成功！");
    }


    @ApiOperation(value = "编辑角色信息")
    @DeleteMapping("/delete/{id}")
    public Object delete(HttpSession session, @PathVariable("id") Long id){
        if(id != null){
            boolean b = roleService.removeById(id);
            if(b){
                // 删除角色信息
                Map<String, Object> removeMap = new HashMap<>();
                removeMap.put("role_id",id);
                boolean b1 = roleResourceService.removeByMap(removeMap);
                return ResultVo.success("成功！");
            }
        }
        return ResultVo.fail("ID不能为空！");
    }


    /**
     * 新增角色权限
     *
     * @param role                    角色
     * @param creator                 创建人
     * @param trees                   前台传过来的角色集合
     * @param r                       转换过后批量插入
     */
    private void addRoleResource(SysRole role, String creator, List<Tree> trees,List<SysRoleResource> r) {
        if(CollUtil.isNotEmpty(trees)){
            List<SysRoleResource> resources = r == null?new ArrayList<>():r;
            trees.forEach(i -> {
                SysRoleResource roleResource = new SysRoleResource();
                roleResource.setRoleId(role.getId())
                        .setRoleName(role.getRoleName())
                        .setSourceId(i.getId())
                        .setSourceName(i.getTitle())
                        .setCreator(creator);
                resources.add(roleResource);
                if(CollUtil.isNotEmpty(i.getChildren())){
                    addRoleResource(role,creator,i.getChildren(),resources);
                }else{
                    //一次插入一条分支
                    roleResourceService.saveBatch(resources);
                    resources.clear();
                }
            });
        }
    }
}
