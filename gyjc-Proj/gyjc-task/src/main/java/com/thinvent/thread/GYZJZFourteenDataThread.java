package com.thinvent.thread;

import com.thinvent.gyjcEnum.EnrolEnum;
import com.thinvent.mapper.IppMapper;
import com.thinvent.mapper.MyMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * 工业增加值完成情况
 * @author xufeng
 * @version 1.0
 * @date 2020/7/1
 **/
@Component
public class GYZJZFourteenDataThread extends Thread {
    public static Integer getPageNum() {
        return pageNum;
    }

    public static void setPageNum(Integer pageNum) {
        GYZJZFourteenDataThread.pageNum = pageNum;
    }

    static Integer pageNum = 1;

    public static Integer getMaxPage() {
        return maxPage;
    }

    public static void setMaxPage(Integer maxPage) {
        GYZJZFourteenDataThread.maxPage = maxPage;
    }

    static Integer maxPage = 0;//记录最大页数

    public synchronized static int nextPage() {
        int i = getPageNum();
        maxPage = Math.max(maxPage, i);
        setPageNum(i+1);
        return i;
    }

    @SuppressWarnings("ALL")
    public GYZJZFourteenDataThread(IppMapper ippMapper, MyMapper myMapper) {
        this.myMapper = myMapper;
        this.ippMapper = ippMapper;
    }

    static int max;

    private IppMapper ippMapper;

    private MyMapper myMapper;

    @Override
    public void run(){
        while (true) {
            int i = nextPage();
            //分页查询
            PageHelper.startPage(i, 100);
            List<HashMap<String, String>> list = myMapper.selectGYZJZData14();
            //超过页数时跳出
            if (list == null || list.size() == 0) {
                break;
            }
            for (HashMap theMap:list) {
                String fz = (String) theMap.get("ENROL_NAME");
                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "本年实际本月");
                theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
                theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
                theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
                theMap.put("LYID", "20200701002");
                EnrolEnum enrol = EnrolEnum.get(String.valueOf(theMap.get("CALLING_ID")));
                if (enrol != null) {
                    theMap.put("ENROL_ID", enrol.getEnrolId());
                    theMap.put("ZBMCFZ", enrol.getEnrolName());
                } else {
                    theMap.put("ZBMCFZ", fz);
                }
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "本年实际本月止累计");
                theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "同比增长比同月");
                theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
                ippMapper.insertToDataMove(theMap);

                theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
                theMap.put("ENROL_NAME", "同比增长比同期");
                theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
                ippMapper.insertToDataMove(theMap);
            }
        }
    }
}
