package com.thinvent.service;

import com.thinvent.dimention.Dimention;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据维度生成统计数据
 * @author -> xufeng
 * @date ->
 **/
public interface CountTJService {

    Map<String, Dimention> map=new HashMap<>();

    /**
     * 统计维度插入数据
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param dataSource 数据来源
     * @param zbkey 指标编码
     * @param dimention 维度
     * @param startDate 起始时间
     * @param endDate 截止时间
     * @param limit 前多少排名 非必填
     * @return 生成条数
     */
    int countTJData(String tableName, String zbmc, String dataSource
            , String zbkey, String dimention, String startDate, String endDate
            , int limit);

}
