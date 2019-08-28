package com.zs.admin.api.vo.activiti;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zs
 * @Date: 2019/8/28 09:55
 * @Description:
 */
@Data
public class ModelVo implements Serializable {

    private String id;
    private String name;
    private String key;
    private String category;
    private String metaInfo;
    private String deploymentId;
    private Integer version;
    private String tenantId;
    private Date createTime;
    private Date lastUpdateTime;
}
