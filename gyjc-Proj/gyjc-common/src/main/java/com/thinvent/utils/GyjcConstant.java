package com.thinvent.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 常量类
 * @author xufeng
 * @version 1.0
 * @date 2020/8/14 16:17
 **/
public class GyjcConstant {

    /**
     * 工业运行监测数据表
     */
    public final static String YW_SYSTEM_DATA_MOVE = "YW_SYSTEM_DATA_MOVE";

    /**
     * 国家数据网数据字典表
     */
    public final static String DIC_STATISTIC_ZB = "DIC_STATISTIC_ZB";

    /**
     * 国家数据网数据来源表
     */
    public final static String STATISTIC_ZB = "STATISTIC_ZB";

    /**
     * 新建指标表默认前缀
     */
    public final static String ZB_TABLE_PREFIX = "ZB_";

    /**
     * 统计表后缀
     */
    public final static String TJ_TABLE_END = "_TJ";

    /**
     * 新建指标展示表默认前缀
     */
    public final static String ZBZS_TABLE_PREFIX = "ZBZS_";

    /**
     * 手动导入来源表
     */
    public final static String EXCEL_HAND_IMPORT = "EXCEL_HAND_IMPORT";

    /**
     * 卫星地球站数据
     */
    public final static String SATELLITE_EARTH_STATION = "SATELLITE_EARTH_STATION";

    /**
     *  5G基站使用进度数据
     */
    public final static String BASESTATION_USE_SCHEDULE = "BASESTATION_USE_SCHEDULE";

    /**
     *  5G基站协调进度数据
     */
    public final static String BASESTATION_COORDINATION = "BASESTATION_COORDINATION";

    /**
     * 特色工业指标表
     */
    public final static String ZB_TSGYZB_ZS_TJ = "ZB_TSGYZB_ZS_TJ";

    /**
     * 在线监测表
     */
    public final static String BASIC_PROPERTY_ONLINE = "BASIC_PROPERTY_ONLINE";
    /**
     * 威胁诱捕
     */
    public final static String THREAT_TRAP_ONLINE = "THREAT_TRAP_ONLINE_LOG";
    /**
     * 字典表常量（地区分类）
     */
    public final static Integer DQ_GROUPNO = 1;

    /**
     * 字典表常量（国标行业分类）
     */
    public final static Integer GBHY_GROUPNO = 2;

    /**
     * 字典表常量（指标来源分类）
     */
    public final static Integer ZBLY_GROUPNO = 9;

    /**
     * 字典表常量（指标分类分类）
     */
    public final static Integer ZBFL_GROUPNO = 1111;

    /**
     * 字典表常量（指标名称分类）
     */
    public final static Integer ZBMC_GROUPNO = 1112;

    /**
     * 字典表常量（统计维度分类）
     */
    public final static Integer TJWD_GROUPNO = 10;

    /**
     * 生效时长 30分钟 --30*60*1000
     */
    public final static Long ENABLE_TIME = 1800000L;

    /**
     * TOKEN 刷新时间 10分钟
     */
    public final static Long FLASH_TIME = 600000L;

    /**
     * @param tableName 来源表名
     * @return 来源名
     */
    public static String convertZbly(String tableName) {
        if (GyjcConstant.YW_SYSTEM_DATA_MOVE.equals(tableName)) {
            return "工业运行监测";
        } else if (GyjcConstant.DIC_STATISTIC_ZB.equals(tableName)) {
            return "国家数据网";
        } else if (GyjcConstant.EXCEL_HAND_IMPORT.equals(tableName)){
            return "手动导入";
        } else {
            return "";
        }
    }

    public static List<HashMap> getSelectOptions(boolean flag) {
        //国家数据网
        HashMap<String, String> gjMap = new HashMap<>(16);
        if (flag) {
            gjMap.put("value", GyjcConstant.STATISTIC_ZB);
        } else {
            gjMap.put("value", GyjcConstant.DIC_STATISTIC_ZB);
        }
        gjMap.put("label", "国家数据网");
        //工业运行监测
        HashMap<String, String> gyMap = new HashMap<>(16);
        gyMap.put("value", GyjcConstant.YW_SYSTEM_DATA_MOVE);
        gyMap.put("label", "工业运行监测");
        //手动导入
        HashMap<String, String> sdMap = new HashMap<>(16);
        sdMap.put("value", GyjcConstant.EXCEL_HAND_IMPORT);
        sdMap.put("label", "手动导入");
        return Arrays.asList(gjMap, gyMap, sdMap);
    }
}
