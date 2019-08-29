package com.zs.admin.service.impl.sys;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.admin.api.entry.Resource;
import com.zs.admin.api.service.sys.IResourceService;
import com.zs.admin.service.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2019-08-29
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
