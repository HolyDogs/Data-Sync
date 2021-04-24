package com.thinvent.controller;

import com.github.pagehelper.PageInfo;
import com.thinvent.gyjcEnum.FrApiResultEnum;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.ApiDataService;
import com.thinvent.service.ApiWatcherService;
import com.thinvent.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
public class IndexController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping(value = "/getNoticeList")
    public GenericResponseModel getSelectList(@RequestParam(value= "pageNum" ,required = false) Integer pageNum,
                                              @RequestParam(value= "title" ,required = false) String title,
                                              @RequestParam(value= "subName" ,required = false) String subName,
                                              @RequestParam(value= "startTime" ,required = false) String startTime,
                                              @RequestParam(value= "endTime" ,required = false) String endTime) throws ParseException {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //设置响应码
        genericResponseModel.setErrCode(200);
        HashMap<String, Object> map = new HashMap<>();
        map.put("noticeType",title);
        map.put("publishStatus","YFB");
        map.put("index",true);
        map.put("noticeSubject",subName);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        genericResponseModel.setData(noticeService.getNoticeList(pageNum, map));
        return genericResponseModel;
    }

    /**
     *  获取消息信息
     * @param id
     * @param flag 判断是否可以为clickCount+1
     * @return
     * @throws ParseException
     */
    @GetMapping(value = "/getNoticeInfo")
    public GenericResponseModel getNoticeInfo(@RequestParam(value= "id") String id,
                                              @RequestParam(value= "flag") String flag) throws ParseException {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //设置响应码
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(noticeService.getNoticeInfo(id, flag));
        return genericResponseModel;
    }

}
