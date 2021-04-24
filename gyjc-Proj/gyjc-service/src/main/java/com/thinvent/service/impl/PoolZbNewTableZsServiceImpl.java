package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinvent.dto.ZbZsTjTableDto;
import com.thinvent.entity.*;
import com.thinvent.mapper.*;
import com.thinvent.service.CountTJService;
import com.thinvent.service.PoolZbNewTableZsService;
import com.thinvent.service.StatisticZbService;
import com.thinvent.utils.GyjcConstant;
import com.thinvent.utils.TreeUtils;
import com.thinvent.utils.ZbDataMoveUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xff
 * @since 2020-08-26
 */
@Service
public class PoolZbNewTableZsServiceImpl extends ServiceImpl<PoolZbNewTableZsMapper, PoolZbNewTableZs> implements PoolZbNewTableZsService {

    @Autowired
    private CountTJService countTJService;

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableZsMapper poolZbNewTableZsMapper;

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableMapper poolZbNewTableMapper;

    @Autowired
    @SuppressWarnings("all")
    private IppMapper ippMapper;

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    @Autowired
    private ZbDataMoveUtils zbDataMoveUtils;

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Autowired
    private StatisticZbService statisticZbService;

    private static Pattern NUM_PATTERN = Pattern.compile("^\\d{5,6}");

    @Override
    public PoolZbNewTableZs getPoolZbNewTableZs(String id) {
        return poolZbNewTableZsMapper.selectById(id);
    }

