package com.thinvent.dimension.impl;

import com.thinvent.mapper.ZsTjTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//月度值计算
@Service
public class PerMonthCountImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Override
    public String getName() {
        return "月度值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        List<HashMap> mapList = zsTjTableMapper.perMonthCount(tableName, zbkey, startDate, endDate);
        return mapList;
    }
}
