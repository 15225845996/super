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
 * @since 2019-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_source")
public class RoleSource extends BaseEntity<RoleSource> {

    private static final long serialVersionUID=1L;

    private Long roleId;

    private String roleName;

    private Long sourceId;

    private String sourceName;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
