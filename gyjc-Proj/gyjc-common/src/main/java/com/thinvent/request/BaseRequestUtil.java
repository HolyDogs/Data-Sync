package com.thinvent.request;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 请求工具
 *
 * @author xufeng
 * @version 1.0
 * @date 2020/7/30 14:39
 **/
@Component
@Slf4j
public class BaseRequestUtil {

    public Document getDoc(String url, Map<String, String> headers, Logger log)
    {
        Document doc = null;
        Connection connection = Jsoup.connect(url);
        addBasicHeaders(connection);
        if (headers != null)
        {
            connection.headers(headers);
        }
        try
        {
            doc = connection.timeout(10 * 1000).get();
        }
        catch (IOException e)
        {
            log.error("Fail to connect!", e);
        }
        return doc;
    }

    public String getJson(String url, String header)
    {
        String json = null;
        //创建连接
        Connection connection = Jsoup.connect(url);
        //设置认证
        connection.header("Authorization", header);
        try
        {
            //请求返回
            json = connection.ignoreContentType(true).execute().body();
        }
        catch (IOException e)
        {
            log.error("Fail to connect the website!", e);
        }
        return json;
    }

    private void addBasicHeaders(Connection connection)
    {
        connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Accept-Language", "zh-CN,zh;q=0.9");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
    }
}
