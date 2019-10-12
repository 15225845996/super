package com.zs.admin.web.controller.sys;

import com.zs.admin.annotation.SysLog;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysLogInfo;
import com.zs.admin.api.service.sys.ISysLogInfoService;
import com.zs.admin.api.service.sys.ISysLogService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.api.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/10/11 17:14
 * @Description:
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ISysLogService logService;
    @Autowired
    private ISysLogInfoService logInfoService;

    @GetMapping("/list")
    public Object list(HttpServletRequest request, com.zs.admin.api.entry.SysLog log,
                       @RequestParam(name = "page",defaultValue = "1")Integer pageNum,
                       @RequestParam(name = "limit",defaultValue = "10")Integer pageSize){
        PageVO page = logService.page(log, pageNum, pageSize);
        return page;
    }


    @PostMapping("/view")
    public Object view(HttpServletRequest request, Long id){
        if(id != null){
            com.zs.admin.api.entry.SysLog log = logService.getDetail(id);
            return ResultVo.data(log);
        }
        return ResultVo.fail("ID不能为空！");
    }
}
