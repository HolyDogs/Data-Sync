package com.thinvent.task;


import com.thinvent.mapper.YwSystemDataMoveMapper;
import com.thinvent.service.secure.BasicPropertyOnlineService;
import com.thinvent.service.secure.ThreatTrapOnlineLogService;
import com.thinvent.utils.GyjcConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数据量统计定时任务
 * @author xufeng
 * @version 1.0
 * @date 2020/8/13 09:34
 **/
@Component
@Slf4j
@AllArgsConstructor
public class ImportNewZbDataTask {

    private final YwSystemDataMoveMapper ywSystemDataMoveMapper;
    private final BasicPropertyOnlineService basicPropertyOnlineService;
    private final ThreatTrapOnlineLogService threatTrapOnlineLogService;

    /**
     * 每月最后一天启动(表达式不便于表示最后一天，使用每个月第一天零点执行，然后保存时月份-1)
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    @Async("taskExecutor")
    public void scanNewZbTable() {
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.YW_SYSTEM_DATA_MOVE);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.STATISTIC_ZB);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.EXCEL_HAND_IMPORT);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.SATELLITE_EARTH_STATION);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.BASESTATION_USE_SCHEDULE);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.BASESTATION_COORDINATION);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonth(GyjcConstant.ZB_TSGYZB_ZS_TJ);
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonthSpecial(GyjcConstant.BASIC_PROPERTY_ONLINE
                , basicPropertyOnlineService.count());
        ywSystemDataMoveMapper.insertIntoCountTableLastDayOfMonthSpecial(GyjcConstant.THREAT_TRAP_ONLINE
                , threatTrapOnlineLogService.count());
    }
}
