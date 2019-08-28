package com.zs.admin.service.mybatis;

import com.zs.admin.api.service.activiti.IActivitiService;
import com.zs.admin.api.vo.ModelVo;
import com.zs.admin.service.SuperAdminServiceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/8/28 10:23
 * @Description:
 */
public class ActivitiTest extends SuperAdminServiceApplicationTests {
    @Autowired
    private IActivitiService activitiService;

    @Test
    public void findModels(){
        List<ModelVo> models = activitiService.findModels();
        models.forEach(System.out::println);
        System.out.println("=-=========================");
    }
}
