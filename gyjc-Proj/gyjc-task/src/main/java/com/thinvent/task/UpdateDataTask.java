package com.thinvent.task;

import com.thinvent.gyjcEnum.EnrolEnum;
import com.thinvent.entity.PoolZb;
import com.thinvent.mapper.IppMapper;
import com.thinvent.mapper.MonthMapper;
import com.thinvent.service.PoolZbService;
import com.thinvent.utils.GyjcConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 每月数据迁移定时任务
 *
 * @author xufeng
 * @version 1.0
 * @date 2020/7/2 上午 9:44
 * TODO 2020/11/3 由于指标池内的工业运行监测数据指标名后续改为了指标名称+指标名称附注
 *             ，若仍要使用本类里的定时任务更新指标数据
 *             ，建议修改指标入指标池逻辑
 **/
@Slf4j
@Component
public class UpdateDataTask {

    /**
     * 用于标识当时是否已有任务在执行
     */
    public static int count = 0;

    @SuppressWarnings("all")
    @Autowired
    private MonthMapper monthMapper;

    @SuppressWarnings("all")
    @Autowired
    private IppMapper ippMapper;

    @Autowired
    private PoolZbService poolZbService;


    /**
     * 线程安全地对标识进行处理
     * @param plusFlag 加减标志
     */
    private synchronized void editCount(boolean plusFlag) {
        if (plusFlag) {
            count++;
        } else {
            count--;
        }
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate1() {
        editCount(true);
        log.info("task1 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        //获取最新时间
        String start = ippMapper.getLastDateByLyid("20200628001");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData1(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "1-本月");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200628001");
            ippMapper.insertToDataMove(theMap);
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            //插入指标池
            //insertPoolZb((String) theMap.get("ENROL_NAME"), (String) theMap.get("LYID"));
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate2() {
        count++;
        log.info("task2 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200629001");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData2(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", theMap.get("ENROL_NAME"));
            theMap.put("ENROL_NAME", "企业单位数");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200629001");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利润总额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利润总额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "亏损企业亏损额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "亏损企业亏损额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "营业收入本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "营业收入同比");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate3() {
        editCount(true);
        log.info("task3 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200629002");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData3(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", theMap.get("ENROL_NAME"));
            theMap.put("ENROL_NAME", "指数值(上年同月=100)");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200629002");
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate4() {
        editCount(true);
        log.info("task4 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200629003");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData4(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "用户个数");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("ZBMCFZ", theMap.get("DIM_NAME"));
            theMap.put("LYID", "20200629003");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "用户用电装接容量");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "用电量本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "用电量上年同月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "用电量累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "用电量上年累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate5() {
        editCount(true);
        log.info("task5 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200629004");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData5(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "指标值");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200629004");
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate6() {
        editCount(true);
        log.info("task6 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200629005");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData6(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", theMap.get("ENROL_NAME"));
            theMap.put("ENROL_NAME", "园区内工业企业个数");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200629005");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入本年本月累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "招商实际到位资金本年本月累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "招商实际到位资金同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业增加值本年本月累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业增加值同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额本年本月累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "从业人员(人)本年本月累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "从业人员(人)同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate7() {
        editCount(true);
        log.info("task7 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200629006");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData7(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "本年本月");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200629006");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "本年1-本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "上年同期1-本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            //插入指标池
            //insertPoolZb((String) theMap.get("ENROL_NAME"), (String) theMap.get("LYID"));
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate8() {
        editCount(true);
        log.info("task8 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200630001");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData8(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产品名称(必填项)");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", theMap.get("ROW1") == null ? "":String.valueOf(theMap.get("ROW1"))
                    .replaceAll("<FONT>", "").replaceAll("</FONT>", ""));
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("LYID", "20200630001");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "计量单位");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "年初库存量本年");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "年初库存量上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "销售量1－本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "销售量上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "企业自用及其他1－本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "企业自用及其他上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "期末库存量本年");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "期末库存量上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "订货量1－本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "订货量上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW12") == null ? "":theMap.get("ROW12")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "订货额1－本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW13") == null ? "":theMap.get("ROW13")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "订货额上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW14") == null ? "":theMap.get("ROW14")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产品价格（税前）1－本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW15") == null ? "":theMap.get("ROW15")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产品价格（税前）上年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW16") == null ? "":theMap.get("ROW16")));
            ippMapper.insertToDataMove(theMap);


        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate9() {
        editCount(true);
        log.info("task9 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200630002");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData9(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "绝对值");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("ENROL_NAME", theMap.get("DIM_NAME"));
            theMap.put("LYID", "20200630002");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            //插入指标池
            //insertPoolZb((String) theMap.get("ENROL_NAME"), (String) theMap.get("LYID"));
        }
        editCount(false);
    }


    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate10() {
        editCount(true);
        log.info("task10 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200630003");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData10(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "本年1—本月");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200630003");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "上年同期1—本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            //插入指标池
            //insertPoolZb((String) theMap.get("ENROL_NAME"), (String) theMap.get("LYID"));
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate11() {
        editCount(true);
        log.info("task11 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200630004");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData11(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "生产总值(亿元)绝对值");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200630004");
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("ZBMCFZ", theMap.get("DIM_NAME"));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "生产总值(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "财政总收入(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "财政总收入(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "公共财政预算收入(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "公共财政预算收入(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "规模以上工业增加值(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "规模以上工业增加值(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "固定资产投资(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "固定资产投资(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "社会消费品零售总额(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "社会消费品零售总额(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW12") == null ? "":theMap.get("ROW12")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "出口总额(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW13") == null ? "":theMap.get("ROW13")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "出口总额(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW14") == null ? "":theMap.get("ROW14")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "外商直接投资实际使用金额(亿元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW15") == null ? "":theMap.get("ROW15")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "外商直接投资实际使用金额(亿元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW16") == null ? "":theMap.get("ROW16")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "城镇居民人均可支配收入(元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW17") == null ? "":theMap.get("ROW17")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "城镇居民人均可支配收入(元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW18") == null ? "":theMap.get("ROW18")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "农民人均纯收入(元)绝对值");
            theMap.put("ROW", String.valueOf(theMap.get("ROW19") == null ? "":theMap.get("ROW19")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "农民人均纯收入(元)增长%");
            theMap.put("ROW", String.valueOf(theMap.get("ROW20") == null ? "":theMap.get("ROW20")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate12() {
        editCount(true);
        log.info("task12 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200630005");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData12(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", theMap.get("ENROL_NAME"));
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("ENROL_NAME", "单位数");
            theMap.put("LYID", "20200630005");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "亏损企业数本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "亏损企业数同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利润总额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利润总额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "亏损企业亏损额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "亏损企业亏损额同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "经济效益综合指数本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW12") == null ? "":theMap.get("ROW12")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "经济效益综合指数同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW13") == null ? "":theMap.get("ROW13")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "总资产贡献率本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW14") == null ? "":theMap.get("ROW14")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "总资产贡献率同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW15") == null ? "":theMap.get("ROW15")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "资本保值增值率本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW16") == null ? "":theMap.get("ROW16")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "资本保值增值率同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW17") == null ? "":theMap.get("ROW17")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "资产负债率本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW18") == null ? "":theMap.get("ROW18")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "资产负债率同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW19") == null ? "":theMap.get("ROW19")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "流动资产周转次数本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW20") == null ? "":theMap.get("ROW20")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "流动资产周转次数同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW21") == null ? "":theMap.get("ROW21")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "成本费用利润率本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW22") == null ? "":theMap.get("ROW22")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "成本费用利润率同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW23") == null ? "":theMap.get("ROW23")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "全员劳动生产率本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW24") == null ? "":theMap.get("ROW24")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "全员劳动生产率同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW25") == null ? "":theMap.get("ROW25")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产品销售率本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW26") == null ? "":theMap.get("ROW26")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产品销售率同比增减百分点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW27") == null ? "":theMap.get("ROW27")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "营业收入本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW28") == null ? "":theMap.get("ROW28")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "营业收入同比");
            theMap.put("ROW", String.valueOf(theMap.get("ROW29") == null ? "":theMap.get("ROW29")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate13() {
        editCount(true);
        log.info("task13 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701001");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData13(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "本年本月止累计");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200701001");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "同比增长（%）");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "鄱阳湖生态区园区合计本年本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "鄱阳湖生态区园区合计同比增长（%）");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            //插入指标池
            //insertPoolZb((String) theMap.get("ENROL_NAME"), (String) theMap.get("LYID"));
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate14() {
        editCount(true);
        log.info("task14 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701002");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData14(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            String fz = (String) theMap.get("ENROL_NAME");
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "本年实际本月");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200701002");
            EnrolEnum enrol = EnrolEnum.get(String.valueOf(theMap.get("CALLING_ID")));
            if (enrol != null) {
                theMap.put("ENROL_ID", enrol.getEnrolId());
                theMap.put("ZBMCFZ", enrol.getEnrolName());
            } else {
                theMap.put("ZBMCFZ", fz);
            }
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "本年实际本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "同比增长比同月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "同比增长比同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate15() {
        editCount(true);
        log.info("task15 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701003");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData15(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "本年实际本月");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200701003");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "本年实际本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "同比增长(%)比同月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", "同比增长(%)比同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            //插入指标池
            //insertPoolZb((String) theMap.get("ENROL_NAME"), (String) theMap.get("LYID"));
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate16() {
        editCount(true);
        log.info("task16 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701004");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData16(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ZBMCFZ", theMap.get("ENROL_NAME"));
            theMap.put("ENROL_NAME", "主营业务收入本月止累计");
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200701004");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "主营业务收入同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利润总额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利润总额去年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "利税总额去年同期");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "营业收入本月止累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "营业收入同比");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate17() {
        editCount(true);
        log.info("task17 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701005");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData17(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("ENROL_NAME", "工业增加值本月");
            theMap.put("ENROL_ID", theMap.get("CALLING_ID"));
            theMap.put("LYID", "20200701005");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业增加值累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "比去年同期增长本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "比去年同期增长累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate18() {
        editCount(true);
        log.info("task18 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701006");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData18(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("ENROL_NAME", "工业产品销售率本月");
            theMap.put("ENROL_ID", theMap.get("CALLING_ID"));
            theMap.put("LYID", "20200701006");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业产品销售率累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "比去年同期增减本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "比去年同期增减累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate19() {
        editCount(true);
        log.info("task19 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200701007");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData19(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200701007");
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("ENROL_NAME", "工业增加值(亿元，现价)累计比重");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业增加值(亿元，现价)本月比重");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业增加值(亿元，现价)累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "工业增加值(亿元，现价)本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产销率累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产销率本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产销率累计同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "产销率本月同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "轻工业产销率累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "轻工业产销率本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "轻工业产销率累计同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "轻工业产销率本月同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW12") == null ? "":theMap.get("ROW12")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "重工业产销率累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW13") == null ? "":theMap.get("ROW13")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "重工业产销率本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW14") == null ? "":theMap.get("ROW14")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "重工业产销率累计同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW15") == null ? "":theMap.get("ROW15")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "重工业产销率本月同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW16") == null ? "":theMap.get("ROW16")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "出口交货值累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW17") == null ? "":theMap.get("ROW17")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "出口交货值本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW18") == null ? "":theMap.get("ROW18")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "出口交货值累计同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW19") == null ? "":theMap.get("ROW19")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "出口交货值本月同比增减%点");
            theMap.put("ROW", String.valueOf(theMap.get("ROW20") == null ? "":theMap.get("ROW20")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate20() {
        editCount(true);
        log.info("task20 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200702001");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData20(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            theMap.put("LYID", "20200702001");
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("ENROL_NAME", "发电量累计");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "发电量本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "发电量累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "发电量本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "原油加工量累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "原油加工量本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "原油加工量累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "原油加工量本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW12") == null ? "":theMap.get("ROW12")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "粗钢产量累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW13") == null ? "":theMap.get("ROW13")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "粗钢产量本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW14") == null ? "":theMap.get("ROW14")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "粗钢产量累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW15") == null ? "":theMap.get("ROW15")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "粗钢产量本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW16") == null ? "":theMap.get("ROW16")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "钢材产量累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW17") == null ? "":theMap.get("ROW17")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "钢材产量本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW18") == null ? "":theMap.get("ROW18")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "钢材产量累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW19") == null ? "":theMap.get("ROW19")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "钢材产量本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW20") == null ? "":theMap.get("ROW20")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }


    @Scheduled(cron = "0 0 1 1 * ?")
    @Async("taskExecutor")
    public void dataUpdate21() {
        editCount(true);
        log.info("task21 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        String start = ippMapper.getLastDateByLyid("20200702002");
        List<HashMap<String, String>> dataList = monthMapper.findByTimeData21(start);
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        for (HashMap theMap:dataList) {
            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("MONTH_MTH_START_DATE", theMap.get("MONTH_MTH_START_DATE").toString().substring(0,10));
            theMap.put("MONTH_END_DATE", theMap.get("MONTH_END_DATE").toString().substring(0,10));
            theMap.put("ROW", String.valueOf(theMap.get("ROW1") == null ? "":theMap.get("ROW1")));
            theMap.put("LYID", "20200702002");
            theMap.put("ENROL_ID", theMap.get("DIM_ID"));
            theMap.put("ENROL_NAME", "水泥产量累计");
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "水泥产量本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW2") == null ? "":theMap.get("ROW2")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "水泥产量累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW3") == null ? "":theMap.get("ROW3")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "水泥产量本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW4") == null ? "":theMap.get("ROW4")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "平板玻璃累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW5") == null ? "":theMap.get("ROW5")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "平板玻璃本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW6") == null ? "":theMap.get("ROW6")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "平板玻璃累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW7") == null ? "":theMap.get("ROW7")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "平板玻璃本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW8") == null ? "":theMap.get("ROW8")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "十种有色金属累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW9") == null ? "":theMap.get("ROW9")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "十种有色金属本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW10") == null ? "":theMap.get("ROW10")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "十种有色金属累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW11") == null ? "":theMap.get("ROW11")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "十种有色金属本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW12") == null ? "":theMap.get("ROW12")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "汽车累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW13") == null ? "":theMap.get("ROW13")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "汽车本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW14") == null ? "":theMap.get("ROW14")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "汽车累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW15") == null ? "":theMap.get("ROW15")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "汽车本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW16") == null ? "":theMap.get("ROW16")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "其中：轿车累计");
            theMap.put("ROW", String.valueOf(theMap.get("ROW17") == null ? "":theMap.get("ROW17")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "其中：轿车本月");
            theMap.put("ROW", String.valueOf(theMap.get("ROW18") == null ? "":theMap.get("ROW18")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "其中：轿车累计同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW19") == null ? "":theMap.get("ROW19")));
            ippMapper.insertToDataMove(theMap);

            theMap.put("ID", UUID.randomUUID().toString().replace("-", ""));
            theMap.put("ENROL_NAME", "其中：轿车本月同比增长");
            theMap.put("ROW", String.valueOf(theMap.get("ROW20") == null ? "":theMap.get("ROW20")));
            ippMapper.insertToDataMove(theMap);
        }
        editCount(false);
    }


    /**
     * 插入指标池
     * @param lyid 来源id
     * @param zbmc 指标名称
     */
    private void insertPoolZb(String zbmc, String lyid){
        PoolZb poolZb = new PoolZb();
        poolZb.setId(UUID.randomUUID().toString().replace("-", ""));
        poolZb.setZbmc(zbmc);
        String zbfl = poolZbService.selectZbflByLyId(lyid);
        poolZb.setZbfl(zbfl);
        poolZb.setSourceMark(GyjcConstant.YW_SYSTEM_DATA_MOVE);
        poolZbService.insertPoolZb(poolZb);
    }

/*
    @Scheduled(cron = "30 5/1 11 3 7 ?")
    public void dataUpdatetest1() throws InterruptedException {
        count++;
        log.info("task1 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        List listA = monthMapper.findByTimeDataOne("2020-03-01");
        log.info("listA:{}", listA.size());
        Thread.sleep(7000);
    }

    @Scheduled(cron = "31 05/1 11 3 7 ?")
    public void dataUpdatetest2() throws InterruptedException {
        count++;
        log.info("task2 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        List listB = monthMapper.findByTimeDataOne("2020-01-01");
        log.info("listB:{}", listB.size());
        Thread.sleep(7000);
    }

    @Scheduled(cron = "31 5/1 11 3 7 ?")
    public void dataUpdatetest3() throws InterruptedException {
        count++;
        log.info("task3 IS RUNNING!!!!!!!!!!!{}", LocalDateTime.now().toString());
        log.info("--{}--{}",Thread.currentThread().getName(),dataList.size());
        List listC = monthMapper.findByTimeDataOne("2019-01-01");
        log.info("listC:{}", listC.size());
        Thread.sleep(7000);
    }*/

}
