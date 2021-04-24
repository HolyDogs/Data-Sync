package com.thinvent.utils;

import com.thinvent.entity.PoolZb;
import com.thinvent.mapper.ExcelHandImportMapper;
import com.thinvent.mapper.IppMapper;
import com.thinvent.mapper.PoolZbNewTableMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/8/14 16:03
 **/
@Component
public class ZbDataMoveUtils {

    @Autowired
    @SuppressWarnings("all")
    private IppMapper ippMapper;

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableMapper poolZbNewTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private ExcelHandImportMapper excelHandImportMapper;

    /**
     * @param tableName 表名
     * @param zbList 指标ID集合
     */
    public void findZbAndInsertToTable(String tableName, String zbList,String startTime,String endTime) {
        if (StringUtils.isEmpty(zbList)) {
            return;
        }
        String[] zbIdArr = zbList.split(",");
        //去重
        HashSet<String> idSet = new HashSet<>(Arrays.asList(zbIdArr));
        String startTimeNum=null;
        String endTimeNum=null;
        if(!StringUtils.isEmpty(startTime)) {
            String[] startTimes = startTime.split("-");
            startTimeNum = startTimes[0] + startTimes[1];
        }
        if(!StringUtils.isEmpty(endTime)) {
            String[] endTimes = endTime.split("-");
            endTimeNum = endTimes[0] + endTimes[1];
        }
        for (String zbId : idSet) {
            PoolZb poolZb = ippMapper.getPoolZbById(zbId);
            String sourceTable = poolZb.getSourceMark();
            String zbfl = poolZb.getZbfl();
            String zbmc = poolZb.getZbmc();
            String jldw = poolZb.getJldw();
            String zbkey = poolZb.getZbkey();
            if (GyjcConstant.DIC_STATISTIC_ZB.equals(sourceTable)) {
                //来源表为DIC_STATISTIC_ZB时
                ippMapper.insertIntoZbTableFromStatisticZb(tableName, zbmc, zbfl, jldw, zbkey,startTimeNum,endTimeNum);
            } else if (GyjcConstant.YW_SYSTEM_DATA_MOVE.equals(sourceTable)) {
                //来源表为YW_SYSTEM_DATA_MOVE时
                poolZbNewTableMapper.insertZbTableFromYWDATAMOVE(tableName, zbmc, zbfl, jldw, zbkey,startTimeNum,endTimeNum);
            } else if(GyjcConstant.EXCEL_HAND_IMPORT.equals(sourceTable)) {
                //来源表为EXCEL_HAND_IMPORT
                excelHandImportMapper.insertIntoZbTableFromHandImport(tableName, zbmc, zbfl, jldw, zbkey,startTimeNum,endTimeNum);
            }
        }
    }

    /**
     * 插入指标数据到展示表
     * @param tableName 原表名
     * @param newTableName 新创建表名
     * @param mapList 指标信息
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 插入条数
     */
    public int zbzsTableDataMove(String tableName, String newTableName, List<HashMap> mapList,String startTime,String endTime) {
        if (mapList == null || mapList.isEmpty()) {
            return 0;
        }
        int count = 0;
        String startDate=null;
        String endDate=null;
        if(!StringUtils.isEmpty(startTime)){
            String[] startTimes = startTime.split("-");
            startDate=startTimes[0]+startTimes[1];
        }
        if(!StringUtils.isEmpty(endTime)){
            String[] endTimes = endTime.split("-");
            endDate=endTimes[0]+endTimes[1];
        }
        for (HashMap map:mapList) {
            String sourceMark = MapUtils.getString(map, "sourceMark");
            count += poolZbNewTableMapper.insertZSZBData(tableName, newTableName, (String) map.get("zbkey"), startDate, endDate);
        }
        return count;
    }

}
