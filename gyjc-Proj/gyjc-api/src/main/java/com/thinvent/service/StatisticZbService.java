package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.StatisticZb;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
public interface StatisticZbService extends IService<StatisticZb> {

    /**
     * 查询分页数据
     * @param pageNum 分页页数
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 分页数据
     */
    PageInfo getDataList(int pageNum, String zbmc, String zbfl, String startTime, String endTime);

    /**
     * 查询不同的指标分类
     * @return 下拉指标分类列表
     */
    List<String> getSelectList();
}
