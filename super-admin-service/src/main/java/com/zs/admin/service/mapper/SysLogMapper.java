package com.zs.admin.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.admin.api.entry.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2019-10-11
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    SysLog getDetail(@Param("id") Long id);

    List<SysLog> groupCount();
}
