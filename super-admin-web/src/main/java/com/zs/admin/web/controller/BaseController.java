package com.zs.admin.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.constant.sys.SourcesCategoryEnum;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysResource;
import com.zs.admin.param.InitMenu;
import com.zs.admin.param.Menu;
import com.zs.admin.param.Tree;
import com.zs.utils.DozerUtils;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zs
 * @Date: 2019/8/17 10:31
 * @Description:
 */
@Controller
public class BaseController {


    protected InitMenu getMenu(HttpSession session){
        Object attribute = session.getAttribute(Constant.USER_SOURCES_MENU_KEY);
        if(attribute != null){
            return (InitMenu)attribute;
        }
        return new InitMenu();
    }


    protected List<Tree> sourceToTree(List<SysResource> resources,Long parentId,boolean isSpread,List<Long> checkIds){
        if(resources != null && resources.size() > 0){
            Long pId = parentId == null?0L:parentId;
            List<SysResource> collect = resources.stream()
                    .filter(i -> pId.equals(i.getParentId()))
                    .collect(Collectors.toList());
            collect.sort((s1,s2) -> s1.getOrdinal().compareTo(s2.getOrdinal()));

            List<Tree> menuCategorys = DozerUtils.dozer(collect, Tree.class);
            if(menuCategorys != null && menuCategorys.size() > 0){
                menuCategorys.stream().forEach(m -> {
                    //默认false，不选中
                    m.setChecked(false);
                    //是否展开
                    m.setSpread(isSpread);
                    //是否禁用
                    m.setDisabled(false);
                    List<Tree> menuInfo = sourceToTree(resources, m.getId(),isSpread,checkIds);
                    if(CollUtil.isNotEmpty(menuInfo)){
                        //list排序
                        menuInfo.sort((s1,s2) -> s1.getOrdinal().compareTo(s2.getOrdinal()));
                    }else{//设置默认选中状态时 父节点不能选中 否则会触发父节点是事件 导致将下面的子节点全部选中或不选 所以只需要设置子节点，再加载后对应的父节点就会对应的选中了
                        //是否选中
                        if(checkIds != null){
                            if(checkIds.contains(m.getId())){
                                m.setChecked(true);
                            }
                        }
                    }
                    m.setChildren(menuInfo);
                });
                return menuCategorys;
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }


    protected List<SysResource> sort(List<SysResource> resources,Long parentId){
        if(resources != null && resources.size() > 0){
            Long pId = parentId == null?0L:parentId;
            List<SysResource> collect = resources.stream()
                    .filter(i -> pId.equals(i.getParentId()))
                    .collect(Collectors.toList());
            collect.sort((s1,s2) -> s1.getOrdinal().compareTo(s2.getOrdinal()));

            List<SysResource> result = new ArrayList<>();
            result.addAll(collect);
            if(collect != null && collect.size() > 0){
                collect.stream().forEach(m -> {
                    List<SysResource> children = sort(resources, m.getId());
                    System.out.println("parnetId:"+parentId+"    result.size()="+result.size());
                    if(CollUtil.isNotEmpty(result)){
                        //list排序
                        children.sort((s1,s2) -> s1.getOrdinal().compareTo(s2.getOrdinal()));
                        result.addAll(children);
                    }
                });
                return result;
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    /**
     * 获取当前登录用户
     *
     * @param session
     * @return
     */
    protected SysAccount getLoginUser(HttpSession session) {
        val userVo = session.getAttribute(Constant.USER_INFO_KEY);
        if (userVo == null) {
            throw new RuntimeException("没有登录信息");
        } else {
            return (SysAccount) userVo;
        }
    }
}
