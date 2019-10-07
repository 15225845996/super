package com.zs.admin.api.entry;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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

    private Long statusId;

    private String statusName;

    /**
     * 年龄
     */
    private Long age;

    /**
     * 性别
     */
    private String sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;



    private String logo;
    private Long loginCount;
    private Date lastLoginTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
