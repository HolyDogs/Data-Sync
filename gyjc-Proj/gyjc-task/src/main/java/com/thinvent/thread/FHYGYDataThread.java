package com.thinvent.thread;

import com.thinvent.mapper.IppMapper;
import com.thinvent.mapper.MyMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * 分行业工业企业主要经济指标线程
 * @author xufeng
 * @version 1.0
 * @date 2020/6/23 上午 11:50
 **/
@Component
public class FHYGYDataThread extends Thread {
    public static Integer getPageNum() {
        return pageNum;
    }

    public static void setPageNum(Integer pageNum) {
        FHYGYDataThread.pageNum = pageNum;
    }

    static Integer pageNum = 1;

    public static Integer getMaxPage() {
        return maxPage;
    }

    public static void setMaxPage(Integer maxPage) {
        FHYGYDataThread.maxPage = maxPage;
    }

    static Integer maxPage = 0;//记录最大页数

    public synchronized static int nextPage() {
        int i = getPageNum();
        maxPage = Math.max(maxPage, i);
        setPageNum(i+1);
        return i;
    }

    public FHYGYDataThread(IppMapper ippMapper, MyMapper myMapper) {
        this.myMapper = myMapper;
        this.ippMapper = ippMapper;
    }

    static int max;

    private IppMapper ippMapper;

    private MyMapper myMapper;

    public void run(){
        while (true) {
            int i = nextPage();
            //分页查询
            PageHelper.startPage(i, 100);
            List<HashMap<String, String>> list = myMapper.selectFHYGYJJData();
            //超过页数时跳出
            if (list == null || list.size() == 0) {
                break;
            }
            for (HashMap theMap:list) {
                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ZBMCFZ", theMap.get("ENROL_NAME"));
                theMap.put("ENROL_NAME", "企业单位数");
                theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
                theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
                theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
                theMap.put("LYID", "20200629001");
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "主营业务收入本月止累计");
                theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "主营业务收入同比增长");
                theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "利润总额本月止累计");
                theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "利润总额同比增长");
                theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "亏损企业亏损额本月止累计");
                theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "亏损企业亏损额同比增长");
                theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "利税总额本月止累计");
                theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "利税总额同比增长");
                theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "营业收入本月止累计");
                theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "营业收入同比");
                theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
                ippMapper.insertToDataMove(theMap);
            }
        }
    }
}
