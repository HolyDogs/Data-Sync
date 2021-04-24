package com.thinvent.service.impl;

import com.thinvent.gyjcEnum.FrApiResultEnum;
import com.thinvent.request.impl.DoRequestClient;
import com.thinvent.service.ApiDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/12/25 13:49
 **/
@Service
public class ApiDataServiceImpl implements ApiDataService {

    @Autowired
    private DoRequestClient doRequestClient;

    @Override
    public void getDataAndSave(String apiName, String param) {
        List<HashMap> myMap = doRequestClient.requestForApi(apiName, param);
        if (null != myMap && myMap.size() > 0) {
            //todo xff save mymap
            String tableName = FrApiResultEnum.valueOf(apiName).getTableName();
        }
    }

    @Override
    public List<HashMap> getDataList(String apiName, String param) {
        return doRequestClient.requestForApi(apiName, param);
    }
}
