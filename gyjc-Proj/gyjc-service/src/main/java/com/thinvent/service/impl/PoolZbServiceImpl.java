package com.thinvent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.PoolZb;
import com.thinvent.entity.PoolZbNewTable;
import com.thinvent.entity.PoolZbNewTableZbInfo;
import com.thinvent.entity.SDictionaryItems;
import com.thinvent.mapper.IppMapper;
import com.thinvent.mapper.PoolZbNewTableMapper;
import com.thinvent.service.PoolZbNewTableZbInfoService;
import com.thinvent.service.PoolZbService;
import com.thinvent.service.SDictionaryItemsService;
import com.thinvent.utils.GyjcConstant;
import com.thinvent.utils.ZbDataMoveUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/7/17 15:38
 **/
@Service
public class PoolZbServiceImpl implements PoolZbService {

    @Autowired
    @SuppressWarnings("all")
    private IppMapper ippMapper;

    @Autowired
    @SuppressWarnings("all")
    private PoolZbNewTableMapper poolZbNewTableMapper;

    @Autowired
    private PoolZbNewTableZbInfoService poolZbNewTableZbInfoService;

    @Autowired
    private ZbDataMoveUtils zbDataMoveUtils;

    @Autowired
    private SDictionaryItemsService sDictionaryItemsService;

    @Override
    public PageInfo getPoolZbByMC(String zbmc, String bindzb, String state, int pageNum
            , String lyTable, String zbfl) {
        PageHelper.startPage(pageNum, 10);
        List<PoolZb> poolZbList = ippMapper.getPoolZb(zbmc, bindzb, state, lyTable, zbfl);
        return (new PageInfo(poolZbList));
    }

    @Override
    public int updateBindZb(String id, String bindZb) {
        //新绑定指标数组
        String[] strArr = null;
        if (!StringUtils.isEmpty(bindZb)) {
            strArr = bindZb.split(",");
        }
        //获取原指标对象
        PoolZb tpoolZb = ippMapper.getPoolZbById(id);
        //原绑定指标
        String originBindzb = tpoolZb.getBindZb();
        //原绑定指标数组
        String[] originBindArr = null;
        if (!StringUtils.isEmpty(originBindzb)) {
            originBindArr = originBindzb.split(",");
        }
        //绑定指标set集合
        HashSet<String> bindSet = new HashSet<>();
        if (strArr != null) {
            //将新绑定指标放入bindset
            bindSet.addAll(Arrays.asList(strArr));
        }
        if (null != originBindArr) {
            for (String orginBind:originBindArr) {
                if (bindSet.contains(orginBind)) {
                    //如果是原来就存在的绑定指标，无需更新，并从该set集合中去除该元素
                    bindSet.remove(orginBind);
                } else {
                    //如果不包含原来就存在的绑定指标，则是页面操作时去除了该绑定关系，双向解绑
                    //获取原绑定指标
                    PoolZb bpoolZb = ippMapper.getPoolZbById(orginBind);
                    bindZbFunc(id, bpoolZb);
                }
            }
        }

        //循环结束后，set集合中的元素即为新增的指标，为新增的指标做双向绑定操作
        for (String addBindZb:bindSet) {
            PoolZb addPoolzb = ippMapper.getPoolZbById(addBindZb);
            if (StringUtils.isEmpty(addPoolzb.getBindZb())) {
                //绑定指标为空时直接更新，置状态为2
                ippMapper.updateBindZbById(addBindZb, id
                        , "2");
            } else {
                //绑定指标不为空时在绑定指标后面加上该指标名称，置状态为3
                ippMapper.updateBindZbById(addBindZb, addPoolzb.getBindZb() + "," + id
                        , "3");
            }
        }

        if (strArr == null || strArr.length == 0) {
            //无绑定指标，设置为未操作
            return ippMapper.updateBindZbById(id, bindZb, "0");
        } else if (strArr.length > 1){
            //有多个绑定指标，设置为已操作，有疑似匹配项
            return ippMapper.updateBindZbById(id, bindZb, "3");
        } else {
            //有一个绑定指标，设置为已操作，有匹配项
            return ippMapper.updateBindZbById(id, bindZb, "2");
        }
    }

    @Override
    public int updateComment(String id, String comments) {
        return ippMapper.updateCommentById(id, comments);
    }

