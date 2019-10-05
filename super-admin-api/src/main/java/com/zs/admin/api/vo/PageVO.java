package com.zs.admin.api.vo;

import com.zs.admin.api.constant.Constant;
import lombok.Data;

import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/10/5 12:25
 * @Description:
 */
@Data
public class PageVO {
    private Long code;
    private String msg;
    private Long count;
    private List data;


    private PageVO(List list, Long count) {
        this.code = 0L;
        this.msg = "";
        this.count = count;
        this.data = list;
    }

    public static PageVO pageVO(List list, Long count) {
        return new PageVO(list, count);
    }
}
