package com.thinvent.controller;


import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.PoolZbNewTableZsService;
import com.thinvent.utils.GyDateUtils;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xff
 * @since 2020-08-26
 */
@RestController
@Slf4j
@RequestMapping("/gyProj")
public class PoolZbNewTableZsController {

    @Autowired
    private PoolZbNewTableZsService poolZbNewTableZsService;

    @GetMapping(value = "/getZsTableList")
    public GenericResponseModel getZsTableList(@RequestParam("pageNum")int pageNum
            , @RequestParam(value = "tableName", required = false) String tableName
            , @RequestParam(value = "tableType", required = false) String tableType
            , @RequestParam(value = "createStartTime", required = false) String createStartTime
            , @RequestParam(value = "createEndTime", required = false) String createEndTime){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbNewTableZsService.getZsTableList(pageNum, tableName, tableType,createStartTime,createEndTime));
        return genericResponseModel;
    }

    @GetMapping(value = "/deleteZsTable")
    public GenericResponseModel deleteZsTable(@RequestParam("tableName")String tableName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //删除展示表以及表记录
        poolZbNewTableZsService.deleteZsTable(tableName);
        return genericResponseModel;
    }

    @GetMapping(value = "/deleteZsTableZb")
    public GenericResponseModel deleteZsTableZb(@RequestParam("tableId") String tableId
            , @RequestParam("zbId") String zbid) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        poolZbNewTableZsService.deleteZsTabelZb(tableId, zbid);
        return genericResponseModel;
    }

    @PostMapping(value = "/deleteZsTableZbList/{tableId}")
    public GenericResponseModel deleteZsTableZbList(@PathVariable("tableId") String tableId
            , @RequestBody String jsonParam) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        String param = JsonPath.read(jsonParam, "$.param");
        List<String> zbIdList = JsonPath.read(param, "$.[*].id");
        for (String zbId:zbIdList) {
            poolZbNewTableZsService.deleteZsTabelZb(tableId, zbId);
        }
        return genericResponseModel;
    }

    @GetMapping(value = "/getZsZbList")
    public GenericResponseModel getZsZbList(@RequestParam(name = "sourceMark", required = false) String sourceMark
            , @RequestParam(name = "zbmc", required = false) String zbmc
            , @RequestParam(name = "tableId")String tableId) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbNewTableZsService.getOtherZbList(tableId, zbmc, sourceMark));
        return genericResponseModel;
    }

    @GetMapping(value = "/addZbToZsTable")
    public GenericResponseModel addZbToZsTable(@RequestParam("tableId") String tableId
            , @RequestParam("zbList") String zbList) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //添加指标
        int i = poolZbNewTableZsService.addZbToZsTable(tableId, zbList);
        genericResponseModel.setErrMsg("指标增加成功，本次同步" + i + "条数据！");
        return genericResponseModel;
    }

    @GetMapping(value = "/editZbNewValue")
    public GenericResponseModel editZbNewValue(@RequestParam("tableName") String tableName
            , @RequestParam("id") String id
            , @RequestParam("value") String value) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //更新NEW_VALUE值
        poolZbNewTableZsService.updateNewValueOfZsTable(tableName, id, value);
        return genericResponseModel;
    }

    @PostMapping("editZbDisplayRange/{tableName}")
    public GenericResponseModel editZbDisplayRange(@RequestBody String jsonParam
            , @PathVariable("tableName") String tableName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //取出参数 起始日期
        String startDate = JsonPath.read(jsonParam, "$.startDate");
        //截止日期
        String endDate = JsonPath.read(jsonParam, "$.endDate");
        //指标参数字符串
        String param = JsonPath.read(jsonParam, "$.param");
        //解析成map集合
        List<HashMap> mapList = JsonPath.read(param, "$.[*]");
        //设置相应范围内的指标数据可展示
        poolZbNewTableZsService.setDisplayDateRange(tableName, startDate, endDate, mapList);
        return genericResponseModel;
    }

