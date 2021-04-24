package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.SToken;
import com.thinvent.mapper.STokenMapper;
import com.thinvent.service.STokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xff
 * @since 2020-11-12
 */
@Service
public class STokenServiceImpl extends ServiceImpl<STokenMapper, SToken> implements STokenService {

    @Autowired
    @SuppressWarnings("all")
    private STokenMapper sTokenMapper;

    @Override
    public SToken getUserInfo(String id) {
        QueryWrapper<SToken> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("TOKEN_NO",id);
        List<SToken> sTokens = sTokenMapper.selectList(queryWrapper);
        SToken sToken = null;
        if(sTokens!=null && sTokens.size()>0){
            sToken=sTokens.get(0);
        }
        return sToken;
    }
}
