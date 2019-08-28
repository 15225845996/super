package com.zs.admin.service.impl.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zs.admin.api.constant.Constant;
import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.vo.ModelVo;
import com.zs.admin.api.vo.ResultVo;
import com.zs.utils.DozerUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author zs
 * @Description: {服务实现类}
 * @date 2019/8/15 12:00
 */
@Service("activitiService")
@Transactional(rollbackFor = Exception.class)
public class ActivitiServiceImpl implements IActivitiService {

    private Logger logger = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 通过流程id查询流程
     *
     * @param processInstanceId
     * @return
     */
    private HistoricProcessInstance queryHistoricProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    @Override
    public String startByKey(String key, Map<String, Object> map) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
        return processInstance == null?null:processInstance.getProcessInstanceId();
    }

    /**
     * 查看 定义的流程图
     *
     * @param processDefinitionId 流程id
     * @return
     */
    @Override
    public byte[] definitionImage(String processDefinitionId) throws IOException {
        BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);
        if (model != null && model.getLocationMap().size() > 0) {
            ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
            InputStream imageStream = generator.generateDiagram(model, "png", new ArrayList<>());
            byte[] buffer = new byte[imageStream.available()];
            imageStream.read(buffer);
            imageStream.close();
            return buffer;
        }
        return null;
    }


    /**
     * 获取流程图像，已执行节点和流程线高亮显示
     */
    @Override
    public byte[] getProcessImage(String processInstanceId) throws Exception {
        //  获取历史流程实例
        HistoricProcessInstance historicProcessInstance = queryHistoricProcessInstance(processInstanceId);
        if (historicProcessInstance == null) {
            throw new Exception();
        } else {
            // 获取流程定义
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
                    .getProcessDefinition(historicProcessInstance.getProcessDefinitionId());

            // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstanceList = historyService
                    .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceId().asc().list();

            // 已执行的节点ID集合
            List<String> executedActivityIdList = new ArrayList<String>();
            @SuppressWarnings("unused") int index = 1;
            logger.info("获取已经执行的节点ID");
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                executedActivityIdList.add(activityInstance.getActivityId());
                logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " + activityInstance
                        .getActivityName());
                index++;
            }
            // 获取流程图图像字符流
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
            DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
            //注意要设置字体否则图片中文会出现乱码
            InputStream imageStream = generator.generateDiagram(bpmnModel, "png", executedActivityIdList, Collections.<String>emptyList(), "宋体", "宋体", "宋体", null, 1.0);
            byte[] buffer = new byte[imageStream.available()];
            imageStream.read(buffer);
            imageStream.close();
            return buffer;
        }
    }

    @Override
    public List<ModelVo> findModels() {
        List<Model> list = repositoryService.createModelQuery().orderByCreateTime().desc().list();
        if(list != null){
            return DozerUtils.dozer(list, ModelVo.class);
        }
        return null;
    }

    @Override
    public ModelVo findModel(String modelId) {
        if(StringUtils.isNotBlank(modelId)){
            return DozerUtils.dozer(repositoryService.getModel(modelId),ModelVo.class);
        }
        return null;
    }

    @Override
    public void saveModel(ModelVo modelVo) {
        if(modelVo != null && modelVo.getId() != null){
            //获取到model信息，并跟新部分字段信息（modelVo中有的字段）
            Model model = repositoryService.getModel(modelVo.getId());
            DozerUtils.dozer(modelVo,model);
            repositoryService.saveModel(model);
        }
    }

    @Override
    public void addModelEditorSource(String modelId, byte[] bytes) {
        repositoryService.addModelEditorSource(modelId, bytes);
    }

    @Override
    public byte[] getModelEditorSource(String modelId) {
        if(StringUtils.isNotBlank(modelId)){
            return repositoryService.getModelEditorSource(modelId);
        }
        return null;
    }

    @Override
    public void addModelEditorSourceExtra(String modelId, byte[] bytes) {
        repositoryService.addModelEditorSourceExtra(modelId, bytes);
    }

    @Override
    public String createModel(String modelName, String modelKey, String description) {
        if(!StringUtils.isNotBlank(modelName)){
            modelName = "俺该叫个啥呢？";
        }
        if(!StringUtils.isNotBlank(modelKey)){
            modelKey = UUID.randomUUID().toString();
        }
        if(!StringUtils.isNotBlank(description)){
            description = "俺是谁？俺在那？俺要干啥？";
        }
        //初始化一个空模型
        Model model = repositoryService.newModel();
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, Constant.MODEL_DEFAULT_REVISION);
        //设置参数
        model.setMetaInfo(modelNode.toString());
        model.setName(modelName);
        model.setKey(modelKey);
        //保存模型
        repositoryService.saveModel(model);
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        try{
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
            return repositoryService.createModelQuery().modelId(model.getId()).singleResult().getId();
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public boolean delModelById(String modelId) {
        if(StringUtils.isNotBlank(modelId)){
            repositoryService.deleteModel(modelId);
            return true;
        }
        return false;
    }

    @Override
    public ResultVo deploymentModel(String modelId) {
        if(StringUtils.isNotBlank(modelId)){
            //获取模型
            Model modelData = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            if (bytes == null) {
                return ResultVo.fail("模型数据为空，请先设计流程并成功保存，再进行发布！");
            }
            try{
                JsonNode modelNode = new ObjectMapper().readTree(bytes);

                BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
                if(model.getProcesses().size()==0){
                    return ResultVo.fail("数据模型不符要求，请至少设计一条主线流程！");
                }
                byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

                //发布流程
                String processName = modelData.getName() + ".bpmn20.xml";
                Deployment deployment = repositoryService.createDeployment()
                        .name(modelData.getName())
                        .addString(processName, new String(bpmnBytes, "UTF-8"))
                        .deploy();
                modelData.setDeploymentId(deployment.getId());
                repositoryService.saveModel(modelData);
                return ResultVo.success();
            }catch (Exception e){
                e.printStackTrace();
                return ResultVo.fail();
            }
        }
        return ResultVo.fail("模型ID不能为空！");
    }

    @Override
    public boolean isDeploymentByKey(String key) {
        if(StringUtils.isNotBlank(key)){
            List<Deployment> list = repositoryService.createDeploymentQuery().deploymentKey(key).list();
            if(list != null && list.size() > 0){
                return true;
            }
        }
        return false;
    }


}
