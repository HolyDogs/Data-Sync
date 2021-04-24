package com.thinvent.mapper;

import com.thinvent.entity.PoolZbNewTable;
import com.thinvent.dto.ZbTableDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/8/12 10:12
 **/
@Mapper
public interface PoolZbNewTableMapper {

    /**
     * 插入新数据
     * @param poolZbNewTable 新建指标表数据
     */
    void insertData(@Param("newTableData") PoolZbNewTable poolZbNewTable);

    /**
     * 根据表名查询数据 模糊查询
     * @param tableName 表名
     * @param tableType 表类型编码
     * @return dataList
     */
    List<PoolZbNewTable> selectNewTableData(@Param("tableName") String tableName,
                                            @Param("tableType") String tableType,
                                            @Param("createStartTime") String createStartTime,
                                            @Param("createEndTime") String createEndTime);


    /**
     * 删除该表
     * @param tableName 表名
     */
    void dropTable(@Param("tableName") String tableName);


    /**
     * 删除该表名在POOL_ZB_NEW_TABLE中的记录
     * @param tableName 表名
     */
    void deleteTableByTableName(@Param("tableName") String tableName);


    /**
     * 获取指标表信息
     * @param id ID
     * @return 指标表信息
     */
    PoolZbNewTable getPoolZbNewTableById(@Param("id") String id);

    /**
     * 更新表关联指标关系
     * @param poolZbNewTable 指标表
     */
    void updatePoolZbNewTableById(@Param("theData") PoolZbNewTable poolZbNewTable);

    /**
     * 删除表内所有zbmc的数据
     * @param tableName 表名
     * @param zbkey 指标key
     */
    void deleteTableData(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey);

    /**
     * 查询并将指标数据插入到指标表  ---工业监测运行
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param jldw 计量单位
     * @param zbkey 指标编码
     */
    void insertZbTableFromYWDATAMOVE(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("zbfl") String zbfl
            , @Param("jldw") String jldw
            , @Param("zbkey") String zbkey
            , @Param("startTime") String startTime
            , @Param("endTime") String endTime);

    /**
     * 查询指标表数据
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源表
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @param dwmc 单位名称
     * @param dqmc 地区名称
     * @return zbtabledto list
     */
    List<ZbTableDto> selectZbTableData(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate
            , @Param("dwmc") String dwmc
            , @Param("dqmc") String dqmc);

    /**
     * 插入展示指标数据到新表 MODIFIED BY XUFENG 通过指标编码插入
     * @param tableName 表名
     * @param zbkey 指标编码
     * @param newTableName 插入表表名
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @return 插入条数
     */
    int insertZSZBData(@Param("tableName") String tableName
            , @Param("newTableName") String newTableName
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);

    /**
     * 根据表名查询数据
     * @param tableName 表名
     * @return dataList
     */
    PoolZbNewTable getNewTableData(@Param("tableName") String tableName);

    /**
     * 获取主题表中各指标最新数据的时间
     * @param tableName 表名
     * @return 指标ID+指标key+（日期+1月） 集合
     */
    List<HashMap<String, String>> getLatestDateOfZb(@Param("tableName") String tableName);
}
