package com.zs.admin.api.vo.sys;

import com.zs.admin.api.entry.Account;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: zs
 * @Date: 2019/8/28 11:33
 * @Description:
 */
@Data
public class AccountVo extends Account implements Serializable {
    static final Long serialVersionUID = 1L;
    //注册申请消息
    private String infoContent;
}
