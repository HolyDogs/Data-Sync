package com.thinvent.service.impl;

import com.thinvent.dimention.Dimention;
import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.service.CountTJService;
import com.thinvent.utils.GyjcConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/12/25 13:57
 **/
@Service
public class CountTjServiceImpl implements CountTJService {

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    public CountTjServiceImpl(List<Dimention> lists){
        for(Dimention d:lists){
            map.put(d.getName(),d);
        }
    }

    @Override
    public int countTJData(String tableName, String zbmc, String dataSource
            , String zbkey, String dimention, String startDate, String endDate
            , int limit){
        List<HashMap> mapList = map.get(dimention).getResult(tableName, zbmc, dataSource, zbkey, startDate, endDate, limit);
        if(!(mapList!=null && mapList.size()>0)){
            return 0;
        }
        if("全省总计".equals(dimention)){
            dimention="月度值";
        }else if("获取季度值".equals(dimention)){
            dimention = "季度值";
        }else if("获取年度值".equals(dimention)){
            dimention = "年度值";
        }
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        int count = 0;
        //将生成的统计数据插入展示统计表
        for (HashMap theMap:mapList) {
            //id
            theMap.put("ID", UUID.randomUUID().toString().replaceAll("-", ""));
            theMap.put("ZBMC", zbmc);
            Object sumValue = theMap.get("SUMVALUE");
            if (StringUtils.isEmpty(sumValue)) {
                //空值不录入
                continue;
            }
            count += 1;
            //toString避免oracle自动将小数点前面的0去掉
            theMap.put("VALUE", sumValue.toString());
            //theMap.put("JLDW", theMap.get("JLDW"));
            theMap.put("DATA_SOURCE", dataSource);
            theMap.put("LYZS_TABLE", tableName);
            //theMap.put("NF", theMap.get("NF"));
            theMap.put("YFORJD", theMap.get("YF"));
            theMap.put("ZBKEY", zbkey);
            //theMap.put("TIME_ID", theMap.get("TIME_ID"));
            //theMap.put("DQMC", theMap.get("DQMC"));
            //theMap.put("NEW_VALUE", ---);
            theMap.put("STATE", "1");
            theMap.put("STATISTIC_MARK", dimention);
            zsTjTableMapper.insertTjData(tjTableName, theMap);
        }
        return count;
    }

}
