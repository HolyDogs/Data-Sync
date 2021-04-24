package com.thinvent.dimension.impl;

import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.utils.GyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//年度值计算
@Service
public class PerYearCountImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Override
    public String getName() {
        return "年度值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        endDate = GyDateUtils.yearEndStrFormat(endDate);
        List<HashMap> mapList = zsTjTableMapper.perYearCount(tableName, zbkey, startDate, endDate);
        return mapList;
    }
}
