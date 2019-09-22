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
@TableName("sys_resource")
public class SysResource extends BaseEntity<SysResource> {

    private static final long serialVersionUID=1L;

    /**
     * 资源名
     */
    private String sourceName;

    private String sourceDescr;

    private Long categoryId;

    private String categoryName;

    private Boolean isParent;

    private Long parentSourceId;

    private String parentSourceName;

    private Boolean isEditable;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
