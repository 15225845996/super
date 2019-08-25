package com.zs.admin.api.entry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysAccount implements Serializable {
  static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  private Long id;
  private String name;
  private String account;
  private String password;
  private Long categoryId;
  private String categoryName;
  private Long age;
  private String sex;

  @TableLogic
  @TableField(fill = FieldFill.INSERT)
  private Boolean isDeleted;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  private String creator;
  private String mender;
  @TableField(fill = FieldFill.UPDATE)
  private Date modifyTime;
}
