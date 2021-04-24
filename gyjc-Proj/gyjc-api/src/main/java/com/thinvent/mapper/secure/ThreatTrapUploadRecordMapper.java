package com.thinvent.mapper.secure;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.secure.ThreatTrapUploadRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yb
 * @since 2021-01-27
 */
@Mapper
@DS("safe")
public interface ThreatTrapUploadRecordMapper extends BaseMapper<ThreatTrapUploadRecord> {

}
