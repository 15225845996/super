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
@TableName("sys_account")
public class SysAccount extends BaseEntity<SysAccount> {

    private static final long serialVersionUID=1L;

    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    private Long categoryId;

    private String categoryName;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 性别
     */
    private String sex;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
