package com.thinvent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author xf
 * @version 1.0
 * @date 2020/6/23 上午 11:06
 **/
@Configuration
public class TaskPoolConfig implements AsyncConfigurer {

    @Override
    @Bean("taskExecutor")
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //线程核心池、可能会用到的线程：监听端口线程、数据解析和插入线程、定时任务线程
        taskExecutor.setCorePoolSize(10);
        //线程核心池最大值
        taskExecutor.setMaxPoolSize(200);
        //线程等待队列0
        taskExecutor.setQueueCapacity(0);
        //线程空闲等待时间，超过60秒关闭
        taskExecutor.setKeepAliveSeconds(60);
        //线程前缀
        taskExecutor.setThreadNamePrefix("taskExecutor--");
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
    }
}
