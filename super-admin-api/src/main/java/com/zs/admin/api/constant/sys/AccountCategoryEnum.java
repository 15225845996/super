package com.zs.admin.api.constant.sys;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
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

    public static String toJson(){
        JSONArray jsonArray = new JSONArray();
        for (AccountCategoryEnum e : AccountCategoryEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("categoryId", e.getCategoryId());
            object.put("categoryName", e.getCategoryName());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }
}
