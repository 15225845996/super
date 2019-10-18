package com.zs.admin.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.admin.api.entry.SysLog;
import com.zs.admin.api.vo.PageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2019-10-11
 */
public interface ISysLogService extends IService<SysLog> {

    boolean save2(SysLog log);

    boolean saveBatch2(List<SysLog> log);


    PageVO page(SysLog account, Integer pageNum, Integer pageSize);

    SysLog getDetail(Long id);

    List<SysLog> groupCount();

}
