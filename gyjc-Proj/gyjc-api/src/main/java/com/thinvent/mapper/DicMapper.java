package com.thinvent.mapper;

import com.thinvent.entity.DicItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author yangbin
 * @version 1.0
 * @date 2020/10/16
 **/
@Mapper
public interface DicMapper  {

    /**
     * 根据item_value以及group_no查询编码
     * @param groupNo 字典类型
     * @param dataType 类型
     * @param enable 是否需要enable,0-选择禁止的，1-需要不被禁用的，其他-不需要该字段
     * @return 编码值
     */
    List<HashMap<String, String>> getDicList(@Param("groupNo") Integer groupNo,
                                             @Param("dataType") Integer dataType,
                                             @Param("enable") Integer enable);

    /**
     * 根据item_value以及group_no查询编码
     * @param itemValue item值
     * @param groupNum 字典类型
     * @return 编码值
     */
    String getItemKeyByValue(@Param("itemValue") String itemValue
            , @Param("groupNum") String groupNum);

    List<DicItem> getDicInfo(@Param("groupNo") Integer groupNo,
                             @Param("itemKey") String itemKey,
                             @Param("itemParent") List<String> itemParent,
                             @Param("itemValue") String itemValue,
                             @Param("areaLevel") Integer areaLevel,
                             @Param("dataType") Integer dataType);

    List<String> getMaxOrSelf(@Param("itemKey") String itemKey,
                              @Param("groupNo") Integer groupNo,
                              @Param("itemValue") String itemValue);
}
