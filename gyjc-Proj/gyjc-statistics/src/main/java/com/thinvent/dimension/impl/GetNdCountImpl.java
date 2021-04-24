package com.thinvent.dimension.impl;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.utils.GyDateUtils;
import com.thinvent.utils.GyjcConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//获取年度值计算
@Service
public class GetNdCountImpl implements Dimention {

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Override
    public String getName() {
        return "获取年度值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        int i = zsTjTableMapper.checkIsExistData(tjTableName, zbkey, "年度值");
        if (i > 0) {
            return null;
        }
        endDate = GyDateUtils.yearEndStrFormat(endDate);
        //获取年度值
        return zsTjTableMapper.getYearData(tableName, zbkey, startDate, endDate);
    }

}
