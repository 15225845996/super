package com.zs.admin.web.controller.activiti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.vo.activiti.HistoricProcessInstanceVo;
import com.zs.admin.api.vo.activiti.ModelVo;
import com.zs.admin.api.vo.ResultVo;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/activiti")
public class ActivitiController implements ModelDataJsonConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IActivitiService activitiService;

    //获取activiti编辑器
    @RequestMapping(value="/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存流程模型
     * @param modelId
     * @param name
     * @param json_xml
     * @param svg_xml
     * @param description
     */
    @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, @RequestParam("name") String name,
                          @RequestParam("json_xml") String json_xml,
                          @RequestParam("svg_xml") String svg_xml,
                          @RequestParam("description") String description) {//对接收参数进行了修改，修复400异常
        try {

            ModelVo model = activitiService.findModel(modelId);

            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            modelJson.put(MODEL_NAME, name);
            modelJson.put(MODEL_DESCRIPTION, description);
            model.setMetaInfo(modelJson.toString());
            model.setName(name);

            activitiService.saveModel(model);

            activitiService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            activitiService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();

        } catch (Exception e) {
            LOGGER.error("Error saving model", e);
        }
    }

    @RequestMapping(value="/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode modelNode = null;

        ModelVo model = activitiService.findModel(modelId);

        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(activitiService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);

            } catch (Exception e) {
                LOGGER.error("Error creating model JSON", e);
            }
        }
        return modelNode;
    }

    /**
     * 获取模型列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/modelList")
    public String modelList(org.springframework.ui.Model model,HttpServletRequest request){
        List<ModelVo> models = activitiService.findModels();
        model.addAttribute("models",models);
        return "model/list";
    }

    /**
     * 获取流程进度图
     * @param model
     * @param request
     * @param response
     * @param processId
     * @throws Exception
     */
    @RequestMapping("/taskImg")
    public void taskImg(org.springframework.ui.Model model,HttpServletRequest request,HttpServletResponse response,String processId) throws Exception{
        if(StringUtils.isNotBlank(processId)){
            byte[] processImage = activitiService.taskImg(processId);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(processImage);
            outputStream.flush();
            outputStream.close();
        }
    }

    @RequestMapping("/startTasksByAccount")
    @ResponseBody
    public ResultVo startTasksByAccount(HttpServletRequest request, HttpServletResponse response,String account,@RequestParam(value = "isEnd",defaultValue = "false")Boolean isEnd){
        if(StringUtils.isNotBlank(account)){
            //查询未结束的
            List<HistoricProcessInstanceVo> list = activitiService.startTasksByAccount(account,isEnd);
            return ResultVo.data(list);
        }
        return ResultVo.fail();
    }

    /**
     * 创建模型
     * @param request
     * @param response
     * @param modelName  模型名
     * @param modelKey     模型key
     * @param description  描述
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/create")
    public void newModel(HttpServletRequest request, HttpServletResponse response,String modelName,String modelKey,String description) throws Exception {
        String modelId = activitiService.createModel(modelName, modelKey, description);
        if(modelId != null){
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelId);
        }
    }

    /**
     * 删除流程模型
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public @ResponseBody ResultVo deleteModel(@PathVariable("id")String id){
        boolean result = activitiService.delModelById(id);
        if(result){
            return ResultVo.success();
        }else{
            return ResultVo.fail("删除失败！");
        }
    }


    @RequestMapping("/deployment/{id}")
    public @ResponseBody ResultVo deploy(@PathVariable("id")String id) throws Exception {
        return activitiService.deploymentModel(id);
    }
}