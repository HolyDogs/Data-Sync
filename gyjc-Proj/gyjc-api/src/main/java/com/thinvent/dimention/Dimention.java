package com.thinvent.dimention;

import java.util.HashMap;
import java.util.List;

//维度接口，增加、删除维度，只需要删除对应实现类
public interface Dimention {

    public String getName();

    public List<HashMap> getResult(String tableName, String zbmc, String dataSource
            , String zbkey, String startDate, String endDate
            , int limit);

}