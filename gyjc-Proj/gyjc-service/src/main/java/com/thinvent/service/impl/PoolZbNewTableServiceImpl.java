package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.dto.ZbTableDto;
import com.thinvent.entity.*;
import com.thinvent.mapper.IppMapper;
import com.thinvent.mapper.PoolZbNewTableMapper;
import com.thinvent.mapper.PoolZbNewTableZbInfoMapper;
import com.thinvent.mapper.PoolZbNewTableZsMapper;
import com.thinvent.service.PoolZbNewTableService;
import com.thinvent.utils.GyjcConstant;
import com.thinvent.utils.ZbDataMoveUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/8/13 10:58
 **/
@Service
@Slf4j
public class PoolZbNewTableServiceImpl implements PoolZbNewTableService {

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableMapper poolZbNewTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private IppMapper ippMapper;

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableZsMapper poolZbNewTableZsMapper;

    @Autowired
    private ZbDataMoveUtils zbDataMoveUtils;

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableZbInfoMapper poolZbNewTableZbInfoMapper;

    @Override
    public PoolZbNewTable getPoolZbNewTable(String tableId) {
        return poolZbNewTableMapper.getPoolZbNewTableById(tableId);
    }

    @Override
    public PageInfo getNewTableList(String tableName, int pageNum, String tableType,String createStartTime,String createEndTime) {
        PageHelper.startPage(pageNum, 10);
        //查询记录
        List<PoolZbNewTable> poolZbNewTables = poolZbNewTableMapper.selectNewTableData(tableName, tableType,createStartTime,createEndTime);
        for (PoolZbNewTable poolZbNewTable:poolZbNewTables) {
            //根据字段zbid去获取对应的指标具体信息，封装到实体类中
            String zbListStr = poolZbNewTable.getZbId();
            if (StringUtils.isEmpty(zbListStr)) {
                continue;
            }
            //逗号分隔
            List<String> zbList = Arrays.asList(zbListStr.split(","));
            //获取指标
            List<PoolZb> poolZbs = ippMapper.getPoolZbsByIds(zbList);
            //封装
            poolZbNewTable.setPoolZbList(poolZbs);
        }
        return new PageInfo(poolZbNewTables);
    }

    @Override
    public int deleteTable(String tableName) {
        if (!StringUtils.isEmpty(tableName)) {
            //保险起见，验证一下表名
            if (tableName.startsWith(GyjcConstant.ZB_TABLE_PREFIX)) {
                try{
                    QueryWrapper<PoolZbNewTableZs> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("PARENT_TABLE", tableName);
                    List list = poolZbNewTableZsMapper.selectList(queryWrapper);
                    if (list != null && list.size() > 0) {
                        return 1;
                    }
                    //删除表
                    poolZbNewTableMapper.dropTable(tableName);
                    //删除指标信息表记录 modified on 09/27  按插入顺序进行删除
                    QueryWrapper<PoolZbNewTableZbInfo> poolZbNewTableZbInfoQueryWrapper = new QueryWrapper<>();
                    poolZbNewTableZbInfoQueryWrapper.eq("TABLE_NAME", tableName);
                    poolZbNewTableZbInfoMapper.delete(poolZbNewTableZbInfoQueryWrapper);

                    //删除指标新建表中的记录
                    poolZbNewTableMapper.deleteTableByTableName(tableName);

                }catch (Exception e) {
                    log.info(tableName + "不存在对应的主题表----");
                    e.printStackTrace();

                }
            }
        }
        return 0;
    }

