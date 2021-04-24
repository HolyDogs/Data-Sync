package com.thinvent.request;

import java.util.HashMap;
import java.util.List;

/**
 * @description -> 请求外部api接口
 * @author -> xufeng
 * @date ->  2020/7/30
 **/
public interface BaseRequestClient {

    /**
     * 请求外部api
     * @param apiName api名
     * @param param 参数
     * @return 转化成map结果返回
     */
    List<HashMap> requestForApi(String apiName, String param);
}