    @Override
    public PageInfo getZsTableList(int pageNum, String tableName, String tableType,String createStartTime,String createEndTime)  {
        QueryWrapper<PoolZbNewTableZs> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(tableName)) {
            queryWrapper.like("TABLE_NAME", tableName);
        }
        if (!StringUtils.isEmpty(tableType)) {
            queryWrapper.like("ZSKBM", tableType);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(!StringUtils.isEmpty(createStartTime)){
                queryWrapper.ge("CREATE_TIME", simpleDateFormat.parse(createStartTime));
            }
            if(!StringUtils.isEmpty(createEndTime)) {
                //结束时间+1天，避免小时等问题
                Calendar c = Calendar.getInstance();
                c.setTime(simpleDateFormat.parse(createEndTime));
                c.add(Calendar.DAY_OF_MONTH, 1);
                queryWrapper.lt("CREATE_TIME", c.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //每页8条
        PageHelper.startPage(pageNum, 10);
        queryWrapper.orderByDesc("CREATE_TIME");
        //查询分页数据
        List<PoolZbNewTableZs> poolZbNewTableZsList = poolZbNewTableZsMapper.selectList(queryWrapper);
        for (PoolZbNewTableZs poolZbNewTableZs:poolZbNewTableZsList) {
            //根据字段zbid去获取对应的指标具体信息，封装到实体类中
            String zbListStr = poolZbNewTableZs.getZbid();
            if (StringUtils.isEmpty(zbListStr)) {
                continue;
            }
            //逗号分隔
            List<String> zbList = Arrays.asList(zbListStr.split(","));
            //获取指标
            List<PoolZb> poolZbs = ippMapper.getPoolZbsByIds(zbList);
            //封装
            poolZbNewTableZs.setPoolZbList(poolZbs);
        }
        return new PageInfo(poolZbNewTableZsList);
    }

    @Override
    public void deleteZsTable(String tableName) {
        //验证
        if (!StringUtils.isEmpty(tableName) && tableName.endsWith("_ZS")) {
            //删除表
            poolZbNewTableZsMapper.dropTable(tableName);
            //Modified by xufeng on 2020/10/10 删除展示统计表
            poolZbNewTableZsMapper.dropTable(tableName + GyjcConstant.TJ_TABLE_END);
            //删除表记录
            QueryWrapper<PoolZbNewTableZs> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("TABLE_NAME", tableName);
            poolZbNewTableZsMapper.delete(queryWrapper);
        }

    }

    @Override
    public void deleteZsTabelZb(String tableId, String zbId) {
        PoolZbNewTableZs poolZbNewTableZs = poolZbNewTableZsMapper.selectById(tableId);
        if (null != poolZbNewTableZs) {
            String tzbid = poolZbNewTableZs.getZbid();
            tzbid = tzbid.replace(zbId, "");
            //去除逗号
            tzbid = tzbid.replace(",,", ",");
            if (tzbid.startsWith(",")) {
                //头部
                tzbid = tzbid.substring(1);
            } else if (tzbid.endsWith(",")) {
                //尾部
                tzbid = tzbid.substring(0, tzbid.length() - 1);
            }
            poolZbNewTableZs.setZbid(tzbid);

            //更新字段
            UpdateWrapper<PoolZbNewTableZs> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("ID", poolZbNewTableZs.getId());
            updateWrapper.set("ZBID", tzbid);
            poolZbNewTableZsMapper.update(null, updateWrapper);

            PoolZb poolZb = ippMapper.getPoolZbById(zbId);
            //删除表中关于该指标的记录
            poolZbNewTableZsMapper.deleteZsZbData(poolZbNewTableZs.getTableName(), poolZb.getZbkey());

            //删除展示表数据
            zsTjTableMapper.deleteTjData(poolZbNewTableZs.getTableName() + GyjcConstant.TJ_TABLE_END
                    , poolZb.getZbkey(), null);
        }
    }

    @Override
    public List<PoolZb> getOtherZbList(String tableId, String zbmc, String sourceMark) {
        PoolZbNewTableZs poolZbNewTableZs = poolZbNewTableZsMapper.selectById(tableId);
        String zsZbId = poolZbNewTableZs.getZbid();
        String parentTable = poolZbNewTableZs.getParentTable();
        PoolZbNewTable poolZbNewTable = poolZbNewTableMapper.getNewTableData(parentTable);
        String pzbId = poolZbNewTable.getZbId();
        //子表指标
        List<String> zsZbList;
        if (StringUtils.isEmpty(zsZbId)) {
            zsZbList = new ArrayList<>();
        } else {
            zsZbList = Arrays.asList(zsZbId.split(","));
        }
        if (StringUtils.isEmpty(pzbId)) {
            //父指标为空，返回空集合
            return new ArrayList<>();
        }
        //父表指标
        List<String> pzbList = new ArrayList<>(Arrays.asList(pzbId.split(",")));
        //去除已勾选的指标
        pzbList.removeAll(zsZbList);
        //查询
        List<PoolZb> poolZbList = ippMapper.getPoolZbsByIds(pzbList);
        if (null != poolZbList) {
            if (!StringUtils.isEmpty(zbmc)) {
                //过滤筛选
                poolZbList = poolZbList.stream()
                        .filter(poolZb -> poolZb.getZbmc().contains(zbmc))
                        .collect(Collectors.toList());
            }
            if (!StringUtils.isEmpty(sourceMark)) {
                //过滤筛选
                poolZbList = poolZbList.stream()
                        .filter(poolZb -> poolZb.getSourceMark().equals(sourceMark))
                        .collect(Collectors.toList());
            }
        }
        return poolZbList;
    }

    @Override
    public int addZbToZsTable(String tableId, String zbList) {
        PoolZbNewTableZs poolZbNewTableZs = poolZbNewTableZsMapper.selectById(tableId);
        String zbId = poolZbNewTableZs.getZbid();
        String tableName = poolZbNewTableZs.getTableName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startTime=null;
        String endTime=null;
        if(!StringUtils.isEmpty(poolZbNewTableZs.getStartTime())){
            startTime=simpleDateFormat.format(poolZbNewTableZs.getStartTime());
        }
        if(!StringUtils.isEmpty(poolZbNewTableZs.getEndTime())){
            endTime=simpleDateFormat.format(poolZbNewTableZs.getEndTime());
        }
        if (StringUtils.isEmpty(zbList)) {
            return 0;
        }
        //更新判断
        UpdateWrapper<PoolZbNewTableZs> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID", poolZbNewTableZs.getId());
        if (StringUtils.isEmpty(zbId)) {
            //更新关联关系
            updateWrapper.set("ZBID", zbList);
        } else {
            updateWrapper.set("ZBID", zbId + "," + zbList);
        }
        poolZbNewTableZsMapper.update(null, updateWrapper);
        //转化格式
        List<PoolZb> poolZbList = ippMapper.getPoolZbsByIds(Arrays.asList(zbList.split(",")));
        List<HashMap> mapList = new ArrayList<>();
        for (PoolZb poolZb:poolZbList) {
            HashMap<String, String> hashMap = new HashMap<>(16);
            hashMap.put("zbmc", poolZb.getZbmc());
            hashMap.put("sourceMark", poolZb.getSourceMark());
            hashMap.put("zbkey", poolZb.getZbkey());
            mapList.add(hashMap);
        }
        //插入指标数据
        return zbDataMoveUtils.zbzsTableDataMove(tableName.substring(0, tableName.length()-3) , tableName, mapList,startTime,endTime);
    }

    @Override
    public void updateNewValueOfZsTable(String tableName, String dataId, String newValue) {
        poolZbNewTableZsMapper.updateNewValueOfZsTable(tableName, dataId, newValue);
    }

    @Override
    public void setDisplayDateRange(String tableName, String startDate, String endDate, List<HashMap> zbMapList) {
        startDate = getString(startDate);
        endDate = getString(endDate);
        for (HashMap hashMap:zbMapList) {
            //指标名称
            String zbmc = MapUtils.getString(hashMap, "zbmc");
            //数据来源表
            String dataSource = MapUtils.getString(hashMap, "sourceMark");
            //转换
            dataSource = GyjcConstant.DIC_STATISTIC_ZB.equals(dataSource) ? GyjcConstant.STATISTIC_ZB : dataSource;

            //设置起始时间小的不可见
            poolZbNewTableZsMapper.setUnVisibleZsZbDataRange(tableName, zbmc, dataSource
                    , startDate, null, "0");
            //设置截止时间大的不可见
            poolZbNewTableZsMapper.setUnVisibleZsZbDataRange(tableName, zbmc, dataSource
                    , null, endDate, "0");
            poolZbNewTableZsMapper.updateStateOfZsTableByDateRange(tableName, zbmc, dataSource
                    , startDate, endDate, "1");

        }
    }

    @Override
    @Deprecated
    public int countOneZbData(String tableName, String zbmc, String dataSource, String dimention) {
        if (GyjcConstant.DIC_STATISTIC_ZB.equals(dataSource)) {
            dataSource = GyjcConstant.STATISTIC_ZB;
        }
        //查询是否已存在当前统计维度数据
        int theNum = poolZbNewTableZsMapper.checkCountDataExist(tableName, zbmc, dataSource, dimention);
        if (theNum > 0) {
            //已存在则无需再次统计
            return 0;
        }
        List<HashMap<String, String>> hashMapList;
        //分维度统计
        switch (dimention){
            case "月度":
                hashMapList = poolZbNewTableZsMapper.countValueGroupByMonth(tableName, zbmc, dataSource);
                break;
            case "季度":
                hashMapList = poolZbNewTableZsMapper.countValueGroupByQuarter(tableName, zbmc, dataSource);
                break;
            case "年度":
                hashMapList = poolZbNewTableZsMapper.countValueGroupByYear(tableName, zbmc, dataSource);
                break;
            case "地区":
                hashMapList = poolZbNewTableZsMapper.countValueGroupByArea(tableName, zbmc, dataSource);
                break;
            default:
                return 0;
        }
        for (HashMap map:hashMapList) {
            HashMap hashMap = new HashMap();
            //主键
            hashMap.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
            //指标名称
            hashMap.put("zbmc", zbmc);
            //值统计和
            hashMap.put("value", map.get("SUMVALUE"));
            //数据源
            hashMap.put("dataSource", dataSource);
            //维度
            hashMap.put("statisticMark", dimention);
            //单位名称，统计值统一为“计算值”
            hashMap.put("dwmc", "计算值");
            //显示状态
            hashMap.put("state", "1");
            switch (dimention) {
                case "月度":
                    hashMap.put("startDate", map.get("START_DATE"));
                    hashMap.put("endDate", map.get("END_DATE"));
                    break;
                case "季度":
                    String str = (String) map.get("QUATER");
                    //年
                    String year = str.split("-")[0];
                    //季度
                    String quater = str.split("-")[1];
                    String startDateStr;
                    String endDateStr;
                    if ("1".equals(quater)) {
                        //一季度
                        startDateStr = year + "-01-01";
                        endDateStr = year + "-03-31";
                    } else if ("2".equals(quater)) {
                        //二季度
                        startDateStr = year + "-04-01";
                        endDateStr = year + "-06-30";
                    } else if ("3".equals(quater)) {
                        //三季度
                        startDateStr = year + "-07-01";
                        endDateStr = year + "-09-30";
                    } else {
                        //四季度
                        startDateStr = year + "-10-01";
                        endDateStr = year + "-12-31";
                    }
                    hashMap.put("startDate", startDateStr);
                    hashMap.put("endDate", endDateStr);
                    break;
                case "年度":
                    hashMap.put("startDate", map.get("YEAR") + "-01-01");
                    hashMap.put("endDate", map.get("YEAR") + "-12-31");
                    break;
                case "地区":
                    hashMap.put("dqmc", map.get("DQMC"));
                    break;
                default:
                    break;
            }
            poolZbNewTableZsMapper.insertDataToZsTable(tableName, hashMap);
        }
        return hashMapList.size();
    }

    @Override
    public int countToTjTable(String tableName, String zbmc, String dataSource
            , String zbkey, String dimention, String startDate, String endDate
            , int limit) {
        if (GyjcConstant.DIC_STATISTIC_ZB.equals(dataSource)) {
            dataSource = GyjcConstant.STATISTIC_ZB;
            //国家数据网数据年度值数据过滤
            if (!"原数据".equals(dimention) && !"年度值".equals(dimention)) {
                QueryWrapper<StatisticZb> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ZBKEY", zbkey);
                queryWrapper.isNull("YF");
                //queryWrapper.select("SELECT 1");
                List<StatisticZb> statisticZbs = statisticZbService.list(queryWrapper);
                //不为月度数据则直接返回
                if (statisticZbs != null && statisticZbs.size() > 0) {
                    return 0;
                }
            }
        }
        //统计表名
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        if ("原数据".equals(dimention)) {
            dimention = "";
        }
        //修复bug，去除zbmc里的空格
        zbmc = zbmc.replace(" ", "");
        //判断是否已存在当前统计类型的数据，如存在则直接返回0
        int num = zsTjTableMapper.checkIsExistData(tjTableName, zbkey, dimention);
        if (num > 0) {
            return 0;
        }
        if(dimention.equals("")){
            return zsTjTableMapper.insertOriginData(tableName, tjTableName, zbkey);
        }
        return countTJService.countTJData(tableName,zbmc,dataSource,zbkey,dimention,startDate,endDate,limit);
    }

    @Override
    public int deleteZbTjData(String tableName, String zbkey, String dimention) {
        //统计表名
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        return zsTjTableMapper.deleteTjData(tjTableName, zbkey, dimention);
    }

    @Override
    public PageInfo getZsTjTableData(int pageNum, String zbmc, String sourceTable
            , String tableName, String startDate
            , String endDate, String statisticMark, String dqmc) {
        //分页
        PageHelper.startPage(pageNum, 10);
        //查询数据
        List<ZbZsTjTableDto> zbZsTjTableDtos = zsTjTableMapper
                .getZsTjTableData(tableName, zbmc, sourceTable, dqmc, statisticMark,startDate,endDate);
        return new PageInfo<>(zbZsTjTableDtos);
    }

    private String getString(String theDate) {
        if (StringUtils.isEmpty(theDate) || "null".equals(theDate) || "\"\"".equals(theDate)) {
            theDate = null;
        } else {
            //JSON解析出的时间格式为"xxxx"需要去掉前后的"
            LocalDate end = LocalDate.parse(theDate.substring(1, 11));
            //时区原因，时间较选择的少一天
            end = end.plusDays(1);
            theDate = end.toString();
        }
        return theDate;
    }


    /**
     * 获取字典项数据
     * @return
     */
    @Override
    public List<HashMap<String, String>> getTJTree(String enable){
        List<HashMap<String, String>> TJList=dicMapper.getDicList(GyjcConstant.TJWD_GROUPNO,1,Integer.valueOf(enable));
        return TJList;
    }

    /**
     * 获取地区数据
     * @return
     */
    @Override
    public List<Object> getAreaTree(){
        List<DicItem> jxList=dicMapper.getDicInfo(GyjcConstant.DQ_GROUPNO,"",null,"江西省",1,1);
        DicItem jx=jxList.get(0);
        List<String> parents = new ArrayList<>();
        parents.add(jx.getItemKey());
        List<DicItem> cityList = dicMapper.getDicInfo(GyjcConstant.DQ_GROUPNO, null, parents, null, 2, 1);
        parents.clear();
        for(DicItem d:cityList) {
            parents.add(d.getItemKey());
        }
        List<DicItem> countryList = dicMapper.getDicInfo(GyjcConstant.DQ_GROUPNO, null, parents, null, 3, 1);
        List<DicItem> list=new ArrayList<>();
        list.addAll(jxList);
        list.addAll(cityList);
        list.addAll(countryList);
        List<Object> objects = new TreeUtils().treeMenu(list);
        return objects;
    }

    @Override
    public int countDwNum(String tableName, List<String> zbkeyList) {
        List<HashMap> mapList = zsTjTableMapper.countDwNum(tableName, zbkeyList);
        String tjTableName = tableName + GyjcConstant.TJ_TABLE_END;
        //避免重复生成，每次生成前先删除
        zsTjTableMapper.deleteTjData(tjTableName, "B999999", "重点企业统计");
        //将生成的统计数据插入展示统计表
        for (HashMap theMap:mapList) {
            //id
            theMap.put("ID", UUID.randomUUID().toString().replaceAll("-", ""));
            theMap.put("ZBMC", "重点监测企业");
            theMap.put("VALUE", theMap.get("SUMVALUE"));
            theMap.put("JLDW", "个");
            theMap.put("DATA_SOURCE", "YW_SYSTEM_DATA_MOVE");
            theMap.put("LYZS_TABLE", tableName);
            //theMap.put("NF", theMap.get("NF"));
            theMap.put("YFORJD", theMap.get("YF"));
            //临时key，指重点监测企业指标
            theMap.put("ZBKEY", "B999999");
            //theMap.put("TIME_ID", theMap.get("TIME_ID"));
            //theMap.put("DQMC", theMap.get("DQMC"));
            //theMap.put("NEW_VALUE", ---);
            theMap.put("STATE", "1");
            theMap.put("STATISTIC_MARK", "重点企业统计");
            zsTjTableMapper.insertTjData(tjTableName, theMap);
        }

        //生成全省的企业个数统计
        List<HashMap> theList = zsTjTableMapper.sumAreaPerMonthCount(tjTableName, "B999999");
        //将生成的统计数据插入展示统计表
        for (HashMap theMap:theList) {
            //id
            theMap.put("ID", UUID.randomUUID().toString().replaceAll("-", ""));
            theMap.put("ZBMC", "重点监测企业");
            theMap.put("VALUE", theMap.get("SUMVALUE"));
            theMap.put("DATA_SOURCE", "YW_SYSTEM_DATA_MOVE");
            theMap.put("LYZS_TABLE", tableName);
            theMap.put("YFORJD", theMap.get("YF"));
            //临时key，指重点监测企业指标
            theMap.put("ZBKEY", "B999999");
            theMap.put("STATE", "1");
            theMap.put("STATISTIC_MARK", "重点企业统计");
            zsTjTableMapper.insertTjData(tjTableName, theMap);
        }

        return theList.size() + mapList.size();
    }
}
