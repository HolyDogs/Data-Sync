package com.thinvent.dimension.impl;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.utils.GyjcConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;import com.thinvent.dimention.Dimention;

//全省总计计算
@Service
public class SumAreaPerMonthImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Override
    public String getName() {
        return "全省总计";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        int i = zsTjTableMapper.checkIsExistTjData(tjTableName, zbkey, "月度值", "360000");
        if (i > 0) {
            return null;
        }
        return zsTjTableMapper.sumAreaPerMonthCount(tableName, zbkey);
    }

}
