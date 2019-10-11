package com.zs.admin.api.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
@TableName("sys_log")
public class SysLog extends BaseEntity<SysLog> {

    private static final long serialVersionUID=1L;

    /**
     * 描述
     */
    private String descr;

    /**
     * 上一个url
     */
    private String proviousUrl;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;

    private Long categoryId;

    private String categoryName;

    /**
     * 类型
     */
    private Long typeId;

    /**
     * 类型
     */
    private String typeName;

    /**
     * 状态
     */
    private Long statusId;

    /**
     * 状态
     */
    private String statusName;

    @TableField(exist = false)
    private List<SysLogInfo> logInfos;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
