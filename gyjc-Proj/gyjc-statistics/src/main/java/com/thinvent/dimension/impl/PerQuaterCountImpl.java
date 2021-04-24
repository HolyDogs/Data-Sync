package com.thinvent.dimension.impl;

import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.utils.GyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//季度值计算
@Service
public class PerQuaterCountImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Override
    public String getName() {
        return "季度值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        startDate = GyDateUtils.quaterStartStrFormat(startDate);
        endDate = GyDateUtils.quaterEndStrFormat(endDate);
        List<HashMap> mapList = zsTjTableMapper.perQuaterCount(tableName, zbkey, startDate, endDate);
        return mapList;
    }
}
