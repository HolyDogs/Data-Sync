package com.thinvent.mapper;

import com.thinvent.entity.PoolZb;
import com.thinvent.entity.UserSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @description -> ippmapper
 * @author -> xufeng
 * @date ->
 **/

@Mapper
public interface IppMapper {

    /**
     * 测试查询
     * @return 1
     */
    List<UserSpace> findData();

    /**
     * 插入迁移表数据
     * @param dataMap 迁移数据
     * @return 插入量
     */
    int insertToDataMove(@Param("theMap") HashMap<String, String> dataMap);

    /**
     * 查询指标池
     * @param zbmc 指标名称
     * @param bindZb 绑定指标
     * @param state 指标状态
     * @param lyTable 来源表
     * @param zbfl 指标分类
     * @return
     */
    List<PoolZb> getPoolZb(@Param("zbmc") String zbmc
            , @Param("bindzb") String bindZb
            , @Param("state") String state
            , @Param("lyTable") String lyTable
            , @Param("zbfl") String zbfl);

    /**
     * 更新指标属性
     * @param id id
     * @param bindZb 绑定指标
     * @param state 指标状态
     * @return 改变数目
     */
    int updateBindZbById(@Param("id") String id, @Param("bindzb") String bindZb
            , @Param("state") String state);

    /**
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @return
     */
    int findZbmc(@Param("zbmc") String zbmc, @Param("zbfl") String zbfl);


    /**
     * 插入
     * @param poolZb
     * @return
     */
    int insertPoolZb(@Param("poolzb") PoolZb poolZb);

    /**
     * 解绑指标
     * @param id 指标id
     * @return 影响条数
     */
    int unbundPoolZb(@Param("id") String id);

    /**
     * 动态创建指标表
     * @param tableName 表名
     */
    void createZbTable(@Param("tableName") String tableName);

    /**
     * 获取所有指标名称和分类（除指定来源表之外的）
     * @param sourceMark 来源表
     * @return 所有指标名称
     */
    List<HashMap<String, String>> getAllZbmcAndFl(@Param("sourceMark") String sourceMark);

    /**
     * 更新指标状态
     * @param idList id集合
     * @param state 状态
     * @return 更新条数
     */
    int updateStateByIds(@Param("idList") List<String> idList, @Param("state") String state);


    /**
     * 根据id或者指标名称查询指标信息
     * @param id id
     * @return poolzb
     */
    PoolZb getPoolZbById(@Param("id") String id);


    /**
     * 通过来源id查询指标分类
     * @param lyid 来源id
     * @return string
     */
    String selectZbflByLyId(@Param("id") String lyid);

    /**
     * 根据指标名称以及指标分类获取指标
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @return
     */
    PoolZb getPoolZbByZbAndFl(@Param("zbmc") String zbmc, @Param("zbfl") String zbfl);

    /**
     * 获取不充分的指标分类集合
     * @return 指标分类list
     */
    List<HashMap> getZbflList();

    /**
     * 获取指标
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param sourceMark 来源表
     * @return 指标集合
     */
    List<PoolZb> getPoolZbByFl(@Param("zbmc") String zbmc, @Param("zbfl") String zbfl, @Param("sourceMark") String sourceMark);


    /**
     * 通过id集合查询指标集合
     * @param idList id集合
     * @return 指标集合
     */
    List<PoolZb> getPoolZbsByIds(@Param("idList") List<String> idList);

    /**
     * 国家数据网数据查询指标后插入到指标新建表
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param jldw 计量单位
     * @param zbkey 指标编码
     */
    void insertIntoZbTableFromStatisticZb(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("zbfl") String zbfl
            , @Param("jldw") String jldw
            , @Param("zbkey") String zbkey
            , @Param("startTime") String startTime
            , @Param("endTime") String endTime);

    /**
     * 动态创建指标展示表
     * @param tableName 表名
     */
    void createZbZsTable(@Param("tableName") String tableName);

    /**
     * 获取指标分类以及来源集合
     * @return z
     */
    List<HashMap> getZbflAndLyList();


    /**
     * 修改备注
     * @param id id
     * @param comment 备注
     * @return
     */
    int updateCommentById(@Param("id") String id
            , @Param("comment") String comment);

    /**
     * 计量单位
     * @param id 主键
     * @param jldw 计量单位
     * @return x
     */
    int updateJldwById(@Param("id") String id
            , @Param("jldw") String jldw);

    /**
     * 判断指标池是否有该指标
     * @param zbmc 指标名称
     * @param lyTable 来源表
     * @param zbfl 指标分类
     * @return
     */
    int checkPoolZb(@Param("zbmc") String zbmc
            , @Param("lyTable") String lyTable
            , @Param("zbfl") String zbfl);

    /**
     * 获取库里指定来源当前最新的日期时间
     * @param lyId 来源ID
     * @return
     */
    String getLastDateByLyid(@Param("lyid") String lyId);
}
