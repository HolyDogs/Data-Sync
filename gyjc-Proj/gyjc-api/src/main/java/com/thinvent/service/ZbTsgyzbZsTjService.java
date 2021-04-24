package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.ZbTsgyzbZsTj;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2020-12-17
 */
public interface ZbTsgyzbZsTjService extends IService<ZbTsgyzbZsTj> {
    /**
     * 获取分页数据
     * @param pageNum 页数
     * @param dataType 数据类型
     * @param theName 名称
     * @param dqmc 地区名称
     * @return 分页信息
     */
    PageInfo getList(int pageNum, String dataType, String theName, String dqmc);
}
