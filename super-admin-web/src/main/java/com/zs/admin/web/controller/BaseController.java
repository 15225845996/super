package com.zs.admin.web.controller;

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

            List<Tree> menuCategorys = DozerUtils.dozer(collect, Tree.class);
            if(menuCategorys != null && menuCategorys.size() > 0){
                menuCategorys.stream().forEach(m -> {
                    //默认false，不选中
                    m.setChecked(false);
                    //是否展开
                    m.setSpread(isSpread);
                    //是否禁用
                    m.setDisabled(false);
                    //是否选中
                    if(checkIds != null){
                        if(checkIds.contains(m.getId())){
                            m.setChecked(true);
                        }
                    }
                    List<Tree> menuInfo = sourceToTree(resources, m.getId(),isSpread,checkIds);
                    if(menuInfo != null){
                        //list排序
                        menuInfo.sort((s1,s2) -> s1.getOrdinal().compareTo(s2.getOrdinal()));
                    }
                    m.setChildren(menuInfo);
                });
                return menuCategorys;
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