    @Override
    public int insertPoolZb(PoolZb poolZb) {
        int i = ippMapper.findZbmc(poolZb.getZbmc(), poolZb.getZbfl());
        if (i > 0) {
            return 0;
        } else {
            return ippMapper.insertPoolZb(poolZb);
        }
    }

    @Override
    public int undebundBindZb(String id) {
        //双向解绑 *************************** start
        PoolZb poolZb = ippMapper.getPoolZbById(id);
        //获取其绑定指标
        String bindZb = poolZb.getBindZb();
        if (bindZb != null){
            String[] zbArr = bindZb.split(",");
            for (String zbStr:zbArr) {
                PoolZb bpoolZb = null;
                bpoolZb = ippMapper.getPoolZbById(zbStr);
                bindZbFunc(id, bpoolZb);
            }
        }else{
            return 0;
        }
        //双向解绑 ******************************end
        return ippMapper.unbundPoolZb(id);
    }

    private void bindZbFunc(String id, PoolZb bpoolZb) {
        if(!StringUtils.isEmpty(bpoolZb)) {
            if (StringUtils.isEmpty(bpoolZb.getBindZb())
                    || !bpoolZb.getBindZb().contains(id)) {
                //do NoThing
            } else if (bpoolZb.getBindZb().contains(id + ",")) {
                String bindStr = bpoolZb.getBindZb().replace(id + ",", "");
                //去掉该绑定指标
                ippMapper.updateBindZbById(bpoolZb.getId(), bindStr
                        , bindStr.split(",").length > 1 ? "3" : "2");
            } else if (bpoolZb.getBindZb().contains("," + id)) {
                String bindStr = bpoolZb.getBindZb().replace("," + id, "");
                //去掉该绑定指标
                ippMapper.updateBindZbById(bpoolZb.getId(), bindStr
                        , bindStr.split(",").length > 1 ? "3" : "2");
            } else {
                ippMapper.updateBindZbById(bpoolZb.getId(), null, "0");
            }
        }
    }

    @Override
    public List<HashMap<String, String>> getAllZbmcAndFl(String sourceMark) {
        return ippMapper.getAllZbmcAndFl(sourceMark);
    }

    @Override
    public int updateStateByIds(List<String> idList, String state) {
        return ippMapper.updateStateByIds(idList, state);
    }

    @Override
    public String selectZbflByLyId(String lyid) {
        return ippMapper.selectZbflByLyId(lyid);
    }

    @Override
    public List<HashMap> getZbflList() {
        return ippMapper.getZbflList();
    }

    @Override
    public PageInfo getPoolZbByFl(String zbmc, String zbfl, int pageNum, String sourceMark) {
        PageHelper.startPage(pageNum, 10);
        List<PoolZb> poolZbList = ippMapper.getPoolZbByFl(zbmc, zbfl, sourceMark);
        return (new PageInfo(poolZbList));
    }

