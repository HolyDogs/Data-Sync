package com.thinvent.service;

import java.util.HashMap;
import java.util.List;

/**
 * @description -> 外部接口调用接口
 * @author -> xufeng
 * @date ->
 **/

public interface ApiDataService {

    /**
     * 获取接口数据并存储  暂不需要
     * @param apiName 外部接口名
     * @param param 外部接口参数
     */
   void getDataAndSave(String apiName, String param);

    /**
     * 获取接口数据
     * @param apiName 外部接口名
     * @param param 接口参数
     * @return 接口数据
     */
   List<HashMap> getDataList(String apiName, String param);
}
