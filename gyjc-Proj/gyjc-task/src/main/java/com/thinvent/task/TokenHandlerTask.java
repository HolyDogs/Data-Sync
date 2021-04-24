package com.thinvent.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.SToken;
import com.thinvent.service.STokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 过期token处理，定时任务
 * @author xufeng
 * @version 1.0
 * @date 2020/11/13 11:28
 **/
@Component
@Slf4j
public class TokenHandlerTask {

    @Autowired
    private STokenService sTokenService;

    /**
     * 整点清除失效token
     */
    @Scheduled(cron = "0 0 * * * ?")
    @Async("taskExecutor")
    public void tokenDelete() {
        log.info("=======删除定时任务启动");
        QueryWrapper<SToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("FAILURE_TIME", new Date());
        sTokenService.remove(queryWrapper);
    }
}
