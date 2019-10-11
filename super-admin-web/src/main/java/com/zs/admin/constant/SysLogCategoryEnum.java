package com.zs.admin.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/10/11 09:16
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum SysLogCategoryEnum {

    LOGIN(1L,"密码登录"),
    REGISTER(2L,"用户注册"),
    LOGOUT(3L,"退出"),
    SENSITIVE(4L,"敏感操作"),
    SPECIAL(5L,"特殊操作"),
    OTHER(-1L,"其他"),
    ;

    private Long categoryId;
    private String categoryName;
}
