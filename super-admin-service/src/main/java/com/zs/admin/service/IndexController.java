package com.zs.admin.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: zs
 * @Date: 2019/8/16 10:50
 * @Description:
 */
@Controller
public class IndexController {


    @RequestMapping(path={"/","/index"})
    public String index(){
        return "hello";
    }
}
