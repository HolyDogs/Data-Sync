package com.thinvent.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/7/29 15:50
 **/
@Mapper
public interface DateDicMapper  {

    /**
     * 查询YW_SYSTEM_DATA_MOVE中有无没有对应时间字典表的数据，如果没有则返回
     * @return 不在字典表中的TIMEID
     */
    List<HashMap<String, String>> findNotExistTimeId();

    /**
     * 插入时间字典新数据
     * @param theMap 插入值
     */
    void saveDicDateZb(@Param("theMap") HashMap theMap);
}
