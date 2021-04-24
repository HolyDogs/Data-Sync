package com.thinvent.task;

import com.thinvent.mapper.DateDicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;

/**
 * 处理时间字典表的定时任务
 *
 * @author xufeng
 * @version 1.0
 * @date 2020/7/29 15:35
 **/
@Slf4j
//@Component 打开即可生效
public class UpdateDicTask {

    @Autowired
    @SuppressWarnings("all")
    private DateDicMapper dateDicMapper;

    @Scheduled(cron = "0 0 2 1 * ?")
    @Async("taskExecutor")
    public void updateDicDate() {
        log.info("TIME TASK IS RUNNING!~~ \n");
        List<HashMap<String, String>> timeList = dateDicMapper.findNotExistTimeId();
        if (timeList != null && timeList.size() > 0) {
            for (HashMap hashMap:timeList) {
                String qssj = (String) hashMap.get("QSSJ");
                String sjmc = qssj.replaceFirst("-", "年")
                        .replaceFirst("-", "月");
                sjmc = sjmc.substring(0, sjmc.length()-2);
                log.info(sjmc);
                hashMap.put("SJMC", sjmc);
                hashMap.put("SJZ", "月度");
                hashMap.put("SJBM", sjmc);
                dateDicMapper.saveDicDateZb(hashMap);
            }
        }

    }

}
