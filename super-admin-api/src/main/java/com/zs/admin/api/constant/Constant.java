package com.zs.admin.api.constant;


import javax.xml.transform.sax.SAXTransformerFactory;

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
     * 未删除
     */
    public static final boolean DATA_NOT_DELETED = false;

    /**
     * 登录用户信息key
     */
    public static final String USER_INFO_KEY = "USER_INFO_KEY";

    /**
     * 登录用户资源key
     */
    public static final String USER_SOURCES_KEY = "USER_SOURCES_KEY";

    /**
     * 用户左侧菜单key
     */
    public static final String USER_SOURCES_MENU_KEY = "USER_SOURCES_MENU_KEY";

    /**
     * 登录用户角色key
     */
    public static final String USER_ROLES_KEY = "USER_ROLES_KEY";

    /**
     * 后台首页地址
     */
    public static final String BACKSTAGE_INDEX_PAGE = "/page/index";

    /**
     * 后台home页面请求资源id
     */
    public static final Long BACKSTAGE_HOME_ID = 8L;

    /**
     * 清空缓存url
     */
    public static final String CLEAR_URL = "/api/clear.json";

    /**
     * logo信息
     */
    public static final String LOGO_TITLE = "LayuiMini";
    public static final String LOGO_IMG = "/images/logo.png";
    public static final String LOGO_HREF = "";

    /**
     * 菜单json 中 menu的key（固定）
     */
    public static final String INIT_MENU_INFO_KEY = "menuInfo";

}
