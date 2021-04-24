package com.thinvent.dimension.impl;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import com.thinvent.utils.DimentionUtils;
import com.thinvent.utils.GyjcConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.thinvent.dimention.Dimention;

//季度环比计算
@Service
public class QuaterHBCountImpl implements Dimention{

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
        return "季度环比";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        //先计算季度值
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        List<HashMap> mapList = zsTjTableMapper.selectBaseTjData(tjTableName, zbkey, "季度值");
        return dimentionUtils.calcuMonthTB(mapList, tjTableName, zbkey, "JDHB");
    }

}
