package com.thinvent.mapper.secure;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.secure.ActiveConnect;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2021-01-05
 */
@Mapper
@DS("secure")
public interface ActiveConnectMapper extends BaseMapper<ActiveConnect> {

}
