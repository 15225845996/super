package com.zs.admin.web.controller.activiti;

import com.zs.admin.api.service.IActivitiService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ActivitiProcessController {

    @Autowired
    private IActivitiService activitiService;
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping("/start/{id}")
    public @ResponseBody String startProcess(@PathVariable("id") String id,@RequestParam(name = "startUser",defaultValue = "员工") String startUser){
        try {
            if(StringUtils.isNotBlank(id)){
                ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().deploymentId(id).singleResult();
                if(processDefinition != null){
                    String processKey = processDefinition.getKey();

                    Map<String,Object> map = new HashMap<>();
                    map.put("subUser",startUser);
                    ProcessInstance processInstance = activitiService.startProcess(processKey,map);
                    if(processInstance != null){
                        return "流程启动成功！";
                    }
                }


            }else{
                return "ID不能为空";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "流程启动失败！";
        }
        return "流程启动失败！";
    }


}