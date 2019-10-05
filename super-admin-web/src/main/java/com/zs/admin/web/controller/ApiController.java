package com.zs.admin.web.controller;

import com.zs.admin.param.InitMenu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Auther: zs
 * @Date: 2019/10/5 10:10
 * @Description:
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController{

    @GetMapping("/menu")
    public InitMenu menu(HttpSession session){
        return getMenu(session);
    }

}
