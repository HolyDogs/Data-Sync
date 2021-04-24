package com.thinvent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.secure.ActiveConnect;
import com.thinvent.entity.secure.BasicPropertyOnline;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.secure.ActiveConnectService;
import com.thinvent.service.secure.BasicPropertyOnlineService;
import com.thinvent.service.secure.ThreatTrapOnlineLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * 安全态势感知
 * @author xufeng
 * @version 1.0
 * @date 2021/1/6 14:06
 **/

@RestController
@Slf4j
@RequestMapping("/gyProj")
@AllArgsConstructor
public class SecureController {

    private final ActiveConnectService activeConnectService;
    private final BasicPropertyOnlineService basicPropertyOnlineService;
    private final ThreatTrapOnlineLogService threatTrapOnlineLogService;

    @GetMapping("/secure/activeConnect/getList")
    public GenericResponseModel genericResponseModel(@RequestParam(value = "pageNum") int pageNum) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        PageHelper.startPage(pageNum, 10);
        //查，查它丫的
        List<ActiveConnect> activeConnectList = activeConnectService.list();
        genericResponseModel.setData(new PageInfo<>(activeConnectList));
        return genericResponseModel;
    }

    @GetMapping("/secure/onlineMonitor/getList")
    public GenericResponseModel getOnlineMonitorModel(@RequestParam(value = "pageNum") int pageNum
            , @RequestParam(value = "dataType", required = false) String dataType
            , @RequestParam(value = "dqmc", required = false) String dqmc
            , @RequestParam(value = "company", required = false) String company
            , @RequestParam(value = "manufacturer", required = false) String manufacturer) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        //在线监测数据
        genericResponseModel.setData(basicPropertyOnlineService
                .getOnlineDataList(pageNum, dataType, dqmc, company, manufacturer));
        return genericResponseModel;
    }

    @GetMapping("/secure/onlineMonitor/getSelectList")
    public GenericResponseModel getOnlineSelectList() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct type");
        queryWrapper.last("ORDER BY (CASE TYPE WHEN '其他' THEN NULL ELSE TYPE END) nulls last");
        genericResponseModel.setData(basicPropertyOnlineService.listObjs(queryWrapper));
        return genericResponseModel;
    }


    @RequestMapping("/secure/threatTrapOnline/getList")
    public GenericResponseModel getThreatTrapList(@RequestParam(value = "pageNum") int pageNum
            , @RequestParam(value = "logType", required = false) String logType
            , @RequestParam(value = "logLevel", required = false) String logLevel
            , @RequestParam(value = "protocol", required = false) String protocol
            , @RequestParam(value = "country", required = false) String country) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        HashMap<String, String> map = new HashMap<>();
        map.put("logType",logType);
        map.put("logLevel",logLevel);
        map.put("protocol",protocol);
        map.put("country",country);
        //威胁诱捕数据
        genericResponseModel.setData(threatTrapOnlineLogService.getThreatTrapList(pageNum, map));
        return genericResponseModel;
    }


}