    @Override
    public int createTableForZb(String tableName, String zbList, String tableComments
            ,String startTime,String endTime, String tableTypeKey) {
        //匹配大写字母以及下划线,正则表达式
        String regex = "^[A-Z_]+$";
        if (!tableName.matches(regex)){
            //表名不合规范
            return 1;
        }
        try {
            //统一前缀ZB_
            tableName = GyjcConstant.ZB_TABLE_PREFIX + tableName;
            ippMapper.createZbTable(tableName);
        }catch (Exception e){
            e.printStackTrace();
            //创建失败，已存在该表
            return 2;
        }

        //记录主题表
        PoolZbNewTable poolZbNewTable = new PoolZbNewTable();
        poolZbNewTable.setId(UUID.randomUUID().toString().replace("-", ""));
        poolZbNewTable.setTableName(tableName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(!StringUtils.isEmpty(startTime)) {
                poolZbNewTable.setStartTime(simpleDateFormat.parse(startTime));
            }
            if(!StringUtils.isEmpty(endTime)) {
                poolZbNewTable.setEndTime(simpleDateFormat.parse(endTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //去重集合
        HashSet<String> resultZbSet = new HashSet<>();
        List<String> rZbList = Arrays.asList(zbList.split(","));
        PoolZbNewTableZbInfo poolZbNewTableZbInfo = null;
        for (String zbId : rZbList) {
            resultZbSet.add(zbId);
            poolZbNewTableZbInfo = new PoolZbNewTableZbInfo();
            //ID
            poolZbNewTableZbInfo.setId(UUID.randomUUID().toString().replace("-", ""));
            //主题表的id
            poolZbNewTableZbInfo.setTableId(poolZbNewTable.getId());
            //主题表名
            poolZbNewTableZbInfo.setTableName(tableName);
            //指标ID
            poolZbNewTableZbInfo.setZbid(zbId);
            //绑定标识
            poolZbNewTableZbInfo.setBindId(zbId);
            //保存
            poolZbNewTableZbInfoService.save(poolZbNewTableZbInfo);
            //获取指标池指标对象
            PoolZb poolZb = ippMapper.getPoolZbById(zbId);
            //获取绑定指标
            String bindZb = poolZb.getBindZb();
            if (!StringUtils.isEmpty(bindZb)) {
                List<String> bindList = Arrays.asList(bindZb.split(","));
                //绑定指标不为空，一起加入主题表(并去重)
                resultZbSet.addAll(bindList);
                for (String bindId : bindList) {
                    //新ID
                    poolZbNewTableZbInfo.setId(UUID.randomUUID().toString().replace("-", ""));
                    poolZbNewTableZbInfo.setZbid(bindId);
                    //保存带入的绑定指标
                    poolZbNewTableZbInfoService.save(poolZbNewTableZbInfo);
                }
            }
        }
        //转成逗号分隔的字符串
        zbList = org.apache.commons.lang.StringUtils.join(resultZbSet, ",");

        poolZbNewTable.setZbId(zbList);
        poolZbNewTable.setCreateTime(new Date());
        poolZbNewTable.setTableComments(tableComments);
        SDictionaryItems sDictionaryItems = sDictionaryItemsService
                .getOne(new QueryWrapper<SDictionaryItems>().eq("ITEM_KEY", tableTypeKey));
        //主题库编码
        poolZbNewTable.setZtkbm(tableTypeKey);
        //主题库名称
        poolZbNewTable.setZtkmc(sDictionaryItems.getItemValue());
        //插入新表数据
        poolZbNewTableMapper.insertData(poolZbNewTable);
        //查询并插入指标数据到新指标表
        zbDataMoveUtils.findZbAndInsertToTable(tableName, zbList,startTime,endTime);
        //创建成功
        return 0;
    }

    @Override
    public List<HashMap> getTreeList() {
        List<HashMap> zbflList = ippMapper.getZbflAndLyList();
        List<HashMap> dataList = new ArrayList<>();
        List<String> lyTableList = new ArrayList<>();

        for (HashMap hashMap : zbflList) {
            String lyTable = MapUtils.getString(hashMap, "SOURCE_MARK");
            String zbfl = MapUtils.getString(hashMap, "ZBFL");
            String newZb = MapUtils.getString(hashMap, "NEWZB");
            if (!lyTableList.contains(lyTable)) {
                lyTableList.add(lyTable);
                HashMap hashMap1 = new HashMap();
                hashMap1.put("label", GyjcConstant.convertZbly(lyTable));
                hashMap1.put("value", lyTable);
                hashMap1.put("icon", Boolean.FALSE);
                hashMap1.put("hasNew",newZb);
                List<HashMap> mapList = new ArrayList<>();
                HashMap<String, Object> strMap = new HashMap<>();
                strMap.put("label", zbfl);
                strMap.put("value", zbfl);
                strMap.put("hasNew",newZb);
                strMap.put("icon", Boolean.FALSE);
                mapList.add(strMap);
                hashMap1.put("children", mapList);
                dataList.add(hashMap1);
            } else {
                for (HashMap theMap:dataList) {
                    if (lyTable.equals(MapUtils.getString(theMap, "value"))) {
                        List aList = (List) theMap.get("children");
                        HashMap myMap = new HashMap();
                        myMap.put("label", zbfl);
                        myMap.put("value", zbfl);
                        myMap.put("hasNew",newZb);
                        myMap.put("icon", Boolean.FALSE);
                        aList.add(myMap);
                        if("true".equals(newZb)) {
                            theMap.put("hasNew", newZb);
                        }
                    }
                }
            }
        }
        return dataList;
    }

    @Override
    public List<PoolZb> getZbListByIdList(String idListStr) {
        if (StringUtils.isEmpty(idListStr)) {
            return null;
        }
        List<String> idList = Arrays.asList(idListStr.split(","));
        return ippMapper.getPoolZbsByIds(idList);
    }

    @Override
    public int updateJldwById(String id, String jldw) {
        return ippMapper.updateJldwById(id, jldw);
    }

}
