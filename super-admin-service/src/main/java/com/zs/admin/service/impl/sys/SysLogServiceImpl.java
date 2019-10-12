package com.zs.admin.service.impl.sys;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.SysAccount;
import com.zs.admin.api.entry.SysLog;
import com.zs.admin.api.entry.SysLogInfo;
import com.zs.admin.api.service.sys.ISysLogInfoService;
import com.zs.admin.api.service.sys.ISysLogService;
import com.zs.admin.api.vo.PageVO;
import com.zs.admin.service.mapper.SysLogMapper;
import org.apache.commons.lang3.StringUtils;
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


    @Override
    public PageVO page(SysLog account, Integer pageNum, Integer pageSize) {
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        IPage<SysLog> page = super.page(new Page<>(pageNum, pageSize), getQuery(account));
        return PageVO.page(page.getRecords(), page.getTotal());
    }

    @Override
    public SysLog getDetail(Long id) {
        if(id != null){
            SysLog log = super.getById(id);
            if(log != null){
                QueryWrapper<SysLogInfo> logInfoQueryWrapper = new QueryWrapper<>();
                logInfoQueryWrapper.lambda().eq(SysLogInfo::getLogId,log.getId());
                List<SysLogInfo> logInfos = logInfoService.list(logInfoQueryWrapper);
                return log.setLogInfos(logInfos);
            }
        }
        return null;
    }


    protected QueryWrapper getQuery(SysLog log){
        QueryWrapper<SysLog> quer = new QueryWrapper<>();
        LambdaQueryWrapper<SysLog> lambda = quer.lambda().orderByDesc(SysLog::getCreateTime);
        if(log != null){
            if(log.getCategoryId() != null){
                lambda.eq(SysLog::getCategoryId,log.getCategoryId());
            }
            if(StringUtils.isNotBlank(log.getDescr())){
                lambda.like(SysLog::getDescr,"%"+log.getDescr()+"%");
            }
            if(log.getTypeId() != null){
                lambda.eq(SysLog::getTypeId,log.getTypeId());
            }
            if(log.getStatusId() != null){
                lambda.eq(SysLog::getStatusId,log.getStatusId());
            }
            if (StringUtils.isNotBlank(log.getMethod())){
                lambda.eq(SysLog::getMethod,log.getMethod());
            }
            if(StringUtils.isNotBlank(log.getUrl())){
                lambda.eq(SysLog::getUrl,log.getUrl());
            }
            if(StringUtils.isNotBlank(log.getProviousUrl())){
                lambda.eq(SysLog::getProviousUrl,log.getProviousUrl());
            }
            if(StringUtils.isNotBlank(log.getIp())){
                lambda.eq(SysLog::getIp,log.getIp());
            }
        }
        return quer;
    }
}
