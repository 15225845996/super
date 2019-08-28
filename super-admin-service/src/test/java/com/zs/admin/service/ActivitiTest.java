package com.zs.admin.service;

import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.vo.activiti.HistoricProcessInstanceVo;
import com.zs.admin.api.vo.activiti.ModelVo;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/8/28 10:23
 * @Description:
 */
public class ActivitiTest extends SuperAdminServiceApplicationTests {
    @Autowired
    private IActivitiService activitiService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void start(){
        Map<String,Object> map = new HashMap<>();
        map.put("subUser","1234");
        activitiService.startByKey("registerProcess","奇动人1",map);
    }

    @Test
    public void findModels(){
        List<ModelVo> models = activitiService.findModels();
        models.forEach(System.out::println);
        System.out.println("=-=========================");
    }

    @Test
    public void isDeploymentByKey(){
        boolean deploymentByKey = activitiService.isDeploymentByKey(Constant.REGISTER_PROCESS_KEY);
        System.out.println(deploymentByKey);
    }



    @Test
    public void query(){
        /*7501*/
        List<HistoricProcessInstanceVo> list = activitiService.tasksByAccount("15225845997", null);
        for (HistoricProcessInstanceVo historicProcessInstanceVo : list) {
            System.out.println(historicProcessInstanceVo);
        }
    }

}
