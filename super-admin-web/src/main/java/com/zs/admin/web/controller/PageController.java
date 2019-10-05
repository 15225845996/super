package com.zs.admin.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: zs
 * @Date: 2019/10/4 20:33
 * @Description:
 */
@Api(value = "页面跳转")
@Controller
@RequestMapping("/page")
public class PageController extends BaseController{

    @ApiOperation("一级页面跳转")
    @GetMapping("/{page1}")
    public String page(@PathVariable("page1")String page1){

        return "/page/"+page1;
    }

    @ApiOperation("二级页面跳转")
    @GetMapping("/{page1}/{page2}")
    public String page(@PathVariable("page1")String page1,@PathVariable("page2")String page2){

        return "/page/"+page1+"/"+page2;
    }
}
