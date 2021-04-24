package com.thinvent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.PoolZbNewTableZs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2020-08-26
 */
@Mapper
public interface PoolZbNewTableZsMapper extends BaseMapper<PoolZbNewTableZs> {

    /**
     * 删除该表
     * @param tableName 表名
     */
    void dropTable(@Param("tableName") String tableName);

    /**
     * 删除该表下的所有指定的指标数据
     * @param tableName 表名
     * @param zbkey zbkey
     */
    void deleteZsZbData(@Param("tableName") String tableName, @Param("zbkey") String zbkey);

    /**
     * 修改指定表指定数据的newValue值
     * @param tableName 表名
     * @param id 数据id
     * @param newValue 新值
     */
    void updateNewValueOfZsTable(@Param("tableName") String tableName
            , @Param("dataId") String id
            , @Param("newValue") String newValue);

    /**
     * 根据时间范围更新展示表中某指标的展示状态
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 指标来源
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @param state 展示状态 1为展示，0为不展示
     */
    void updateStateOfZsTableByDateRange(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate
            , @Param("state") String state);

    /**
     * 设置范围外的展示指标数据不可见
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 指标来源
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @param state 展示状态 1为展示，0为不展示
     */
    void setUnVisibleZsZbDataRange(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate
            , @Param("state") String state);

    /**
     * 根据地区统计指标值
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源表
     * @return 统计结果
     */
    List<HashMap<String, String>> countValueGroupByArea(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource);

    /**
     * 根据月度统计指标值
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源表
     * @return 统计结果
     */
    List<HashMap<String, String>> countValueGroupByMonth(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource);


    /**
     * 根据季度统计指标值
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源表
     * @return 统计结果
     */
    List<HashMap<String, String>> countValueGroupByQuarter(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource);

    /**
     * 根据年度统计指标值
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源表
     * @return 统计结果
     */
    List<HashMap<String, String>> countValueGroupByYear(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource);

    /**
     * 插入数据到展示表
     * @param hashMap 数据map
     * @param tableName  表名
     */
    void insertDataToZsTable(@Param("tableName") String tableName
            , @Param("map") HashMap hashMap);

    /**
     * 监测是否已存在统计数据
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源
     * @param statisticMark 统计维度
     * @return 存在条数
     */
    int checkCountDataExist(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource
            , @Param("statisticMark") String statisticMark);

    /**
     * 创建统计展示表
     * @param tableName 统计展示表名
     */
    void createZsTjTable(@Param("tableName") String tableName);
}
