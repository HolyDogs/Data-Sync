package com.thinvent.dimension.impl;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//全国排名计算
@Service
public class RankOfJXImpl implements Dimention{

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Override
    public String getName() {
        return "全国排名";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        return zsTjTableMapper.calcuRankOfJX(tableName, zbkey);
    }

}
