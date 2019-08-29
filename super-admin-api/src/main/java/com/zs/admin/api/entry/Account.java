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
@TableName("sys_account")
public class Account extends BaseEntity<Account> {

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
