package com.zs.admin.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: zs
 * @Date: 2019/10/11 09:16
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum SysLogTypeEnum {

    C(1L,"增"),
    R(2L,"查"),
    U(3L,"改"),
    D(4L,"删"),
    ;

    private Long typeId;
    private String typeName;
}
