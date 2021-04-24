package com.thinvent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.SSystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yb
 * @since 2020-11-12
 */
@Mapper
public interface SSystemLogMapper extends BaseMapper<SSystemLog> {

    public List<SSystemLog> getLogInfo(@Param("loginName") String loginName,
                                       @Param("userName") String userName,
                                       @Param("startTime") String startTime,
                                       @Param("endTime") String endTime);

}