    /**
     * A --> A
     * B --> A
     * C --> A
     * B --> B
     * A --> B
     * 删除C指标，会删除前面三条，但A、B指标仍留在该主题表
     **/
    @Override
    public void deleteTabelZb(String tableId, String zbId) {
        QueryWrapper<PoolZbNewTableZbInfo> queryWrapper = new QueryWrapper<>();
        //表id相等
        queryWrapper.eq("TABLE_ID", tableId);
        //去重的指标ID
        queryWrapper.select("DISTINCT ZBID");
        //原指标ID集合
        List<Object> zbIdList = poolZbNewTableZbInfoMapper.selectObjs(queryWrapper);

        QueryWrapper<PoolZbNewTableZbInfo> bindIdQueryWrapper = new QueryWrapper<>();
        //表ID
        bindIdQueryWrapper.eq("TABLE_ID", tableId);
        //指标ID
        bindIdQueryWrapper.eq("ZBID", zbId);
        //去重的绑定ID
        bindIdQueryWrapper.select("DISTINCT BIND_ID");
        List<Object> bindIdList = poolZbNewTableZbInfoMapper.selectObjs(bindIdQueryWrapper);

        QueryWrapper<PoolZbNewTableZbInfo> deleteQueryWrapper = new QueryWrapper<>();
        deleteQueryWrapper.in("BIND_ID", bindIdList);
        deleteQueryWrapper.eq("TABLE_ID", tableId);
        //删除指标以及连带的指标
        poolZbNewTableZbInfoMapper.delete(deleteQueryWrapper);
        //现指标ID集合
        List<Object> nowZbIdList = poolZbNewTableZbInfoMapper.selectObjs(queryWrapper);

        PoolZbNewTable poolZbNewTable = poolZbNewTableMapper.getPoolZbNewTableById(tableId);
        if (null != poolZbNewTable) {
            //设置变更字段
            poolZbNewTable.setZbId(org.apache.commons.lang.StringUtils.join(nowZbIdList, ","));
            //更新
            poolZbNewTableMapper.updatePoolZbNewTableById(poolZbNewTable);
            //获取删除的指标
            List<String> deleteZbId = zbIdList.stream()
                    .filter(zb -> !nowZbIdList.contains(zb))
                    .map(Object::toString)
                    .collect(Collectors.toList());
            for (String deleteId:deleteZbId) {
                PoolZb poolZb = ippMapper.getPoolZbById(deleteId);

                //删除表数据
                poolZbNewTableMapper.deleteTableData(poolZbNewTable.getTableName(), poolZb.getZbkey());
            }
        }
    }

    @Override
    public int zbDataAddToTable(String tableId, String zbList) {
        PoolZbNewTable poolZbNewTable = poolZbNewTableMapper.getPoolZbNewTableById(tableId);
        String zbId = poolZbNewTable.getZbId();
        if (StringUtils.isEmpty(zbList)) {
            return 0;
        }
        List<String> oldzbIdList = null;
        if (StringUtils.isEmpty(zbId)) {
            oldzbIdList = new ArrayList<>();
        } else {
            oldzbIdList = Arrays.asList(zbId.split(","));
        }
        StringBuilder newZbStr = new StringBuilder();
        List<String> newZbIdList = Arrays.asList(zbList.split(","));
        PoolZbNewTableZbInfo poolZbNewTableZbInfo = new PoolZbNewTableZbInfo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startTime=null;
        String endTime=null;
        if(!StringUtils.isEmpty(poolZbNewTable.getStartTime())){
            startTime=simpleDateFormat.format(poolZbNewTable.getStartTime());
        }
        if(!StringUtils.isEmpty(poolZbNewTable.getEndTime())){
            endTime=simpleDateFormat.format(poolZbNewTable.getEndTime());
        }
        for (String newId : newZbIdList) {
            if (!oldzbIdList.contains(newId)) {
                //用于插入指标数据
                newZbStr.append(newId).append(",");
            }
            poolZbNewTableZbInfo.setId(UUID.randomUUID().toString().replace("-", ""));
            poolZbNewTableZbInfo.setTableId(tableId);
            poolZbNewTableZbInfo.setTableName(poolZbNewTable.getTableName());
            //指标ID
            poolZbNewTableZbInfo.setZbid(newId);
            //绑定指标
            poolZbNewTableZbInfo.setBindId(newId);
            //保存
            poolZbNewTableZbInfoMapper.insert(poolZbNewTableZbInfo);
            //获取指标池对象
            PoolZb poolZb = ippMapper.getPoolZbById(newId);
            //获取绑定指标
            String bindZb = poolZb.getBindZb();
            if (!StringUtils.isEmpty(bindZb)) {
                List<String> bindList = Arrays.asList(bindZb.split(","));
                for (String bindId : bindList) {
                    if (!oldzbIdList.contains(bindId)) {
                        //加入新指标
                        newZbStr.append(bindId).append(",");
                    }
                    //新ID
                    poolZbNewTableZbInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                    poolZbNewTableZbInfo.setZbid(bindId);
                    //保存指标绑定关系
                    poolZbNewTableZbInfoMapper.insert(poolZbNewTableZbInfo);
                }
            }
        }
        if (newZbStr.length() > 0) {
            String addZbStr = newZbStr.substring(0, newZbStr.length() - 1);
            if (StringUtils.isEmpty(zbId)) {
                poolZbNewTable.setZbId(addZbStr);
            } else {
                poolZbNewTable.setZbId(zbId + "," + addZbStr);
            }
            //更新表关联指标id
            poolZbNewTableMapper.updatePoolZbNewTableById(poolZbNewTable);
            //插入指标数据
            zbDataMoveUtils.findZbAndInsertToTable(poolZbNewTable.getTableName(), addZbStr,startTime,endTime);
            return 1;
        } else {
            return -1;
        }

    }

