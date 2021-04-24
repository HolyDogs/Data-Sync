package com.thinvent.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/7/2 上午 11:24
 **/
@Mapper
@DS("gyjc")
public interface MonthMapper {
    /**
     * @description ->重点企业财务状况主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData1(@Param("start") String start);

    /**
     * @description ->分行业工业企业主要经济指标
     * @author -> xufeng
     * @param start date
     * @date -> 0703
     **/
    List<HashMap<String, String>> findByTimeData2(@Param("start") String start);

    /**
     * @description ->分行业工业生产者出厂价格指数
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData3(@Param("start") String start);

    /**
     * @description ->全社会用电分类（全口径）
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData4(@Param("start") String start);

    /**
     * @description ->中国制造业采购经理指数
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData5(@Param("start") String start);

    /**
     * @description ->重点工业园区主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData6(@Param("start") String start);

    /**
     * @description ->重点企业产销总值及主要产品产量
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData7(@Param("start") String start);

    /**
     * @description ->重点企业主要产品销售、库存、订货及价格
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData8(@Param("start") String start);

    /**
     * @description ->重点企业主要经济指标月报表
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData9(@Param("start") String start);

    /**
     * @description ->园区工业企业主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData10(@Param("start") String start);

    /**
     * @description ->各设区市主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData11(@Param("start") String start);

    /**
     * @description ->工业企业主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData12(@Param("start") String start);

    /**
     * @description ->工业园区主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData13(@Param("start") String start);

    /**
     * @description ->工业增加值完成情况
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData14(@Param("start") String start);

    /**
     * @description ->工业主要产品产量
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData15(@Param("start") String start);

    /**
     * @description ->江西省工业分县（市、区）主要经济指标
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData16(@Param("start") String start);

    /**
     * @description ->全国各地区工业增加值（只抽江西的）
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData17(@Param("start") String start);

    /**
     * @description ->全国各地区工业产品销售率（只抽江西的）
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData18(@Param("start") String start);

    /**
     * @description ->全国分地区工业增加值和产品销售率完成情况
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData19(@Param("start") String start);

    /**
     * @description ->全国分地区主要工业产品生产完成情况（一）
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData20(@Param("start") String start);

    /**
     * @description ->全国分地区主要工业产品生产完成情况（二）
     * @author -> xufeng
     * @param start date
     * @date ->
     **/
    List<HashMap<String, String>> findByTimeData21(@Param("start") String start);

}
