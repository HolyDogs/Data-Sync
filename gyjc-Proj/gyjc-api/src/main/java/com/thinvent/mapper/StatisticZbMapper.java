package com.thinvent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.StatisticZb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
@Mapper
public interface StatisticZbMapper extends BaseMapper<StatisticZb> {

    /**
     * 获取指标分类集合，不重复
     * @return 指标分类集合
     */
    List<String> getZbflSelectList();

    /**
     * 获取国家数据网原数据
     * @param zbmc  指标名称
     * @param zbfl  指标分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return
     */
    List<StatisticZb> getStatisticList(@Param("zbmc") String zbmc,
                                       @Param("zbfl") String zbfl,
                                       @Param("startTime") String startTime,
                                       @Param("endTime") String endTime);
}
