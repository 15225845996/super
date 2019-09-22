package com.zs.admin.web.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: zs
 * @Date: 2019/8/30 09:46
 * @Description:
 */
@Controller
@RequestMapping("/studio")
public class StudioController {

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){

        return "studio/index";
    }
}
