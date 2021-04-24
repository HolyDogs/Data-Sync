package com.thinvent.controller;

import com.thinvent.gyjcEnum.FrApiResultEnum;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.ApiDataService;
import com.thinvent.service.ApiWatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 法人相关controller
 * @author xufeng
 * @version 1.0
 * @date 2020/7/13 14:47
 **/
@RestController
@RequestMapping(value = "/gyProj")
public class MyController {

    @Autowired
    private ApiDataService apiDataService;

    @Autowired
    private ApiWatcherService apiWatcherService;

    @GetMapping(value = "/api/{apiName}/{param}", produces = "application/json;charset=utf-8")
    public GenericResponseModel requestApi(@PathVariable(name = "apiName")String apiName
            , @PathVariable(name = "param")String param){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //装入请求数据
        genericResponseModel.setData(apiDataService.getDataList(apiName, param));

        //测试数据 *******
        //String zzz = "[{\"id\":\"3600005000041822\",\"qymc\":\"思创数码科技股份有限公司\",\"zch\":\"360000210004449\",\"tyshxydm\":\"\",\"fddbr\":\"游建平\",\"qylx\":\"1222\",\"djjg\":\"001\",\"qyzt\":\"1\",\"hyml\":\"F\",\"hydm\":\"5273\",\"clrq\":\"20000803000000\",\"yzbm\":\"\",\"dzyj\":\"\",\"xkjyxm\":\"\",\"ybjyxm\":\"为计算机系统服务；数据处理；公共软件、基础软件、应用软件开发与服务；软件咨询及培训；弱电工程的设计、施工；智能楼宇布线；防雷工程设计与施工；宽带及多媒体解决方案；计算机、电子产品、通讯设备及配件的销售；进出口经营；税控收款机的工程安装、销售、技术支持和售后服务。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"sdjggss\":\"001147058049\",\"gxjg\":\"001147058\",\"jyqxstart\":\"20000803000000\",\"jyqxend\":\"\",\"zlqxstart\":\"\",\"zlqxend\":\"\",\"xydj\":\"A\",\"zsszdxzqh\":\"360106\",\"zs\":\"江西省南昌市高新区火炬大道681号\",\"zscq\":\"02\",\"zsszjjkfq\":\"\",\"sfzrd\":\"0\",\"zrdhy\":\"\",\"zdhynr\":\"\",\"sfzdqy\":\"0\",\"zdqynr\":\"\",\"sfzsszjjkfq\":\"\",\"hzrq\":\"20151215091249\",\"gpz\":\"\",\"sfcqyx\":\"1\",\"jylb\":\"\",\"sfwstzqytz\":\"0\",\"zsxzqh1\":\"\",\"zsxzqh2\":\"\",\"zsxzqh3\":\"\",\"hsfs\":\"\",\"scjydszxzqh\":\"\",\"scjyd\":\"\",\"scjydxzqh1\":\"\",\"scjydxzqh2\":\"\",\"scjydxzqh3\":\"\",\"jysx\":\"\",\"sfwsqr\":\"\",\"sfjtmgs\":\"\",\"jtmc\":\"\",\"jtjc\":\"\",\"i_date\":\"20151207013757\",\"u_date\":\"20190522151159\",\"rowkey\":\"91360000705517982P\"}]";
        //List<HashMap> mapList = JsonPath.read(zzz, "$.[*]");
        //genericResponseModel.setData(mapList);
        //测试数据 ********
        //设置响应码
        genericResponseModel.setErrCode(200);
        return genericResponseModel;
    }

    @GetMapping(value = "/getSelectList")
    public GenericResponseModel getSelectList() {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //设置响应码
        genericResponseModel.setErrCode(200);
        List<HashMap> list = new ArrayList<>();
        for (FrApiResultEnum frApiResultEnum:FrApiResultEnum.values()) {
            HashMap hashMap = new HashMap(16);
            //下拉值
            hashMap.put("value", frApiResultEnum.name());
            //下拉标签
            hashMap.put("label", frApiResultEnum.getApiName());
            //参数名
            hashMap.put("param", frApiResultEnum.getParamName());
            list.add(hashMap);
        }
        genericResponseModel.setData(list);
        return genericResponseModel;
    }

    //需要删除
    @GetMapping("/getApiResult")
    public GenericResponseModel getApiResult(@RequestParam(value= "pageNum" ,required = false) Integer pageNum,
                                             @RequestParam(value= "provider" ,required = false) String provider,
                                             @RequestParam(value= "producer" ,required = false) String producer,
                                             @RequestParam(value= "result" ,required = false) String result,
                                             @RequestParam(value= "startTime" ,required = false) String startTime,
                                             @RequestParam(value= "endTime" ,required = false) String endTime) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(apiWatcherService.getApiInfo(pageNum,provider,producer,result,startTime,endTime));
        return genericResponseModel;
    }

}
