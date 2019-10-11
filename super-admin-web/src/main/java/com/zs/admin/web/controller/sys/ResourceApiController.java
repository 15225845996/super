package com.zs.admin.web.controller.sys;

import com.zs.admin.annotation.SysLog;
import com.zs.admin.api.constant.sys.RoleCategoryEnum;
import com.zs.admin.api.constant.sys.SourcesCategoryEnum;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.api.entry.SysRoleResource;
import com.zs.admin.api.service.sys.ISysResourceService;
import com.zs.admin.api.vo.ResultVo;
import com.zs.admin.constant.SysLogTypeEnum;
import com.zs.admin.param.Tree;
import com.zs.admin.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: zs
 * @Date: 2019/10/5 12:10
 * @Description:
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceApiController extends BaseController{

    @Autowired
    private ISysResourceService resourceService;


    @ApiOperation(value = "获取权限树")
    @PostMapping("/tree")
    @SysLog(desc = "获取权限树")
    public Object tree(@RequestParam(defaultValue = "0") Long parentId,
                       @RequestParam(defaultValue = "true") Boolean isSpread,
                       @RequestParam(required = false) List<Long> checkIds){
        List<Tree> trees = sourceToTree(resourceService.list(), parentId, isSpread, checkIds);
        return trees;
    }

    @PostMapping("/addOrEdit")
    @SysLog(desc = "添加或获取权限信息")
    public ResultVo addOrEdit(HttpServletRequest request, Long id){
        Map<String,Object> result = new HashMap<>();
        if(id != null){
            //获取权限信息
            result.put("resource",resourceService.getById(id));
        }
        //获取权限类型
        result.put("category", SourcesCategoryEnum.toJson());
        return ResultVo.data(result);
    }


}
