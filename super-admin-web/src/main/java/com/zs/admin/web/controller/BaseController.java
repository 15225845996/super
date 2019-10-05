package com.zs.admin.web.controller;

import com.zs.admin.api.constant.Constant;
import com.zs.admin.param.InitMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

}
