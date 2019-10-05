package com.zs.admin.api.constant.sys;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/10/5 13:58
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum RoleCategoryEnum {
    DEFAULT(0L,"默认角色"),
    CUSTOM(1L,"自定义"),
    ;

    private Long categoryId;
    private String categoryName;

    public static String toJson(){
        JSONArray jsonArray = new JSONArray();
        for (RoleCategoryEnum e : RoleCategoryEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("categoryId", e.getCategoryId());
            object.put("categoryName", e.getCategoryName());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }
}
