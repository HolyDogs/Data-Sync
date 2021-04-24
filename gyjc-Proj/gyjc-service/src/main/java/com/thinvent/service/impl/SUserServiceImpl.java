package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.SToken;
import com.thinvent.entity.SUser;
import com.thinvent.mapper.STokenMapper;
import com.thinvent.mapper.SUserMapper;
import com.thinvent.service.SUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xff
 * @since 2020-11-11
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserMapper, SUser> implements SUserService {

    @Autowired
    @SuppressWarnings("all")
    private SUserMapper sUserMapper;

    @Autowired
    @SuppressWarnings("all")
    private STokenMapper sTokenMapper;

    @Override
    public SUser getUserByLoginName(String loginName,Integer isDelete) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("LOGIN_NAME",loginName);
        if(isDelete.intValue()!=2){
            queryWrapper.eq("IS_DELETE",isDelete);
        }
        SUser sUser = sUserMapper.selectOne(queryWrapper);
        return sUser;
    }

    @Override
    public PageInfo getUserInfo(Integer pageNum, String name,String loginName,String phone) {
        PageHelper.startPage(pageNum, 10);
        List<SUser> users = sUserMapper.getUserInfo(name,loginName,phone);
        for (SUser user:users){
            user.setEnablePassword("");
            user.setPassword("");
        }
        return (new PageInfo(users));
    }

    @Override
    public void deleteUser(String userId, SToken sToken){
        UpdateWrapper<SUser> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("UPDATE_USER_ID",sToken.getLoginUserId());
        updateWrapper.set("UPDATE_USER_NAME",sToken.getLoginUserName());
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.set("IS_DELETE",1);
        updateWrapper.eq("ID",userId);
        sUserMapper.update(null,updateWrapper);
        QueryWrapper<SToken> stokenWrapper = new QueryWrapper<>();
        stokenWrapper.eq("LOGIN_USER_ID",userId);
        sTokenMapper.delete(stokenWrapper);
    }

    @Override
    public void undeleteUser(String userId, SToken sToken){
        UpdateWrapper<SUser> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("UPDATE_USER_ID",sToken.getLoginUserId());
        updateWrapper.set("UPDATE_USER_NAME",sToken.getLoginUserName());
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.set("IS_DELETE",0);
        updateWrapper.eq("ID",userId);
        sUserMapper.update(null,updateWrapper);
    }

    @Override
    public void updateUser(String userId, String userName, Integer userSex, String userPhone, String userEmail,SToken sToken) {
        UpdateWrapper<SUser> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("FULL_NAME",userName);
        updateWrapper.set("SEX",userSex);
        updateWrapper.set("PHONE",userPhone);
        updateWrapper.set("EMAIL",userEmail);
        updateWrapper.set("UPDATE_USER_ID",sToken.getLoginUserId());
        updateWrapper.set("UPDATE_USER_NAME",sToken.getLoginUserName());
        updateWrapper.set("UPDATE_TIME",new Date());
        updateWrapper.eq("ID",userId);
        sUserMapper.update(null,updateWrapper);
    }
}
