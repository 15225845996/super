package com.zs.admin.api.service.activiti;

import com.zs.admin.api.vo.ResultVo;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zs
 * @Date: 2019/8/21 14:35
 * @Description:
 */
public interface IActivitiService {

    ProcessInstance startByKey(String key,Map<String,Object> map);

    /**
     * 查看定义的流程图
     *
     * @param processDefinitionId
     * @return
     */
    byte[] definitionImage(String processDefinitionId) throws IOException;

    /**
     * 查看流程进度图
     *
     * @param pProcessInstanceId
     * @return
     * @throws Exception
     */
    byte[] getProcessImage(String pProcessInstanceId) throws Exception;


    List<Model> findModels();

    Model findModel(String modelId);

    void saveModel(Model model);

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
    Model createModel(String modelName,String modelKey,String description);


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

}
