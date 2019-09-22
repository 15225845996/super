package com.zs.admin.api.entry;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zs
 * @since 2019-08-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends BaseEntity<SysRole> {

    private static final long serialVersionUID=1L;

    private String roleName;

    private String roleDescr;

    private Long categoryId;

    private String categoryName;

    private Boolean isEditable;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
