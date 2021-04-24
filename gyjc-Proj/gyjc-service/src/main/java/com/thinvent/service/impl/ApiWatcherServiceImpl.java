package com.thinvent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.ApiWatcher;
import com.thinvent.service.ApiWatcherService;
import com.thinvent.mapper.ApiWatcherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yb
 * @since 2020-11-27
 */
@Service
public class ApiWatcherServiceImpl extends ServiceImpl<ApiWatcherMapper, ApiWatcher> implements ApiWatcherService {

    @Autowired
    @SuppressWarnings("all")
    private ApiWatcherMapper apiWatcherMapper;

    @Override
    public PageInfo getApiInfo(Integer pageNum, String provider, String producer, String result, String startTime, String endTime){
        PageHelper.startPage(pageNum, 10);
        List<ApiWatcher> apiInfo = apiWatcherMapper.getApiInfo(provider, producer, result, startTime, endTime);
        return (new PageInfo(apiInfo));
    }
}