/*    @GetMapping("countOneZbData")
    public GenericResponseModel countOneZbData(@RequestParam("tableName") String tableName
            , @RequestParam("zbmc") String zbmc
            , @RequestParam("dataSource") String dataSource
            , @RequestParam("dimension") String dimension) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        int num =poolZbNewTableZsService.countToTjTable(tableName, zbmc, dataSource, dimension);
        //int num = poolZbNewTableZsService.countOneZbData(tableName, zbmc, dataSource, dimension);
        //统计数据条数
        genericResponseModel.setData(num);
        return genericResponseModel;
    }*/

    @PostMapping("countToTjTable/{action}")
    public GenericResponseModel countToTjTable(@RequestBody String requestStr
            , @PathVariable("action") String action) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //获取表名
        String tableName = JsonPath.read(requestStr, "$.tableName");
        //解析维度
        String dimension = JsonPath.read(requestStr, "$.dimension");
        String startDate = null;
        String endDate = null;
        int limit = 0;
        try {
            //解析起始日期
            startDate = JsonPath.read(requestStr, "$.startDate");
            //解析截止日期
            endDate = JsonPath.read(requestStr, "$.endDate");
            //前多少名
            limit = JsonPath.read(requestStr, "$.limit");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析失败，前台未传该参数");
        }
        //解析出指标名称以及数据来源的字符串
        String param = JsonPath.read(requestStr, "$.param");
        //解析成map集合
        List<HashMap> mapList = JsonPath.read(param, "$.[*]");
        //用于统计生成数据条数
        int count = 0;

        //循环map，调用接口生成或删除统计数据，返回影响条数
        if ("add".equals(action)) {
            //重点监测企业
            if ("重点企业统计".equals(dimension)) {
                List<String> zbkeyList = mapList.stream()
                        .map(map -> MapUtils.getString(map, "zbkey"))
                        .collect(Collectors.toList());
                genericResponseModel.setData(poolZbNewTableZsService.countDwNum(tableName, zbkeyList));
                return genericResponseModel;
            }
            for (HashMap hashMap:mapList) {
                //指标别名
                String zbmc = MapUtils.getString(hashMap, "comments");
                if (StringUtils.isEmpty(zbmc)) {
                    //别名为空再取指标名称
                    zbmc = MapUtils.getString(hashMap, "zbmc");
                }
                count = count + poolZbNewTableZsService
                        .countToTjTable(tableName
                                , zbmc
                                , MapUtils.getString(hashMap, "sourceMark")
                                , MapUtils.getString(hashMap, "zbkey")
                                , dimension
                                , startDate
                                , endDate
                                , limit);
            }
        } else if ("delete".equals(action)){
            for (HashMap hashMap : mapList) {
                count = count + poolZbNewTableZsService
                        .deleteZbTjData(tableName
                                , MapUtils.getString(hashMap, "zbkey")
                                , dimension);
            }
        }
        genericResponseModel.setData(count);
        return genericResponseModel;
    }


    @GetMapping(value = "getTjTableData")
    public GenericResponseModel getTjTableData(@RequestParam("pageNum") int pageNum
            , @RequestParam(value = "zbmc", required = false) String zbmc
            , @RequestParam(value = "sourceTable", required = false) String sourceTable
            , @RequestParam("tableName") String tableName
            , @RequestParam(value = "startDate", required = false) String startDate
            , @RequestParam(value = "endDate", required = false) String endDate
            , @RequestParam(value = "statisticMark", required = false) String statisticMark
            , @RequestParam(value = "dqmc", required = false) String dqmc) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        startDate = GyDateUtils.formatDate(startDate, 1);
        endDate = GyDateUtils.formatDate(endDate, 1);
        //时间筛选暂时未使用
        genericResponseModel.setData(poolZbNewTableZsService
                .getZsTjTableData(pageNum, zbmc, sourceTable, tableName
                        , startDate, endDate, statisticMark, dqmc));
        return genericResponseModel;
    }

    @GetMapping(value = "getTJTree")
    public GenericResponseModel getTJTree(@RequestParam(value = "enable", required = false)String enable) {
        if(StringUtils.isEmpty(enable)){
            enable="2";
        }
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbNewTableZsService.getTJTree(enable));
        return genericResponseModel;
    }

    @GetMapping(value = "getAreaTree")
    public GenericResponseModel getAreaTree() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbNewTableZsService.getAreaTree());
        return genericResponseModel;
    }
}

