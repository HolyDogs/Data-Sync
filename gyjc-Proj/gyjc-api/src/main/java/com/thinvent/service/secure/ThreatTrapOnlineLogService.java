package com.thinvent.service.secure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.secure.ThreatTrapOnlineLog;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yb
 * @since 2021-01-26
 */
public interface ThreatTrapOnlineLogService extends IService<ThreatTrapOnlineLog> {

    public PageInfo getThreatTrapList(Integer pageNum, HashMap<String, String> map);
}
