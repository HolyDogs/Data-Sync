package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.PoolZb;
import com.thinvent.entity.PoolZbNewTableZs;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2020-08-26
 */
public interface PoolZbNewTableZsService extends IService<PoolZbNewTableZs> {

    /**
     * id获取PoolZbNewTableZs
     * @return
     */
    PoolZbNewTableZs getPoolZbNewTableZs(String id);

    /**
     * 获取展示表分页数据
     * @param pageNum 分页
     * @param tableName 表名
     * @param tableType 表类型编码
     * @param createStartTime 创建起始时间
     * @param createEndTime 创建截止时间
     * @return 分页数据
     */
    PageInfo getZsTableList(int pageNum, String tableName, String tableType, String createStartTime, String createEndTime);

    /**
     * 删除表以及记录
     * @param tableName 表名
     */
    void deleteZsTable(String tableName);

    /**
     * 删除展示表与指定指标的关联关系，并删除其指标表内的所有关于该指标的数据
     * @param tableId 表ID
     * @param zbId 指标id
     */
    void deleteZsTabelZb(String tableId, String zbId);


    /**
     * 获取展示表未勾选的指标
     * @param tableId 表id
     * @param zbmc 指标名称
     * @param sourceMark 来源标志
     * @return 指标集合
     */
    List<PoolZb> getOtherZbList(String tableId, String zbmc, String sourceMark);

    /**
     * 为展示表新加指标
     * @param tableId 表id
     * @param zbList 指标id集合
     */
    int addZbToZsTable(String tableId, String zbList);

    /**
     * 修改指定表指定数据的新值
     * @param tableName 表名
     * @param dataId 需要修改的数据的id
     * @param newValue 修改的新值
     */
    void updateNewValueOfZsTable(String tableName, String dataId, String newValue);

    /**
     * 为选择的指标设置一个展示范围
     * @param tableName 表名
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @param zbMapList 指标map结合
     */
    void setDisplayDateRange(String tableName, String startDate, String endDate, List<HashMap> zbMapList);

    /**
     * 根据维度统计某指标的数据并保存
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 来源表
     * @param dimention 统计维度
     * @return 新增条数
     */
    @Deprecated
    int countOneZbData(String tableName, String zbmc, String dataSource, String dimention);

    /**
     * 将统计数据（或源数据）加入统计表
     * @param tableName 表名
     * @param zbkey 指标编码
     * @param dimention 统计维度
     * @param zbmc 指标名称
     * @param dataSource 数据来源
     * @param startDate startDate 非必填
     * @param endDate endDate 非必填
     * @param limit 前多少排名 非必填
     * @return 新增条数
     */
    int countToTjTable(String tableName, String zbmc, String dataSource
            , String zbkey, String dimention, String startDate
            , String endDate, int limit);

    /**
     * 删除对应的统计数据
     * @param tableName 表名
     * @param zbkey 指标编码
     * @param dimention 统计维度
     * @return 删除条数
     */
    int deleteZbTjData(String tableName, String zbkey, String dimention);


    /**
     * 查询指标表分页数据
     * @param pageNum 分页页数
     * @param zbmc 指标名称
     * @param sourceTable 来源表
     * @param tableName 表名
     * @param startDate 起始日期
     * @param endDate 截止日期
     * @param statisticMark 统计维度表示
     * @param dqmc 地区名称
     * @return 指标表分页数据
     */
    PageInfo getZsTjTableData(int pageNum, String zbmc, String sourceTable
            , String tableName, String startDate, String endDate, String statisticMark, String dqmc);

    /**
     * 获取统计字典项数据
     * @param enable 是否需要enable
     * @return
     */
    List<HashMap<String, String>> getTJTree(String enable);

    /**
     * 获取地区字典项数据
     * @return
     */
    List<Object> getAreaTree();

    /**
     * 生成企业单位数
     * @param tableName 表名
     * @param zbkeyList 指标key集合
     * @return 生成数据条数
     */
    int countDwNum(String tableName, List<String> zbkeyList);
}
