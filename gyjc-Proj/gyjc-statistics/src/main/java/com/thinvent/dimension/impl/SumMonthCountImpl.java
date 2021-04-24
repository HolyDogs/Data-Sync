package com.thinvent.dimension.impl;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.utils.DimentionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;import com.thinvent.dimention.Dimention;

//月度累计值计算
@Service
public class SumMonthCountImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Autowired
    private DimentionUtils dimentionUtils;

    @Override
    public String getName() {
        return "月度累计值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        //先计算月度值
        List<HashMap> mapList = zsTjTableMapper.perMonthCount(tableName, zbkey, startDate, endDate);
        return dimentionUtils.convertMonthTotal(mapList);
    }

}
