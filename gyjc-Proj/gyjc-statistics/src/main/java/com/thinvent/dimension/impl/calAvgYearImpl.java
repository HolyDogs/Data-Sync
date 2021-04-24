package com.thinvent.dimension.impl;

import com.thinvent.dimention.Dimention;
import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

//年度平均值计算
@Service
public class calAvgYearImpl implements Dimention {

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Override
    public String getName() {
        return "年度平均值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        return zsTjTableMapper.calAvgOfYear(tableName, zbkey);
    }

}
