package com.zs.adminservice.controller.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zs.adminservice.service.IActivitiService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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