package com.zs.admin.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @Auther: zs
 * @Date: 2019/8/24 19:49
 * @Description:
 */
@Service
public class ActivitiTaskService implements JavaDelegate{
    Logger logger = LoggerFactory.getLogger(ActivitiTaskService.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String processId = delegateExecution.getProcessInstanceId();
        String processKey = delegateExecution.getProcessInstanceBusinessKey();
        logger.info("工作流任务节点，流程实例ID：{}；流程定义KEY：{}",processId,processKey);
    }
}
