package com.zs.admin.annotation;

import com.zs.admin.constant.SysLogCategoryEnum;
import com.zs.admin.constant.SysLogTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: zs
 * @Date: 2019/10/11 09:13
 * @Description:
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    //是否记录参数  默认记录
    boolean isRecordParam() default true;

    //描述 默认无
    String desc() default "无";

    //日志类型 默认敏感操作
    SysLogCategoryEnum category() default SysLogCategoryEnum.SENSITIVE;

    //操作类型 默认查
    SysLogTypeEnum type() default SysLogTypeEnum.R;
}
