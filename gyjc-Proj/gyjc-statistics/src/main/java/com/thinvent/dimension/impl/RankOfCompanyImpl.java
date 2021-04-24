package com.thinvent.dimension.impl;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//企业排名计算
@Service
public class RankOfCompanyImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Override
    public String getName() {
        return "企业排名";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        List<HashMap> mapList = zsTjTableMapper.calRankOfComp(tableName, zbkey, startDate, endDate, limit, 1);
        mapList.addAll(zsTjTableMapper.calRankOfComp(tableName, zbkey, startDate, endDate, limit, 0));
        return mapList;
    }

}
