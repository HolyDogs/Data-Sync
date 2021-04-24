package com.thinvent.controller.notoken;

import com.jayway.jsonpath.JsonPath;
import com.thinvent.entity.secure.ThreatTrapOnlineLog;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.secure.ThreatTrapOnlineLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

/**
 * @author xufeng
 * @version 1.0
 * @date 2021/1/19 08:49
 **/
@RestController
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class ReceiveThreatTrapDataController {

    private final ThreatTrapOnlineLogService threatTrapOnlineLogService;

    @RequestMapping(value = "/receiveData")
    public GenericResponseModel receiveData(@RequestBody String jsonData) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        try {
            String jsonStr = StringEscapeUtils.unescapeJava(jsonData);
            //log.info(jsonStr);
            //去除前后双引号
            jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
            String type = JsonPath.read(jsonStr, "$.type");
            if ("在线日志".equals(type)) {
                HashMap dataMap = JsonPath.read(jsonStr, "$.data");
                if (4 == MapUtils.getInteger(dataMap, "log_type")) {
                    //若为拉取日志，不保存
                    return genericResponseModel;
                }
                ThreatTrapOnlineLog threatTrapOnlineLog = new ThreatTrapOnlineLog();
                threatTrapOnlineLog.setAppendInfoSymbol(MapUtils.getString(dataMap, "append_info_symbol"));
                threatTrapOnlineLog.setConfirmedId(MapUtils.getString(dataMap, "confirmed_id"));
                threatTrapOnlineLog.setTaskExecuteTime(MapUtils.getString(dataMap, "task_execute_time"));
                threatTrapOnlineLog.setLevelId(MapUtils.getInteger(dataMap, "level_id"));
                threatTrapOnlineLog.setCreateTimeSecond(localTimeFormatDate(MapUtils.getString(dataMap, "create_time_second")));
                threatTrapOnlineLog.setCreateTime(localTimeFormatDate(MapUtils.getString(dataMap, "create_time")));
                threatTrapOnlineLog.setCreateTimeHour(localTimeFormatDate(MapUtils.getString(dataMap, "create_time_hour")));
                threatTrapOnlineLog.setCreateTimeDay(localTimeFormatDate(MapUtils.getString(dataMap, "create_time_day")));
                threatTrapOnlineLog.setLogType(MapUtils.getInteger(dataMap, "log_type"));
                threatTrapOnlineLog.setPassed(MapUtils.getString(dataMap, "passed"));
                threatTrapOnlineLog.setRequest(MapUtils.getString(dataMap, "request"));
                threatTrapOnlineLog.setInstruction(MapUtils.getString(dataMap, "instruction"));
                threatTrapOnlineLog.setRequestCreateTime(localTimeFormatDate(MapUtils.getString(dataMap, "request_create_time")));
                threatTrapOnlineLog.setSrcIp(MapUtils.getString(dataMap, "src_ip"));
                threatTrapOnlineLog.setSrcLongitude(MapUtils.getDouble(dataMap, "src_longitude"));
                threatTrapOnlineLog.setSrcLatitude(MapUtils.getDouble(dataMap, "src_latitude"));
                threatTrapOnlineLog.setSrcCountry(MapUtils.getString(dataMap, "src_country"));
                threatTrapOnlineLog.setSrcIsp(MapUtils.getString(dataMap, "src_isp"));
                threatTrapOnlineLog.setDstIp(MapUtils.getString(dataMap, "dst_ip"));
                threatTrapOnlineLog.setDstLongitude(MapUtils.getDouble(dataMap, "dst_longitude"));
                threatTrapOnlineLog.setDstLatitude(MapUtils.getDouble(dataMap, "dst_latitude"));
                threatTrapOnlineLog.setEventIp(MapUtils.getString(dataMap, "event_ip"));
                threatTrapOnlineLog.setEventStartTime(localTimeFormatDate(MapUtils.getString(dataMap, "event_start_time")));
                threatTrapOnlineLog.setEventEndTime(localTimeFormatDate(MapUtils.getString(dataMap, "event_end_time")));
                threatTrapOnlineLog.setProductId(MapUtils.getString(dataMap, "product_unique_code"));
                threatTrapOnlineLog.setProtocol(MapUtils.getString(dataMap, "protocol"));
                threatTrapOnlineLogService.save(threatTrapOnlineLog);
                log.info("=====威胁诱捕数据接收、更新成功！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genericResponseModel;
    }

    private java.util.Date localTimeFormatDate(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(timeStr);
            return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
