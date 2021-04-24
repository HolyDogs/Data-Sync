package com.thinvent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.ApiWatcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口   需要删除
 * </p>
 *
 * @author yb
 * @since 2020-11-27
 */
public interface ApiWatcherMapper extends BaseMapper<ApiWatcher> {

    List<ApiWatcher> getApiInfo(@Param("provider") String provider,
                                @Param("producer") String producer,
                                @Param("result") String result,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime);
}
