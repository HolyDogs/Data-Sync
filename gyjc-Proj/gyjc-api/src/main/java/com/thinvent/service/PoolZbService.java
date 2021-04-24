package com.thinvent.service;

import com.thinvent.entity.PoolZb;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/7/17 15:36
 **/
public interface PoolZbService {

    /**
     * 查询指标池，通过条件
     * @param zbmc 指标名称
     * @param bindzb 绑定指标
     * @param state 状态
     * @param pageNum 页码
     * @param lyTable 来源表
     * @param zbfl 指标分类
     * @return
     */
    PageInfo getPoolZbByMC(String zbmc, String bindzb, String state, int pageNum, String lyTable, String zbfl);


    /**
     * 更新绑定指标
     * @param id id
     * @param bindZb 绑定指标
     * @return
     */
    int updateBindZb(String id, String bindZb);


    /**
     * 修改备注
     * @param id id
     * @param comments 备注
     * @return
     */
    int updateComment(String id, String comments);

    /**
     * 插入新数据
     * @param poolZb 指标池
     * @return
     */
    int insertPoolZb(PoolZb poolZb);

    /**
     * 解绑指标
     * @param id 指标id
     * @return 影响条数
     */
    int undebundBindZb(String id);

    /**
     * 获取所有指标名称和分类,用符号|连接(除sourceMark之外的)
     * @param sourceMark 来源表
     * @return 所有指标名称
     */
    List<HashMap<String, String>> getAllZbmcAndFl(String sourceMark);

    /**
     * 更新指标状态
     * @param idList id集合
     * @param state 状态
     * @return 更新数目
     */
    int updateStateByIds(List<String> idList, String state);

    /**
     * 通过来源id查询指标分类
     * @param lyid 来源id
     * @return 指标分类
     */
    String selectZbflByLyId(String lyid);


    /**
     * 获取不重复的指标分类
     * @return 指标分类集合
     */
    List<HashMap> getZbflList();

    /**
     * 根据指标名称指标分类获取指标
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param pageNum 页数
     * @param sourceMark 来源表
     * @return 分页数据
     */
    PageInfo getPoolZbByFl(String zbmc, String zbfl, int pageNum, String sourceMark);


    /**
     * 创建新表
     * @param tableName 表名
     * @param zbList 指标集合
     * @param tableComments 表备注
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @param tableTypeKey 主题表类型字典key
     * @return 结果标识
     */
    int createTableForZb(String tableName, String zbList, String tableComments
            , String startTime, String endTime, String tableTypeKey);

    /**
     * 获取指标树形结构数据
     * @return 树形结构数据
     */
    List<HashMap> getTreeList();

    /**
     * 根据id集合查询所有指标
     * @param idList 指标id集合 ，分隔
     * @return 指标集合
     */
    List<PoolZb> getZbListByIdList(String idList);


    /**
     * 更新计量单位
     * @param id 主键
     * @param jldw 计量单位
     * @return 更新条数
     */
    int updateJldwById(String id, String jldw);
}
