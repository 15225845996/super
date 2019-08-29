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
@TableName("sys_account_role")
public class AccountRole extends BaseEntity<AccountRole> {

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
