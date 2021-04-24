package com.thinvent.controller;

import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.PoolZbNewTableService;
import com.thinvent.service.PoolZbService;
import com.thinvent.utils.GyjcConstant;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 指标池业务请求
 *
 * @author xufeng
 * @version 1.0
 * @date 2020/7/17 16:26
 **/
@RestController
@RequestMapping(value = "/gyProj")
public class PoolZbController {

    @Autowired
    private PoolZbService poolZbService;

    @Autowired
    private PoolZbNewTableService poolZbNewTableService;

    @GetMapping(value = "/zbget")
    public GenericResponseModel findPoolZb(@RequestParam(name = "zbmc", required = false) String zbmc
            , @RequestParam(name = "bindZb", required = false) String bindZb
            , @RequestParam(name = "pageNum") Integer pageNum
            , @RequestParam(name = "state", required = false) String state
            , @RequestParam(name = "lyTable", required = false) String lyTable
            , @RequestParam(name = "zbfl", required = false) String zbfl) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //请求成功
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbService.getPoolZbByMC(zbmc, bindZb, state, pageNum, lyTable, zbfl));
        return genericResponseModel;
    }

    @PostMapping(value = "/zbUpdate/{id}")
    public GenericResponseModel updateBindZb(@PathVariable(name = "id") String id
            , @RequestBody String bindZb) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //请求成功
        genericResponseModel.setErrCode(200);
        bindZb = JsonPath.read(bindZb, "$.bindzbval");
        if (bindZb.length() > 2000) {
            genericResponseModel.setErrMsg("超出绑定限制，最多绑定60个指标，请勿绑定过多指标！");
            return genericResponseModel;
        }
        int i = poolZbService.updateBindZb(id, bindZb);
        if (i > 0) {
            genericResponseModel.setErrMsg("保存成功！");
        } else {
            genericResponseModel.setErrMsg("保存失败!");
        }
        return genericResponseModel;
    }

    @GetMapping(value = "/commentUpdate")
    public GenericResponseModel commentUpdate(@RequestParam(name = "id") String id
            , @RequestParam(name = "comment", required = false) String comment) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //请求成功
        genericResponseModel.setErrCode(200);
        int i = poolZbService.updateComment(id, comment);
        if (i > 0) {
            genericResponseModel.setErrMsg("更新成功！");
        } else {
            genericResponseModel.setErrMsg("更新失败！");
        }
        return genericResponseModel;
    }

    @GetMapping(value = "/jldwUpdate")
    public GenericResponseModel jldwUpdate(@RequestParam(name = "id") String id
    , @RequestParam(name = "jldw", required = false) String jldw) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //请求成功
        genericResponseModel.setErrCode(200);
        int i = poolZbService.updateJldwById(id, jldw);
        if (i > 0) {
            genericResponseModel.setErrMsg("更新成功！");
        } else {
            genericResponseModel.setErrMsg("更新失败！");
        }
        return genericResponseModel;
    }

    @GetMapping(value = "/unBundZb")
    public GenericResponseModel unBundZb(@RequestParam(name = "id") String id) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //请求成功
        genericResponseModel.setErrCode(200);
        int i = poolZbService.undebundBindZb(id);
        if (i > 0) {
            genericResponseModel.setErrMsg("解绑成功");
        } else {
            genericResponseModel.setErrMsg("解绑失败，请确认该指标是否绑定其它指标");
        }
        return genericResponseModel;
    }

    @GetMapping(value = "/getAllZb")
    public GenericResponseModel getAllZb(@RequestParam(name = "sourceMark", required = false)String sourceMark) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //请求成功
        genericResponseModel.setErrCode(200);
        //获取所有指标名称
        List<HashMap<String, String>> zbmcList = poolZbService.getAllZbmcAndFl(sourceMark);
        List<HashMap> hashMaps = new ArrayList<>();
        //封装成前台transfer能使用的数据形式
        for (HashMap hashMap:zbmcList) {
            //添加用|连接的指标名称以及分类
            String zbmc = hashMap.get("ZBMC") + "|" + hashMap.get("ZBFL");
            HashMap dataMap = new HashMap();
            dataMap.put("label", zbmc);
            dataMap.put("key", hashMap.get("ID"));
            dataMap.put("source", GyjcConstant.convertZbly((String) hashMap.get("SOURCE_MARK")));
            hashMaps.add(dataMap);
        }
        //放入返回响应变量
        genericResponseModel.setData(hashMaps);
        return genericResponseModel;
    }

    @GetMapping(value = "/setPoolZbState")
    public GenericResponseModel setPoolZbState(@RequestParam("ids")String[] ids
            , @RequestParam("state")String state) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //先解绑
        for (String id:ids) {
            if (!StringUtils.isEmpty(id)) {
                //解绑指标
                poolZbService.undebundBindZb(id);
            }
        }
        //更新指标状态为无匹配指标
        poolZbService.updateStateByIds(Arrays.asList(ids), "1");
        genericResponseModel.setErrMsg("设置成功");
        return genericResponseModel;
    }

    @GetMapping(value = "/getZbflList")
    public GenericResponseModel getZBFLList(){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //获取指标分类集合
        genericResponseModel.setData(poolZbService.getZbflList());
        return genericResponseModel;
    }

    @GetMapping(value = "/getZbByFl")
    public GenericResponseModel getPoolZbByfl(@RequestParam(name = "zbmc", required = false) String zbmc
            , @RequestParam(name = "zbfl", required = false) String zbfl
            , @RequestParam(name = "pageNum") Integer pageNum
            , @RequestParam(name = "sourceMark", required = false) String sourceMark){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //查询指标集合
        genericResponseModel.setData(poolZbService.getPoolZbByFl(zbmc, zbfl, pageNum, sourceMark));
        return genericResponseModel;
    }

    @GetMapping(value = "/tableCreate")
    public GenericResponseModel tableCreate(@RequestParam(name = "tableName") String tableName
            , @RequestParam(name = "zbList") String zbList
            , @RequestParam(name = "tableComments") String tableComments
            , @RequestParam(name = "startTime") String startTime
            , @RequestParam(name = "endTime") String endTime
            , @RequestParam(name = "tableType") String tableTypeKey) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //建表，检验等
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(tableComments)
                || StringUtils.isEmpty(tableTypeKey)) {
            genericResponseModel.setErrMsg("建表失败，表名、主题库类型或主题描述不能为空");
        } else {
            int i = poolZbService.createTableForZb(tableName, zbList, tableComments,startTime,endTime, tableTypeKey);
            if (i > 0) {
                genericResponseModel.setErrMsg("建表失败，请检查是否已存在该表或者表名不合规范");
            } else {
                genericResponseModel.setErrMsg("创建主题表成功!");
            }
        }
        return genericResponseModel;

    }

    @GetMapping(value = "/getTreeList")
    public GenericResponseModel getTreeList() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbService.getTreeList());
        return genericResponseModel;
    }

    @GetMapping(value = "/getZbByIdList")
    public GenericResponseModel getZbByIdList(@RequestParam(value = "idList", required = false) String idList) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(poolZbService.getZbListByIdList(idList));
        return genericResponseModel;
    }

    @GetMapping(value = "/getSourceSelectOptions")
    public GenericResponseModel getSourceSelectOptions(@RequestParam(value = "flag", required = false) boolean flag) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(GyjcConstant.getSelectOptions(flag));
        return genericResponseModel;
    }
}
