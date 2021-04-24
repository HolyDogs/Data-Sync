package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.ZbTsgyzbZsTj;
import com.thinvent.mapper.ZbTsgyzbZsTjMapper;
import com.thinvent.service.ZbTsgyzbZsTjService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xff
 * @since 2020-12-17
 */
@Service
public class ZbTsgyzbZsTjServiceImpl extends ServiceImpl<ZbTsgyzbZsTjMapper, ZbTsgyzbZsTj> implements ZbTsgyzbZsTjService {

    @Autowired
    @SuppressWarnings("all")
    private ZbTsgyzbZsTjMapper zbTsgyzbZsTjMapper;

    @Override
    public PageInfo getList(int pageNum, String dataType, String theName, String dqmc) {
        QueryWrapper<ZbTsgyzbZsTj> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dataType)) {
            //数据类型
            queryWrapper.eq("DATA_TYPE", dataType);
        }
        if (!StringUtils.isEmpty(theName)) {
            //指标名称
            queryWrapper.like("NAME", theName);
        }
        if (!StringUtils.isEmpty(dqmc)) {
            //地区名称
            queryWrapper.like("DQMC", dqmc);
        }
        //按类型升序、导入时间倒序排序
        queryWrapper.orderByAsc("DATA_TYPE").orderByDesc("CREATE_TIME");
        //分页
        PageHelper.startPage(pageNum, 10);
        List<ZbTsgyzbZsTj> zbTsgyzbZsTjList = zbTsgyzbZsTjMapper.selectList(queryWrapper);
        return new PageInfo<>(zbTsgyzbZsTjList);
    }
}
