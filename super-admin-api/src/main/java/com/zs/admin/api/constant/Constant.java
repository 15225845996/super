package com.zs.admin.api.constant;

import jdk.nashorn.internal.objects.annotations.Function;

/**
 * @Auther: zs
 * @Date: 2019/8/24 11:51
 * @Description:
 */
public class Constant {

    /**
     * activiti模型默认版本
     */
    public static final Integer MODEL_DEFAULT_REVISION = 1;

    /**
     * resultvo 状态码
     */
    public static final Long RESULT_CODE_SUCCESS = 1L;
    public static final Long RESULT_CODE_FAIL = 0L;

    /**
     * 注册流程key
     */
    public static final String REGISTER_PROCESS_KEY = "registerProcess";


    /**
     * 未删除
     */
    public static final Boolean DATA_DELETED = true;

    /**
     * 已删除
     */
    public static final boolean DATA_NOT_DELETED = false;
}
