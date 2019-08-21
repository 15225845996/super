package com.zs.admin.api.adminweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zs
 * @Date: 2019/8/17 10:31
 * @Description:
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping(value = {"/","/index"})
    public String index(HttpServletRequest request, Model model){
        return "index";
    }
}
