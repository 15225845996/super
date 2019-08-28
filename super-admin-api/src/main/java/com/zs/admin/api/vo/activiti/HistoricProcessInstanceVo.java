package com.zs.admin.api.vo.activiti;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/8/28 17:00
 * @Description:
 */
@Data
public class HistoricProcessInstanceVo implements Serializable {
    private String id;
    private String startUserId;
    private String startActivityId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private String processDefinitionVersion;
    private String deploymentId;
    private Date startTime;
    private Date endTime;
    private Long durationInMillis;
    private String processInstanceId;

    private String name;
    private String description;
    private Map<String,Object> processVariables;
}