    @Override
    public PageInfo getZbTableData(int pageNum, String zbmc
            , String sourceTable, String tableName, String startDate, String endDate, String dwmc, String dqmc) {
        PageHelper.startPage(pageNum, 10);
        List<ZbTableDto> zbTableDtoList = poolZbNewTableMapper
                .selectZbTableData(tableName, zbmc, sourceTable, startDate, endDate, dwmc, dqmc);
        return new PageInfo(zbTableDtoList);
    }

    @Override
    public int displayTableCreate(String tableName, String newTableName, List<HashMap> mapList,String startTime,String endTime) {
        try {
            //创建新表
            ippMapper.createZbZsTable(newTableName);
            //更新到记录表POOL_ZB_NEW_TABLE_ZS
            PoolZbNewTableZs poolZbNewTableZs = new PoolZbNewTableZs();
            poolZbNewTableZs.setId(UUID.randomUUID().toString().replace("-", ""));
            poolZbNewTableZs.setCreateTime(new Date());
            poolZbNewTableZs.setTableName(newTableName);
            poolZbNewTableZs.setParentTable(tableName);
            //获取原表数据
            PoolZbNewTable poolZbNewTable = poolZbNewTableMapper.getNewTableData(tableName);
            //转为指标id集合
            List<String> zbList = mapList.stream().map(hashMap -> MapUtils.getString(hashMap, "id")).collect(Collectors.toList());
            //将集合转化为逗号分隔的字符串
            String zbIdStr = org.apache.commons.lang.StringUtils.join(zbList, ",");
            //设置指标id集
            poolZbNewTableZs.setZbid(zbIdStr);
            //设置备注
            poolZbNewTableZs.setTableComments(poolZbNewTable.getTableComments() + "(展示表)");
            //设置展示表类型、类型名称
            poolZbNewTableZs.setZskbm(poolZbNewTable.getZtkbm());
            poolZbNewTableZs.setZskmc(poolZbNewTable.getZtkmc());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if(!StringUtils.isEmpty(startTime)) {
                    //起始时间
                    poolZbNewTableZs.setStartTime(simpleDateFormat.parse(startTime));
                }
                if(!StringUtils.isEmpty(endTime)) {
                    //截止时间
                    poolZbNewTableZs.setEndTime(simpleDateFormat.parse(endTime));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //插入新数据
            poolZbNewTableZsMapper.insert(poolZbNewTableZs);
            //创建统计展示表
            poolZbNewTableZsMapper.createZsTjTable(newTableName + GyjcConstant.TJ_TABLE_END);
            //插入指标数据
            return zbDataMoveUtils.zbzsTableDataMove(tableName, newTableName, mapList,startTime,endTime);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void updateTableData(String tableName) {
        List<HashMap<String, String>> mapList = poolZbNewTableMapper.getLatestDateOfZb(tableName);
        if (mapList != null && mapList.size() > 0) {
            for (HashMap hashMap:mapList) {
                //更新最新的数据
                zbDataMoveUtils.findZbAndInsertToTable(tableName, MapUtils.getString(hashMap, "ID")
                        , MapUtils.getString(hashMap, "LASTDATE"), null);
            }
        }
    }
}
