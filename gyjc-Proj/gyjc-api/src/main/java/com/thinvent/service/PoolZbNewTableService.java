package com.thinvent.service;

import com.thinvent.entity.PoolZbNewTable;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * 指标新建表--接口
 * @author xufeng
 */
public interface PoolZbNewTableService {

    /**
     * 获取table信息
     * @param tableId
     * @return
     */
    PoolZbNewTable getPoolZbNewTable(String tableId);

    /**
     * 分页查询新建指标表数据
     * @param tableName 表名
     * @param pageNum 页数
     * @param tableType 表类型编码
     * @param createStartTime 表创建起始时间
     * @param createEndTime 表创建起始时间
     * @return
     */
    PageInfo getNewTableList(String tableName, int pageNum, String tableType, String createStartTime, String createEndTime);


    /**
     * 删除该表以及表格管理表内的数据
     * @param tableName 表名
     * @return 返回标识
     */
    int deleteTable(String tableName);

    /**
     * 删除该表与指标的关联关系，并删除其指标表内的所有关于该指标的数据
     * @param tableId 表ID
     * @param zbId 指标id
     */
    void deleteTabelZb(String tableId, String zbId);

    /**
     * 增加指标数据
     * @param tableId 表id
     * @param zbList 指标集合
     * @return 执行结果
     */
    int zbDataAddToTable(String tableId, String zbList);

    /**
     * 查询指标表分页数据
     * @param pageNum 分页页数
     * @param zbmc 指标名称
     * @param sourceTable 来源表
     * @param tableName 表名
     * @param startDate 起始日期
     * @param endDate 截止日期
     * @param dwmc 单位名称
     * @param dqmc 地区名称
     * @return 指标表分页数据
     */
    PageInfo getZbTableData(int pageNum, String zbmc, String sourceTable
            , String tableName, String startDate, String endDate, String dwmc, String dqmc);

    /**
     * 创建展示表并同步选定的指标数据
     * @param tableName 原表名
     * @param newTableName 创建的新表名
     * @param mapList 创建的指标
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 新增条数
     */
    int displayTableCreate(String tableName, String newTableName, List<HashMap> mapList, String startTime, String endTime);

    /**
     * 更新表数据
     * @param tableName 表名
     */
    void updateTableData(String tableName);
}
