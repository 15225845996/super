package com.zs.admin.web.controller.sys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zs
 * @Date: 2019/10/5 12:09
 * @Description:
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/list")
    public Object list(){

        return null;
    }
}
