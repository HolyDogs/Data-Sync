package com.thinvent.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.secure.ThreatTrapOnlineLog;
import com.thinvent.mapper.secure.ThreatTrapOnlineLogMapper;
import com.thinvent.service.secure.ThreatTrapOnlineLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yb
 * @since 2021-01-26
 */
@Service
public class ThreatTrapOnlineLogServiceImpl extends ServiceImpl<ThreatTrapOnlineLogMapper, ThreatTrapOnlineLog> implements ThreatTrapOnlineLogService {

    @Autowired
    @SuppressWarnings("all")
    private ThreatTrapOnlineLogMapper threatTrapOnlineLogMapper;

    @Override
    public PageInfo getThreatTrapList(Integer pageNum, HashMap<String, String> map) {

        QueryWrapper<ThreatTrapOnlineLog> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(map.get("logType"))){
            wrapper.eq("LOG_TYPE",Integer.valueOf(map.get("logType")));
        }
        if(!StringUtils.isEmpty(map.get("logLevel"))){
            wrapper.eq("LEVEL_ID",Integer.valueOf(map.get("logLevel")));
        }
        if(!StringUtils.isEmpty(map.get("protocol"))){
            wrapper.like("PROTOCOL",map.get("protocol"));
        }
        if(!StringUtils.isEmpty(map.get("country"))){
            wrapper.like("SRC_COUNTRY",map.get("country"));
        }
        wrapper.select("ID");
        wrapper.orderByDesc("CREATE_TIME");
        PageHelper.startPage(pageNum, 10);
        List<Object> idList = threatTrapOnlineLogMapper.selectObjs(wrapper);
        //分页数据id
        PageInfo pageInfo = new PageInfo(idList);
        List<ThreatTrapOnlineLog> threatTrapOnlineLogs = null;
        if (idList.size() > 0) {
            //id集合不为空
            wrapper = new QueryWrapper<>();
            wrapper.in("ID", idList);
            threatTrapOnlineLogs = threatTrapOnlineLogMapper.selectList(wrapper);
        }
        //设置分页list
        pageInfo.setList(threatTrapOnlineLogs);
        return (pageInfo);
    }

}
