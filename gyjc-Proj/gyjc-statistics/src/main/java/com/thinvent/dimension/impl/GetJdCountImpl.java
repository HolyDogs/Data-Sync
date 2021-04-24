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
//获取季度值计算
@Service
public class GetJdCountImpl implements Dimention {

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Override
    public String getName() {
        return "获取季度值";
    }

    @Override
    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit) {
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        int i = zsTjTableMapper.checkIsExistData(tjTableName, zbkey, "季度值");
        if (i > 0) {
            return null;
        }
        startDate = GyDateUtils.quaterStartStrFormat(startDate);
        endDate = GyDateUtils.quaterEndStrFormat(endDate);
        //获取3、6、9、12月份的数据记录成季度值
        return zsTjTableMapper.getJdOfLjData(tableName, zbkey, startDate, endDate);
    }

}
