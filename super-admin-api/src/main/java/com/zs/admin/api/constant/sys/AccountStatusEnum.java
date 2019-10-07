package com.zs.admin.api.constant.sys;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/10/7 15:26
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {

    NORMAL(0L,"正常"),
    LOCAKED(-1L,"锁定"),
    ;

    private Long statusId;
    private String statusName;

    public static String toJson(){
        JSONArray jsonArray = new JSONArray();
        for (AccountStatusEnum e : AccountStatusEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("statusId", e.getStatusId());
            object.put("statusName", e.getStatusName());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }
}
