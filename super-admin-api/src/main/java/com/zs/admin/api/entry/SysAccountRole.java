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
@TableName("sys_account_role")
public class SysAccountRole extends BaseEntity<SysAccountRole> {

    private static final long serialVersionUID=1L;

    private String account;

    private String accountName;

    private Long roleId;

    private String roleName;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
