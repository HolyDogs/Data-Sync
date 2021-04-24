package com.thinvent.request.impl;

import com.thinvent.gyjcEnum.FrApiResultEnum;
import com.thinvent.request.BaseRequestClient;
import com.thinvent.request.BaseRequestUtil;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/7/30 14:18
 **/
@Component
public class DoRequestClient implements BaseRequestClient {

    @Autowired
    private BaseRequestUtil baseRequestUtil;

    @Value("${requestApi.url}")
    private String urlHead;

    @Override
    public List<HashMap> requestForApi(String apiName, String param) {
        FrApiResultEnum frApiResultEnum = FrApiResultEnum.valueOf(apiName);
        String url = frApiResultEnum.getApiAddress();
        String paramName = frApiResultEnum.getParamName();
        url = urlHead + url + "&" + paramName + "=" + param;
        String header = frApiResultEnum.getAuthHeader();
        //请求并获取返回的json
        String json = baseRequestUtil.getJson(url, header);
        //解析请求结果
        Boolean success = JsonPath.read(json, "$.success");
        if (success) {
            //若成功请求，解析返回的数据装入hashMap返回
            return JsonPath.read(json, "$.data.[*]");
        }
        return null;
    }
}
