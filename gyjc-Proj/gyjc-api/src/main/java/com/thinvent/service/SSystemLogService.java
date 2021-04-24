package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.SSystemLog;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yb
 * @since 2020-11-12
 */
public interface SSystemLogService extends IService<SSystemLog> {

    /**
     * 保存日志
     * @param ip
     * @param userId
     * @param userName
     * @param log
     * @param isException
     */
    void saveLog(String ip, String userId, String userName, String log, Integer isException);

    /**
     *  获取日志信息
     * @param pageNum
     * @return
     */
    PageInfo getLogInfo(Integer pageNum, String loginName, String userName, String startTime, String endTime);

}
