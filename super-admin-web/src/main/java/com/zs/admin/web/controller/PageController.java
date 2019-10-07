package com.zs.admin.web.controller;

import com.zs.admin.api.constant.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

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
    public String page(HttpServletRequest request,Model model,@PathVariable("page1")String page1){
        syncParam(model,request);
        return "/page/"+page1;
    }

    @ApiOperation("二级页面跳转")
    @GetMapping("/{page1}/{page2}")
    public String page(HttpServletRequest request,Model model,@PathVariable("page1")String page1, @PathVariable("page2")String page2){
        syncParam(model,request);
        return "/page/"+page1+"/"+page2;
    }

    protected void syncParam(Model model,HttpServletRequest request){
        Map<String, String[]> map = request.getParameterMap();
        if(map != null){
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                if(entry.getValue().length == 1){
                    model.addAttribute(entry.getKey(),entry.getValue()[0]);
                }else{
                    model.addAttribute(entry.getKey(),Arrays.toString(entry.getValue()));
                }
            }
        }
        model.addAttribute(Constant.USER_INFO_KEY,getLoginUser(request.getSession()));
    }
}
