package com.thinvent.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.YwSystemDataMove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 各业务系统数据迁移 Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
@Mapper
public interface YwSystemDataMoveMapper extends BaseMapper<YwSystemDataMove> {

    /**
     * 查询指标分类集合
     * @return 下拉框集合
     */
    List<HashMap<String, String>> getSelectList();

    /**
     * 获取数据量
     * @param tableName 表名
     * @return 数据量
     */
    int getDataCount(@Param("tableName") String tableName);

    /**
     * 获取safe数据源的表数据量
     * @param tableName 表名
     * @return 数据量
     */
    @DS("safe")
    int getSafeDataCount(@Param("tableName") String tableName);

    /**
     * 获取上个月的数据量统计
     * @param tableName 表名
     * @return 数据量统计
     */
    Integer getLastMonthDataCount(@Param("tableName") String tableName);

    /**
     * 每月底将数据统计保存到GYJC_DATA_STATISTIC_COUNT表
     * @param tableName 表名
     */
    void insertIntoCountTableLastDayOfMonth(@Param("tableName") String tableName);

    /**
     * 针对某些不在同一源下的表进行统计
     * @param tableName 表名
     * @param countNum 统计数据
     */
    void insertIntoCountTableLastDayOfMonthSpecial(@Param("tableName") String tableName
            , @Param("countNum") int countNum);
}
