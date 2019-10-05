package com.zs.admin.api.entry;

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
 * @since 2019-10-04
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
    private String title;

    /**
     * 链接地址
     */
    private String href;

    /**
     * 图标
     */
    private String icon;

    /**
     * 跳转类型
     */
    private String target;

    /**
     * 描述
     */
    private String descr;

    /**
     * 资源类型
     */
    private Long categoryId;

    /**
     * 类型名
     */
    private String categoryName;

    /**
     * 是否父节点
     */
    private Boolean isParent;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 父节点标题
     */
    private String parentTitle;

    /**
     * 是否可编辑
     */
    private Boolean isEditable;

    /**
     * 排序
     */
    private Long ordinal;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
