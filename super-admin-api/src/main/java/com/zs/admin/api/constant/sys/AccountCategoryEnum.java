package com.zs.admin.api.constant.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/8/25 17:06
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum AccountCategoryEnum {

    ADMIN(1L,"管理员"),
    USER(2L,"用户"),
    ;

    private Long categoryId;
    private String categoryName;
}
