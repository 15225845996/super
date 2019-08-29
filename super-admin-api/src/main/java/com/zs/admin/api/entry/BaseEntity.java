package com.zs.admin.api.entry;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: zs
 * @Date: 2019/8/29 17:19
 * @Description:
 */
@Data
public class BaseEntity<T> implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;


    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private String creator;
    private String mender;
    @TableField(fill = FieldFill.UPDATE)
    private Date modifyTime;

    protected Serializable pkVal() {
        return null;
    }
}
