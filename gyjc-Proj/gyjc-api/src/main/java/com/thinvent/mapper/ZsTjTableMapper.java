package com.thinvent.mapper;

import com.thinvent.dto.ZbZsTjTableDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @description -> 展示统计表mapper
 * @author -> xufeng
 * @date ->
 **/

@Mapper
public interface ZsTjTableMapper {


    /**
     * 更新源数据到统计表
     * @param tableName 数据表名
     * @param tjTableName 统计表名
     * @param zbkey 指标编码
     * @return 更新条数
     */
    int insertOriginData(@Param("tableName") String tableName
            , @Param("tjTableName") String tjTableName
            , @Param("zbkey") String zbkey);

    /**
     * 确认当前统计维度数据是否存在
     * @param tjTableName 统计表名
     * @param zbkey 指标编码
     * @param dimention 统计维度
     * @return 条数
     */
    int checkIsExistData(@Param("tjTableName") String tjTableName
            , @Param("zbkey") String zbkey
            , @Param("dimention") String dimention);

    /**
     * 生成每月统计数据（同时也按地区统计）
     * @param tableName 展示表名
     * @param zbkey 指标编码
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @return 数据
     */
    List<HashMap> perMonthCount(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);

    /**
     * 生成季度统计数据
     * @param tableName 表名
     * @param zbkey 指标编码
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @return 统计数据
     */
    List<HashMap> perQuaterCount(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);

    /**
     * 生成年度统计数据
     * @param tableNmae 表名
     * @param zbkey 指标编码
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @return 统计数据
     */
    List<HashMap> perYearCount(@Param("tableName") String tableNmae
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);


    /**
     * 插入数据
     * @param theMap 数据map
     * @param tjTableName 统计展示表名
     * @return 插入
     */
    int insertTjData(@Param("tableName") String tjTableName, @Param("theMap") HashMap theMap);

    /**
     * 查询统计数据
     * @param tjTableName 统计表名
     * @param nf 年份
     * @param yforjd 月份或者季度
     * @param zbkey 指标编码
     * @param dqbm 地区编码
     * @param dimention 统计维度
     * @return 值
     */
    String selectTjValue(@Param("tableName") String tjTableName
            , @Param("nf") String nf
            , @Param("yforjd") String yforjd
            , @Param("zbkey") String zbkey
            , @Param("dqbm") String dqbm
            , @Param("dimention") String dimention);

    /**
     * 删除统计数据
     * @param tjTableName 统计表名
     * @param zbkey 指标编码
     * @param dimention 统计维度
     * @return 删除条数
     */
    int deleteTjData(@Param("tableName") String tjTableName
            , @Param("zbkey") String zbkey
            , @Param("dimention") String dimention);

    /**
     * 生成江西在全国的排名数据
     * @param tableName 展示表名
     * @param zbkey 指标编码
     * @return 排名数据
     */
    List<HashMap> calcuRankOfJX(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey);


    /**
     * 生成江西在全国的占比
     * @param tableName 展示表名
     * @param zbkey 指标编码
     * @return 排名数据
     */
    List<HashMap> calcuRatioOfJX(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey);

    /**
     * 根据条件查询统计数据
     * @param tableName 统计表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源
     * @param dqmc 地区名称
     * @param statisticMark 统计维度
     * @return 统计数据
     */
    List<ZbZsTjTableDto> getZsTjTableData(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("dataSource") String dataSource
            , @Param("dqmc") String dqmc
            , @Param("statisticMark") String statisticMark
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);

    /**
     * 生成江西各市的统计数据
     * @param tableName 展示表名
     * @param zbkey 指标编码
     * @return 统计数据
     */
    List<HashMap> sumAreaPerMonthCount(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey);

    /**
     * 生成企业单位数量
     * @param tableName 展示表名
     * @param zbkeyList 指标key集合
     * @return 统计数据
     */
    List<HashMap> countDwNum(@Param("tableName") String tableName
            , @Param("keyList") List<String> zbkeyList);

    /**
     * 查询统计表月季度数据
     * @param tjTableName 统计表名
     * @param zbkey 指标编码
     * @param dimension 统计维度
     * @return 月度数据
     */
    List<HashMap> selectBaseTjData(@Param("tjTableName") String tjTableName
            , @Param("zbkey") String zbkey
            , @Param("dimension") String dimension);

    /**
     * 确认是否存在对应的全省数据
     * @param tjTableName 统计表名
     * @param zbkey 指标编码
     * @param dimension 统计维度
     * @param dqbm 地区编码
     * @return 数据数
     */
    int checkIsExistTjData(@Param("tjTableName") String tjTableName
            , @Param("zbkey") String zbkey
            , @Param("dimension") String dimension
            , @Param("dqbm") String dqbm);

    /**
     * 为累计值生成季度数据
     * @param tableName 展示表名
     * @param zbkey 指标key
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计结果
     */
    List<HashMap> getJdOfLjData(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);

    /**
     * 获取年度值
     * @param tableName 展示库
     * @param zbkey 指标编码
     * @param startDate 开始时间
     * @param endDate 截止时间
     * @return 年度数据
     */
    List<HashMap> getYearData(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate);

    /**
     * 统计企业排名
     * @param tableName 展示表名
     * @param zbkey 指标key
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @param limit 统计前多少名企业（ 实际值+1）
     * @param range 统计全省或者各市（1-各市， 其它-全省）
     * @return 统计数据
     */
    List<HashMap> calRankOfComp(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey
            , @Param("startDate") String startDate
            , @Param("endDate") String endDate
            , @Param("limit") int limit
            , @Param("range") int range);

    /**
     * 计算季度平均值
     * @param tableName 表名
     * @param zbkey 指标编码
     * @return 统计数据
     */
    List<HashMap> calAvgOfQuater(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey);

    /**
     * 计算年度平均值
     * @param tableName 表名
     * @param zbkey 指标编码
     * @return 统计数据
     */
    List<HashMap> calAvgOfYear(@Param("tableName") String tableName
            , @Param("zbkey") String zbkey);
}
