package com.zs.admin.web.controller.back;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: zs
 * @Date: 2019/8/30 09:46
 * @Description:
 */
@Api("登录后首页分发接口")
@Controller
@RequestMapping("/studio")
public class StudioController {

    @ApiOperation(value = "登录后首页",notes = "登录后页面的最外层框架")
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){

        return "studio/index";
    }
}
