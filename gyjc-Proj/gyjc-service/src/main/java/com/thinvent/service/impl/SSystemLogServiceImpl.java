package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.SSystemLog;
import com.thinvent.entity.SUser;
import com.thinvent.mapper.SSystemLogMapper;
import com.thinvent.mapper.SUserMapper;
import com.thinvent.service.SSystemLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yb
 * @since 2020-11-12
 */
@Service
public class SSystemLogServiceImpl extends ServiceImpl<SSystemLogMapper, SSystemLog> implements SSystemLogService {

    @Autowired
    @SuppressWarnings("all")
    private SSystemLogMapper systemLogMapper;

    @Autowired
    @SuppressWarnings("all")
    private SUserMapper sUserMapper;

    @Override
    public void saveLog(String ip,String userId,String userName,String log,Integer isException) {
        QueryWrapper<SUser> userQuery=new QueryWrapper<>();
        if(!StringUtils.isEmpty(userId)){
            userQuery.eq("ID",userId);
        }
        if(!StringUtils.isEmpty(userName)){
            userQuery.eq("LOGIN_NAME",userName);
        }
        SUser user = sUserMapper.selectOne(userQuery);
        SSystemLog systemLog=new SSystemLog();
        systemLog.setId(UUID.randomUUID().toString().replace("-", ""));
        systemLog.setClientIp(ip);
        systemLog.setUserId(StringUtils.isEmpty(userId)?user==null?null:user.getId():userId);
        systemLog.setUserName(StringUtils.isEmpty(userName)?user==null?null:user.getLoginName():userName);
        systemLog.setFullName(user==null?null:user.getFullName());
        systemLog.setLog(log);
        systemLog.setLogTime(new Date());
        systemLog.setIsDelete(0);
        systemLog.setIsException(isException);
        systemLogMapper.insert(systemLog);

    }

    @Override
    public PageInfo getLogInfo(Integer pageNum,String loginName,String userName,String startTime,String endTime) {

        PageHelper.startPage(pageNum, 10);
        List<SSystemLog> logs = systemLogMapper.getLogInfo(loginName,userName,startTime,endTime);
        return (new PageInfo(logs));
    }
}
