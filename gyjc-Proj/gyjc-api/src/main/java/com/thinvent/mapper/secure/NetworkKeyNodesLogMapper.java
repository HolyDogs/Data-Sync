package com.thinvent.mapper.secure;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.secure.NetworkKeyNodesLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2021-01-28
 */
@Mapper
@DS("safe")
public interface NetworkKeyNodesLogMapper extends BaseMapper<NetworkKeyNodesLog> {

}
