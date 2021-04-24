package com.thinvent.mapper.secure;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.secure.ThreatTrapOnlineLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yb
 * @since 2021-01-26
 */
@Mapper
@DS("safe")
public interface ThreatTrapOnlineLogMapper extends BaseMapper<ThreatTrapOnlineLog> {

}
