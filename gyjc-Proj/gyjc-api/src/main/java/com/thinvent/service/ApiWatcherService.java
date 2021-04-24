package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.ApiWatcher;

/**
 * <p>
 *  服务类 需要删除
 * </p>
 *
 * @author yb
 * @since 2020-11-27
 */
public interface ApiWatcherService extends IService<ApiWatcher> {

    PageInfo getApiInfo(Integer pageNum, String provider, String producer, String result, String startTime, String endTime);

}
