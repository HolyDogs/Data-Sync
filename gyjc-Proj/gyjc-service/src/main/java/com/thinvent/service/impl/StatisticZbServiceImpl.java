package com.thinvent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.StatisticZb;
import com.thinvent.mapper.StatisticZbMapper;
import com.thinvent.service.StatisticZbService;
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
 * @author xff
 * @since 2020-08-18
 */
@Service
public class StatisticZbServiceImpl extends ServiceImpl<StatisticZbMapper, StatisticZb> implements StatisticZbService {

    @Autowired
    @SuppressWarnings("all")
    private StatisticZbMapper statisticZbMapper;

    @Override
    public PageInfo getDataList(int pageNum, String zbmc, String zbfl,String startTime,String endTime) {
        //分页
        PageHelper.startPage(pageNum, 10);
        //条件查询
        List<StatisticZb> statisticZbList = statisticZbMapper.getStatisticList(zbmc,zbfl,startTime,endTime);
        return new PageInfo(statisticZbList);
    }

    @Override
    public List<String> getSelectList() {
        return statisticZbMapper.getZbflSelectList();
    }
}
