package com.thinvent.mapper.secure;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.secure.PropertyOnlineUploadRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yb
 * @since 2021-02-05
 */

@Mapper
@DS("safe")
public interface PropertyOnlineUploadRecordMapper extends BaseMapper<PropertyOnlineUploadRecord> {

}
