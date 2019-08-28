package com.zs.admin.api.service.activiti;

import com.zs.admin.api.vo.activiti.HistoricProcessInstanceVo;
import com.zs.admin.api.vo.activiti.ModelVo;
import com.zs.admin.api.vo.ResultVo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/8/21 14:35
 * @Description:
 */
public interface IActivitiService {

    String startByKey(String key,String starter,Map<String,Object> map);

    /**
     * 查看定义的流程图
     *
     * @param processDefinitionId
     * @return
     */
    byte[] defaultTaskImg(String processDefinitionId) throws IOException;

    /**
     * 查看流程进度图
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    byte[] taskImg(String processInstanceId) throws Exception;


    List<ModelVo> findModels();

    ModelVo findModel(String modelId);

    void saveModel(ModelVo model);

    void addModelEditorSource(String modelId ,byte[] bytes);

    byte[] getModelEditorSource(String modelId);

    void addModelEditorSourceExtra(String modelId,byte[] bytes);

    /**
     * 创建模型
     * @param modelName  名字
     * @param modelKey      key
     * @param description  描述
     * @return
     */
    String createModel(String modelName,String modelKey,String description);


    /**
     * 根据id删除流程模型
     * @param modelId
     * @return
     */
    boolean delModelById(String modelId);

    /**
     * 部署流程模型
     */
    ResultVo deploymentModel(String modelId);

    /**
     * 查找流程是否已经部署
     */
    boolean isDeploymentByKey(String key);

    /**
     * 查找用户发起的流程
     * isEnd:是否已结束
     */
    List<HistoricProcessInstanceVo> tasksByAccount(String account, Boolean isEnd);

}
