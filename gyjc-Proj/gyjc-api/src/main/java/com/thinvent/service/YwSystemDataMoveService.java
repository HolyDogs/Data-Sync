package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.YwSystemDataMove;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 各业务系统数据迁移 服务类
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
public interface YwSystemDataMoveService extends IService<YwSystemDataMove> {

    /**
     * 分页查询
     * @param pageNum 页数
     * @param ywSystemDataMove 用来筛选的条件
     * @return 分页数据
     */
    PageInfo getListPage(int pageNum, YwSystemDataMove ywSystemDataMove);

    /**
     * 查询分类下拉集合
     * @return 下拉框封装数据
     */
    List<HashMap<String, String>> selectList();

    /**
     * 查询源数据统计值
     * @param dataType 数据来源类型（表名）
     * @return 统计值
     */
    HashMap<String, String> getDataCount(String dataType);
}
