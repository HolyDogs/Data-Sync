package com.thinvent.service;

import com.thinvent.entity.BasestationCoordination;
import com.thinvent.entity.BasestationUseSchedule;
import com.thinvent.entity.SatelliteEarthStation;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yb
 * @since 2020-11-12
 */
public interface BasestationService {

    /**
     * 返回卫星地球站数据
     * @param pageNum 页数
     * @param bhqdsbh 保护清单识别号
     * @param yhdwmc 用户单位名称
     * @param lxr 联系人
     * @param startDate 确认起始时间
     * @param endDate 确认截止时间
     * @return 分页数据
     */
    PageInfo<SatelliteEarthStation> getEarthStations(int pageNum, String bhqdsbh, String yhdwmc, String lxr, String startDate, String endDate);

    /**
     * 返回5G基站使用进度
     * @param pageNum 页数
     * @param jzsyh 5G基站索引号
     * @param tzszsyr 台站设置使用人
     * @param lxr 联系人
     * @param jzlx 基站类型
     * @param jzzt 基站状态
     * @param startTime 建站起始时间
     * @param endTime 建站截止时间
     * @return 分页数据
     */
    PageInfo<BasestationUseSchedule> getUseSchedules(int pageNum, String jzsyh, String tzszsyr, String lxr, String jzlx, String jzzt, String startTime, String endTime);

    /**
     * 返回5G基站协调进度数据
     * @param pageNum 页数
     * @param bhqdsbh 保护清单识别号
     * @param yhdwmc 用户单位名称
     * @param sfxyxt 是否需要协调
     * @param sfyfqxtqq 是否已发起协调请求
     * @param sfyqrxtqq 是否已确认协调请求
     * @param sfywcxt 是否已完成协调
     * @return 分页数据
     */
    PageInfo<BasestationCoordination> getCoordinations(int pageNum, String bhqdsbh, String yhdwmc, String sfxyxt, String sfyfqxtqq, String sfyqrxtqq, String sfywcxt);

}
