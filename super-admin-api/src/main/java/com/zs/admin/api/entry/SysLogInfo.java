package com.zs.admin.api.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zs
 * @since 2019-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_log_info")
public class SysLogInfo extends BaseEntity<SysLogInfo> {

    private static final long serialVersionUID=1L;

    /**
     * 日志id
     */
    private Long logId;

    /**
     * 是否请求参数
     */
    @TableField("is_param")
    private Boolean isParam;

    /**
     * 参数key
     */
    @TableField("`key`")
    private String key;

    /**
     * 参数value
     */
    @TableField("`value`")
    private String value;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
