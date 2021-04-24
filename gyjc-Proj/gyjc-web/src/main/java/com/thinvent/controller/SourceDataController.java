package com.thinvent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.SDictionaryItems;
import com.thinvent.entity.YwSystemDataMove;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.*;
import com.thinvent.task.UpdateDataTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 原数据（工业运行监测、国家数据网等）请求接口
 * @author xufeng
 * @version 1.0
 * @date 2020/8/18 16:07
 **/
@RestController
@RequestMapping(value = "/gyProj")
public class SourceDataController {

    @Autowired
    private YwSystemDataMoveService ywSystemDataMoveService;

    @Autowired
    private StatisticZbService statisticZbService;

    @Autowired
    private ExcelHandImportService excelHandImportService;

    @Autowired
    private BasestationService basestationService;

    @Autowired
    private ZbTsgyzbZsTjService tsgyzbZsTjService;

    @Autowired
    private SDictionaryItemsService sDictionaryItemsService;

    @Autowired
    private UpdateDataTask updateDataTask;

    @GetMapping(value = "/ywsystem/getList")
    public GenericResponseModel genericResponseModel(@RequestParam(value = "zbmc", required = false) String zbmc
            , @RequestParam(value = "lyid", required = false) String lyid
            , @RequestParam(value = "pageNum") int pageNum
            , @RequestParam(value = "zbfz", required = false) String zbfz
            , @RequestParam(value = "dwmc", required = false) String dwmc
            , @RequestParam(value = "dqmc", required = false) String dqmc
            , @RequestParam(value = "startTime", required = false) String startTime
            , @RequestParam(value = "endTime", required = false) String endTime) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        YwSystemDataMove ywSystemDataMove = new YwSystemDataMove();
        ywSystemDataMove.setLyid(lyid);
        ywSystemDataMove.setZbmc(zbmc);
        ywSystemDataMove.setZbmcfz(zbfz);
        ywSystemDataMove.setQssj(startTime);
        ywSystemDataMove.setJzsj(endTime);
        ywSystemDataMove.setDqmc(dqmc);
        ywSystemDataMove.setDwmc(dwmc);
        genericResponseModel.setData(ywSystemDataMoveService.getListPage(pageNum, ywSystemDataMove));
        return genericResponseModel;
    }

    @GetMapping(value = "/ywsystem/getZbflList")
    public GenericResponseModel genericResponseModel() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(ywSystemDataMoveService.selectList());
        return genericResponseModel;
    }

    @GetMapping(value = "/statistic/getList")
    public GenericResponseModel getStatisticDataList(@RequestParam(value = "zbmc", required = false) String zbmc
            , @RequestParam(value = "zbfl", required = false)String zbfl
            , @RequestParam(value = "pageNum") int pageNum
            , @RequestParam(value = "startTime", required = false) String startTime
            , @RequestParam(value = "endTime", required = false) String endTime) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(statisticZbService.getDataList(pageNum, zbmc, zbfl,startTime,endTime));
        return genericResponseModel;
    }

    @GetMapping(value = "/statistic/getZbflList")
    public GenericResponseModel getZbflListSta() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(statisticZbService.getSelectList());
        return genericResponseModel;
    }

    @GetMapping(value = "/getDataCount")
    public GenericResponseModel getDataCount(@RequestParam("dataType") String dataType) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(ywSystemDataMoveService.getDataCount(dataType));
        return genericResponseModel;
    }

    @GetMapping(value = "/handImport/getZbflList")
    public GenericResponseModel getHandImpZbflList() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(excelHandImportService.getZbflSelectList());
        return genericResponseModel;
    }

    @GetMapping(value = "/handImport/getList")
    public GenericResponseModel getHandImpDataList(@RequestParam(value = "zbmc", required = false) String zbmc
            , @RequestParam(value = "zbfl", required = false) String zbfl
            , @RequestParam(value = "dqmc", required = false) String dqmc
            , @RequestParam(value = "pageNum") int pageNum
            , @RequestParam(value = "startTime", required = false) String startTime
            , @RequestParam(value = "endTime", required = false) String endTime) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(excelHandImportService.getDataList(pageNum, zbmc, zbfl, dqmc,startTime,endTime));
        return genericResponseModel;
    }

    @GetMapping(value = "/5G/getEarthStation")
    public GenericResponseModel getEarthStation(@RequestParam(value = "pageNum") int pageNum,
                                                @RequestParam(value = "bhqdsbh",required = false) String bhqdsbh,
                                                @RequestParam(value = "yhdwmc",required = false) String yhdwmc,
                                                @RequestParam(value = "lxr",required = false) String lxr,
                                                @RequestParam(value = "startTime",required = false) String startTime,
                                                @RequestParam(value = "endTime",required = false) String endTime) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(basestationService.getEarthStations(pageNum,bhqdsbh,yhdwmc,lxr,startTime,endTime));
        return genericResponseModel;
    }

    @GetMapping(value = "/5G/getUseSchedule")
    public GenericResponseModel getUseSchedule(@RequestParam(value = "pageNum") int pageNum,
                                               @RequestParam(value = "jzsyh",required = false) String jzsyh,
                                               @RequestParam(value = "tzszsyr",required = false) String tzszsyr,
                                               @RequestParam(value = "lxr",required = false) String lxr,
                                               @RequestParam(value = "jzlx",required = false) String jzlx,
                                               @RequestParam(value = "jzzt",required = false) String jzzt,
                                               @RequestParam(value = "startTime",required = false) String startTime,
                                               @RequestParam(value = "endTime",required = false) String endTime) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(basestationService.getUseSchedules(pageNum,jzsyh,tzszsyr,lxr,jzlx,jzzt,startTime,endTime));
        return genericResponseModel;
    }

    @GetMapping(value = "/5G/getCoordination")
    public GenericResponseModel getCoordination(@RequestParam(value = "pageNum") int pageNum,
                                                @RequestParam(value = "bhqdsbh",required = false) String bhqdsbh,
                                                @RequestParam(value = "yhdwmc",required = false) String yhdwmc,
                                                @RequestParam(value = "sfxyxt",required = false) String sfxyxt,
                                                @RequestParam(value = "sfyfqxtqq",required = false) String sfyfqxtqq,
                                                @RequestParam(value = "sfyqrxtqq",required = false) String sfyqrxtqq,
                                                @RequestParam(value = "sfywcxt",required = false) String sfywcxt) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(basestationService.getCoordinations(pageNum,bhqdsbh,yhdwmc,sfxyxt,sfyfqxtqq,sfyqrxtqq,sfywcxt));
        return genericResponseModel;
    }

    @GetMapping(value = "/tsgyzb/getSelectList")
    public GenericResponseModel getTsgyhySelectList() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(sDictionaryItemsService
                .list(new QueryWrapper<SDictionaryItems>()
                        .eq("GROUP_NO", 38)));
        return genericResponseModel;
    }

    @GetMapping(value = "/tsgyzb/getList")
    public GenericResponseModel getTsgyhyDataList(@RequestParam(value = "pageNum") int pageNum
            , @RequestParam(value = "dataType", required = false) String dataType
            , @RequestParam(value = "theName", required = false) String theName
            , @RequestParam(value = "dqmc", required = false) String dqmc) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(tsgyzbZsTjService.getList(pageNum, dataType, theName, dqmc));
        return genericResponseModel;
    }

    @GetMapping("/ywsystem/updateYwData")
    public GenericResponseModel updateYwData() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        if (UpdateDataTask.count > 0) {
            //禁止重复启动任务更新数据
            genericResponseModel.setErrCode(204);
            return genericResponseModel;
        }
        try {
            //反射获取该更新定时任务中所有的方法
            Method[] methods = updateDataTask.getClass().getMethods();
            //筛选需要手动执行更新的方法
            for (Method method:methods) {
                if (method.getName().startsWith("dataUpdate")) {
                    //手动触发更新
                    method.invoke(updateDataTask);
                }
            }
            //手动等待一段时间
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
            genericResponseModel.setErrCode(202);
            return genericResponseModel;
        }
        if (UpdateDataTask.count > 0) {
            //若线程仍未运行完，提示用户需要等待更新完
            genericResponseModel.setErrCode(201);
        } else {
            //若线程20秒内就运行完，给前台提示更新成功
            genericResponseModel.setErrCode(200);
        }
        return genericResponseModel;
    }
}
