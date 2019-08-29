package com.zs.admin.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.admin.api.entry.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2019-08-29
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
