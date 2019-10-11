package com.zs.admin.web.controller.sys;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.entry.SysRole;
import com.zs.admin.api.service.sys.ISysResourceService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.param.Tree;
import com.zs.admin.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Auther: zs
 * @Date: 2019/10/5 12:09
 * @Description:
 */
@Api(value = "权限管理")
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController{
    @Autowired
    private ISysResourceService resourceService;



    @ApiOperation(value = "获取权限列表")
    @GetMapping("/list")
    public Object list(){
        List<SysResource> list = resourceService.list();
        list = sort(list,null);
        return PageVO.page(list, (long) list.size());
    }

    @ApiOperation(value = "保存权限信息")
    @PostMapping("/save")
    public Object save(HttpSession session, SysResource resource){
        if(resource == null){
            return ResultVo.fail("信息不完整！");
        }
        SysResource original = null;
        if(resource.getId() != null){
            original = resourceService.getById(resource.getId());
        }else{//新增， 设置默认信息
            resource.setCreator(getLoginUser(session).getAccount());
            resource.setIsParent(false);
            if(resource.getParentId() == null){
                resource.setParentId(0L);
            }
            if(resource.getOrdinal() == null){
                resource.setOrdinal(0L);
            }
        }
        boolean result = resourceService.saveOrUpdate(resource);
        if(result){
            //跟新当前父节点信息（父节点不等于0，0是最上层）
            if(!resource.getParentId().equals(0L)){
                SysResource currentParent = resourceService.getById(resource.getParentId());
                if(currentParent != null){
                    if(!currentParent.getIsParent()){//如果不是父节点，跟新为父节点
                        currentParent.setIsParent(true);
                        currentParent.setModifyTime(new Date());
                        resourceService.updateById(currentParent);
                    }
                }
            }
            //跟新原父节点信息（同样不为0的）
            if(original != null && !original.getParentId().equals(0)){
                SysResource originalParent = resourceService.getById(original.getParentId());
                if(originalParent != null){
                    if(originalParent.getIsParent()){//是父节点，查看是否患有其他子节点，没有则跟新
                        List<SysResource> childens = resourceService.getByParentId(originalParent.getId());
                        if(!CollUtil.isNotEmpty(childens)){
                            //跟新为子节点
                            originalParent.setIsParent(false);
                            originalParent.setModifyTime(new Date());
                            resourceService.updateById(originalParent);
                        }
                    }
                }
            }
        }
        return ResultVo.success("成功！");
    }


    @ApiOperation(value = "删除权限信息")
    @DeleteMapping("/del/{id}")
    public Object del(HttpSession session,@ApiParam(name = "id", value = "权限ID")@PathVariable(name = "id") Long id){
        if(id == null){
            return ResultVo.fail("信息不完整！");
        }
        List<SysResource> currrentChildens = resourceService.getByParentId(id);
        if(CollUtil.isNotEmpty(currrentChildens)){
            return ResultVo.fail("当前节点包含其他子节点，不能删除！");
        }
        SysResource resource = resourceService.getById(id);
        if(resource != null){
            boolean result = resourceService.removeById(id);
            if(result){//查看原父节点是否缓有其他子节点，没有则跟新原父节点状态
                List<SysResource> childens = resourceService.getByParentId(resource.getParentId());
                if(!CollUtil.isNotEmpty(childens)){
                    SysResource parent = resourceService.getById(resource.getParentId());
                    if(parent != null){
                        parent.setIsParent(false);
                        parent.setModifyTime(new Date());
                        resourceService.updateById(parent);
                    }
                }
            }
        }
        return ResultVo.success("成功！");
    }
}
