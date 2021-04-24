package com.thinvent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.SDictionaryItems;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.PoolZbNewTableService;
import com.thinvent.service.SDictionaryItemsService;
import com.thinvent.utils.GyDateUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/8/13 15:37
 **/
@RestController
@RequestMapping("/gyProj")
public class PoolZbNewTableController {

    @Autowired
    private PoolZbNewTableService poolZbNewTableService;

    @Autowired
    private SDictionaryItemsService sDictionaryItemsService;

    @GetMapping(value = "getTableList")
    public GenericResponseModel getTableList(@RequestParam("pageNum")int pageNum
            , @RequestParam(value = "tableName", required = false) String tableName
            , @RequestParam(value = "tableType", required = false) String tableType
            , @RequestParam(value = "createStartTime", required = false) String createStartTime
            , @RequestParam(value = "createEndTime", required = false) String createEndTime){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbNewTableService.getNewTableList(tableName, pageNum, tableType,createStartTime,createEndTime));
        return genericResponseModel;
    }

    @GetMapping(value = "deleteTable")
    public GenericResponseModel deleteTable(@RequestParam("tableName") String tableName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setData(poolZbNewTableService.deleteTable(tableName));
        genericResponseModel.setErrCode(200);
        return genericResponseModel;
    }

    @GetMapping(value = "deleteTableZb")
    public GenericResponseModel deleteTableZb(@RequestParam("tableId") String tableId
            , @RequestParam("zbmc") String zbmc
            , @RequestParam("zbId") String zbid){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        poolZbNewTableService.deleteTabelZb(tableId, zbid);
        return genericResponseModel;

    }

    @PostMapping(value = "deleteTableZbList/{tableId}")
    public GenericResponseModel deleteTableZbList(@PathVariable("tableId") String tableId
            , @RequestBody String jsonParam) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //指标参数Json字符串
        String param = JsonPath.read(jsonParam, "$.param");
        List<String> zbIdList = JsonPath.read(param, "$.[*].id");
        for (String zbId:zbIdList) {
            poolZbNewTableService.deleteTabelZb(tableId, zbId);
        }
        return genericResponseModel;
    }

    @GetMapping(value = "zbDataAddToTable")
    public GenericResponseModel zbDataAddToTable(@RequestParam("tableId") String tableId
            , @RequestParam("zbList") String zbList) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        if (poolZbNewTableService.zbDataAddToTable(tableId, zbList) < 0) {
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("您已添加该指标");
            return genericResponseModel;
        }
        genericResponseModel.setErrCode(200);
        genericResponseModel.setErrMsg("同步完成");
        return genericResponseModel;
    }


    @GetMapping(value = "getNewTableData")
    public GenericResponseModel getNewTableData(@RequestParam("pageNum") int pageNum
            , @RequestParam(value = "zbmc", required = false) String zbmc
            , @RequestParam(value = "sourceTable", required = false) String sourceTable
            , @RequestParam("tableName") String tableName
            , @RequestParam(value = "startDate", required = false) String startDate
            , @RequestParam(value = "endDate", required = false) String endDate
            , @RequestParam(value = "dwmc", required = false) String dwmc
            , @RequestParam(value = "dqmc", required = false) String dqmc) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        startDate = GyDateUtils.formatDate(startDate, 1);
        endDate = GyDateUtils.formatDate(endDate, 1);
        genericResponseModel.setData(poolZbNewTableService.getZbTableData(pageNum, zbmc, sourceTable, tableName, startDate, endDate, dwmc, dqmc));
        return genericResponseModel;
    }

    @PostMapping(value = "displayCreate/{tableName}")
    public GenericResponseModel displayCreate(@RequestBody String jsonParam
            , @PathVariable("tableName") String tableName){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //取出参数
        String param = JsonPath.read(jsonParam, "$.param");
        //转化为map集合
        List<HashMap> mapList = JsonPath.read(param, "$.[*]");
        String startTime = JsonPath.read(jsonParam, "$.startTime");
        String endTime = JsonPath.read(jsonParam, "$.endTime");
        //新建表并同步数据
        int result = poolZbNewTableService
                .displayTableCreate(tableName, tableName + "_ZS", mapList,startTime,endTime);
        //判断
        if (result < 0) {
            genericResponseModel.setErrMsg("表创建失败，请检查当前表是否已创建展示表！");
        } else {
            genericResponseModel.setErrMsg("创建展示表成功，并同步" + result + "条数据到展示表");
        }
        return genericResponseModel;
    }

    @GetMapping(value = "getTableTypeSelectList")
    public GenericResponseModel getTableTypeSelectList() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<SDictionaryItems> queryWrapper = new QueryWrapper<>();
        //可用
        queryWrapper.eq("ENABLE", 1);
        //主题库类型
        queryWrapper.eq("GROUP_NO", "26");
        //不为一级类型
        queryWrapper.ne("ITEM_PARENT", "0");
        genericResponseModel.setData(sDictionaryItemsService.list(queryWrapper));
        return genericResponseModel;
    }

    @GetMapping(value = "getTableTypeTree")
    public GenericResponseModel getTableTypeTree() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<SDictionaryItems> queryWrapper = new QueryWrapper<>();
        //主题库类型
        queryWrapper.eq("GROUP_NO", "26");
        queryWrapper.eq("ENABLE", 1);
        //一级类型
        queryWrapper.eq("ITEM_PARENT", "0");
        queryWrapper.select("ITEM_KEY AS value","ITEM_VALUE AS label");
        List<Map<String, Object>> resultObj = sDictionaryItemsService.listMaps(queryWrapper);
        for (Object resultMap : resultObj) {
            String itemKey = MapUtils.getString((Map) resultMap, "VALUE");
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("GROUP_NO", "26");
            queryWrapper.eq("ENABLE", 1);
            //二级类型
            queryWrapper.eq("ITEM_PARENT", itemKey);
            queryWrapper.select("ITEM_KEY AS value","ITEM_VALUE AS label");
            List<Map<String, Object>> childMapList = sDictionaryItemsService.listMaps(queryWrapper);
            ((Map) resultMap).put("children", childMapList);
        }
        genericResponseModel.setData(resultObj);
        return genericResponseModel;
    }

    @GetMapping("updateTableData")
    public GenericResponseModel updateTableData(@RequestParam("tableName") String tableName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        poolZbNewTableService.updateTableData(tableName);
        return genericResponseModel;
    }


}
