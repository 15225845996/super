package com.zs.admin.api.constant.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/10/5 07:52
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum  SourcesCategoryEnum {
    NAV(1L,"导航菜单"),
    OTHER(2L,"其他"),
            ;

    private Long categoryId;
    private String categoryName;
}
