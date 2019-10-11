package com.zs.admin.service.impl.sys;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.SysLog;
import com.zs.admin.api.service.sys.ISysLogInfoService;
import com.zs.admin.api.service.sys.ISysLogService;
import com.zs.admin.service.mapper.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2019-10-11
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Autowired
    private ISysLogInfoService logInfoService;

    @Override
    public boolean save2(SysLog log) {
        boolean save = super.save(log);
        if(save){
            if(CollUtil.isNotEmpty(log.getLogInfos())){
                log.getLogInfos().forEach(i -> i.setLogId(log.getId()));
                return logInfoService.saveBatch(log.getLogInfos());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean saveBatch2(List<SysLog> logs) {
        if(CollUtil.isNotEmpty(logs)){
            logs.forEach(i -> this.save2(i));
            return true;
        }
        return false;
    }
}
