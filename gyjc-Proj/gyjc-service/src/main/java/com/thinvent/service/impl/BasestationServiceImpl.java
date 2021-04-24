package com.thinvent.service.impl;


import com.thinvent.entity.BasestationCoordination;
import com.thinvent.entity.BasestationUseSchedule;
import com.thinvent.entity.SatelliteEarthStation;
import com.thinvent.mapper.BasestationMapper;
import com.thinvent.service.BasestationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yb
 * @since 2020-11-12
 */
@Service
public class BasestationServiceImpl implements BasestationService {

    @Autowired
    @SuppressWarnings("all")
    private BasestationMapper basestationMapper;

    @Override
    public PageInfo<SatelliteEarthStation> getEarthStations(int pageNum,String bhqdsbh,String yhdwmc,String lxr,String startDate,String endDate) {
        PageHelper.startPage(pageNum, 10);
        List<SatelliteEarthStation> SatelliteEarthStations = basestationMapper.getEarthStations(bhqdsbh,yhdwmc,lxr,startDate,endDate);
        return (new PageInfo(SatelliteEarthStations));
    }

    @Override
    public PageInfo<BasestationUseSchedule> getUseSchedules(int pageNum,String jzsyh,String tzszsyr,String lxr,String jzlx,String jzzt,String startTime,String endTime) {
        PageHelper.startPage(pageNum, 10);
        List<BasestationUseSchedule> SatelliteEarthStations = basestationMapper.getUseSchedules(jzsyh,tzszsyr,lxr,jzlx,jzzt,startTime,endTime);
        return (new PageInfo(SatelliteEarthStations));
    }

    @Override
    public PageInfo<BasestationCoordination> getCoordinations(int pageNum,String bhqdsbh,String yhdwmc,String sfxyxt,String sfyfqxtqq,String sfyqrxtqq,String sfywcxt) {
        PageHelper.startPage(pageNum, 10);
        List<BasestationCoordination> SatelliteEarthStations = basestationMapper.getCoordinations(bhqdsbh,yhdwmc,sfxyxt,sfyfqxtqq,sfyqrxtqq,sfywcxt);
        return (new PageInfo(SatelliteEarthStations));
    }
}
