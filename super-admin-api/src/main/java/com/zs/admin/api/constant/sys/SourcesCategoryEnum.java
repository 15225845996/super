package com.zs.admin.api.constant.sys;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/10/5 07:52
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum  SourcesCategoryEnum {
    MENU(1L,"菜单"),
    BUTTON(2L,"按钮"),
    OTHER(3L,"其他")
            ;

    private Long categoryId;
    private String categoryName;

    public static String toJson(){
        JSONArray jsonArray = new JSONArray();
        for (SourcesCategoryEnum e : SourcesCategoryEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("categoryId", e.getCategoryId());
            object.put("categoryName", e.getCategoryName());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }
}
