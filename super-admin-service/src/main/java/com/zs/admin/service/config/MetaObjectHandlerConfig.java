package com.zs.admin.service.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zs.admin.api.constant.Constant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Auther: zs
 * @Date: 2019/8/25 13:18
 * @Description:
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentDate = new Date();
        //默认未删除
        setFieldValByName("isDeleted", Constant.DATA_NOT_DELETED,metaObject);
        //创建时间默认当前时间
        setFieldValByName("createTime", currentDate,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentDate = new Date();
        //修改时间
        setFieldValByName("modifyTime",currentDate,metaObject);
    }
}
