package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.entity.YwSystemDataMove;
import com.thinvent.mapper.YwSystemDataMoveMapper;
import com.thinvent.service.YwSystemDataMoveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 各业务系统数据迁移 服务实现类
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
@Service
@Slf4j
public class YwSystemDataMoveServiceImpl extends ServiceImpl<YwSystemDataMoveMapper, YwSystemDataMove> implements YwSystemDataMoveService {

    @Autowired
    @SuppressWarnings("all")
    private YwSystemDataMoveMapper ywSystemDataMoveMapper;

    @Override
    public PageInfo getListPage(int pageNum, YwSystemDataMove ywSystemDataMove) {
        QueryWrapper<YwSystemDataMove> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(ywSystemDataMove.getZbmc())) {
            //指标名称 like
            queryWrapper.like("ZBMC", ywSystemDataMove.getZbmc());
        }
        if (!StringUtils.isEmpty(ywSystemDataMove.getLyid())) {
            //指标分类匹配
            queryWrapper.eq("LYID", ywSystemDataMove.getLyid());
        }
        if (!StringUtils.isEmpty(ywSystemDataMove.getZbmcfz())) {
            //指标名称附注
            queryWrapper.like("ZBMCFZ", ywSystemDataMove.getZbmcfz());
        }
        //地区名称
        if(!StringUtils.isEmpty(ywSystemDataMove.getDqmc())){
            queryWrapper.like("DQMC",ywSystemDataMove.getDqmc());
        }
        //单位名称
        if(!StringUtils.isEmpty(ywSystemDataMove.getDwmc())){
            queryWrapper.like("DWMC",ywSystemDataMove.getDwmc());
        }
        //起始时间
        if(!StringUtils.isEmpty(ywSystemDataMove.getQssj())){
            String startString="M-"+ywSystemDataMove.getQssj().substring(0,4)+""+ywSystemDataMove.getQssj().substring(5,7);
            queryWrapper.ge("TIMEID",startString);
        }
        //截止时间
        if(!StringUtils.isEmpty(ywSystemDataMove.getJzsj())){
            String endString="M-"+ywSystemDataMove.getJzsj().substring(0,4)+""+ywSystemDataMove.getJzsj().substring(5,7);
            queryWrapper.le("TIMEID",endString);
        }
        //查主键，防止全表扫描查询过慢
        queryWrapper.select("SJQY_ID");
        PageHelper.startPage(pageNum, 10);
        //主键分页
        List<Object> ywSystemDataMoveIdList = ywSystemDataMoveMapper.selectObjs(queryWrapper);
        queryWrapper = new QueryWrapper<>();
        //获取分页信息
        PageInfo pageInfo = new PageInfo(ywSystemDataMoveIdList);
        if (null != ywSystemDataMoveIdList && ywSystemDataMoveIdList.size() > 0) {
            //设置ID IN (list)
            queryWrapper.in("SJQY_ID", ywSystemDataMoveIdList);
            //根据分页的ID再去查询整条数据
            List<YwSystemDataMove> ywSystemDataMoveList = ywSystemDataMoveMapper.selectList(queryWrapper);

            //设置分页数据
            pageInfo.setList(ywSystemDataMoveList);
        }

        return pageInfo;
    }

    @Override
    public List<HashMap<String, String>> selectList() {
        return ywSystemDataMoveMapper.getSelectList();
    }

    @Override
    public HashMap<String, String> getDataCount(String dataType) {
        int count = 0;
        try {
            count = ywSystemDataMoveMapper.getDataCount(dataType);
        } catch (Exception e) {
            //e.printStackTrace();
            log.info("默认数据源查询失败，数据源切换为safe查询");
            count = ywSystemDataMoveMapper.getSafeDataCount(dataType);
        }
        Integer lastMonthCount = ywSystemDataMoveMapper.getLastMonthDataCount(dataType);
        if (lastMonthCount == null) {
            //避免项目未启动导致上月数据统计为0
            lastMonthCount = 0;
        }
        HashMap hashMap = new HashMap(16);
        //当前总量
        hashMap.put("total", count);
        //当前总量-上月总量=增加量
        hashMap.put("addNum", count-lastMonthCount);
        return hashMap;
    }
}
