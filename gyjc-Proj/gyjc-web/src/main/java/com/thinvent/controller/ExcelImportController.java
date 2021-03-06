package com.thinvent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.*;
import com.thinvent.entity.secure.BasicPropertyOnline;
import com.thinvent.entity.secure.PropertyOnlineUploadRecord;
import com.thinvent.entity.secure.ThreatTrapOnlineLog;
import com.thinvent.entity.secure.ThreatTrapUploadRecord;
import com.thinvent.exception.ExcelHandleException;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.*;
import com.thinvent.service.secure.BasicPropertyOnlineService;
import com.thinvent.service.secure.PropertyOnlineUploadRecordService;
import com.thinvent.service.secure.ThreatTrapOnlineLogService;
import com.thinvent.service.secure.ThreatTrapUploadRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author xufeng
 * @version 1.0
 * @date 2020/9/4 15:14
 **/
@RestController
@RequestMapping(value = "/gyProj")
@Slf4j
public class ExcelImportController {

    @Autowired
    private ThreatTrapUploadRecordService threatTrapUploadRecordService;

    @Autowired
    private StatisticZbService statisticZbService;

    @Autowired
    private ExcelUnderminedService excelUnderminedService;

    @Autowired
    private ExcelZbflService excelZbflService;

    @Autowired
    private ExcelImportRecordService excelImportRecordService;

    @Autowired
    private ZbDataService zbDataService;

    @Autowired
    private DicStatisticZbService dicStatisticZbService;

    @Autowired
    private ThreatTrapOnlineLogService threatTrapOnlineLogService;

    @Autowired
    private BasicPropertyOnlineService basicPropertyOnlineService;

    @Autowired
    private PropertyOnlineUploadRecordService propertyOnlineUploadRecordService;

    //??????????????????????????????
    private static int is_upload = 0;

    //????????????????????????
    private static int property_upload = 0;

    @PostMapping("/upload")
    public GenericResponseModel genericResponseModel(@RequestParam("file") MultipartFile file){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);

        List<List<String>> pageData = new ArrayList<>();

        String origiName = file.getOriginalFilename();
        if (StringUtils.isEmpty(origiName)) {
            return genericResponseModel;
        }
        String fileName = origiName.substring(0, origiName.indexOf("."));
        String zblx="";
        try {
            zblx = fileName.split("-")[1];
            String fileType = origiName.substring(origiName.indexOf(".") + 1);
            String state = "";

            InputStream fi = file.getInputStream();
            HashMap excelMap = (HashMap) zbDataService.importExcel(fileName, fileType, fi);
            if(!excelMap.isEmpty()){
                state = (String) excelMap.get("state");
            }

            //????????????
            ExcelImportRecord excelImportRecord = new ExcelImportRecord();
            excelImportRecord.setId(UUID.randomUUID().toString());
            excelImportRecord.setImportDate(new Date());
            excelImportRecord.setExcelName(fileName);
            //???????????????
            String noUnint="";
            //state 0:?????? ;1:?????????;2:???????????????
            if ("0".equals(state)) {
                //?????????????????????????????????????????????????????????????????????????????????
                QueryWrapper<StatisticZb> statisticZbQueryWrapper = new QueryWrapper<>();
                statisticZbQueryWrapper.eq("ZBFL", fileName.split("-")[0]);
                List<StatisticZb> statisticZbs = statisticZbService.list(statisticZbQueryWrapper);
                String first = null;
                if (null == statisticZbs || statisticZbs.size() == 0) {
                    first = "first";
                }
                //????????????????????????????????????????????????????????????????????????????????????
                QueryWrapper<ExcelUndermined> excelUnderminedQueryWrapper = new QueryWrapper<>();
                excelUnderminedQueryWrapper.eq("EXCEL_NAME", fileName);
                excelUnderminedService.remove(excelUnderminedQueryWrapper);
                //???????????????????????????????????????????????????????????????????????????EXCEL_ZBFL???
                if ("first".equals(first)) {
                    ExcelZbfl excelZbfl = new ExcelZbfl();
                    excelZbfl.setId(UUID.randomUUID().toString().replace("-", ""));
                    excelZbfl.setCreateTime(new Date());
                    excelZbfl.setSjly("???????????????");
                    excelZbfl.setZbfl(fileName.split("-")[0]);
                    excelZbflService.save(excelZbfl);
                }
                List<String> title = (List<String>) excelMap.get("title");
                List<String> datas = (List<String>) excelMap.get("data");
                for (int j = 0; j < datas.size(); j++) {
                    List data = Arrays.asList(datas.get(j).split(","));
                    String zbmc  = (String) data.get(0);
                    String zbmcSelf = "";//??????????????????????????????
                    if(zbmc.contains("(") && zbmc.contains(")")){
                        //??????????????????????????????
                        String jldw = zbmc.substring(zbmc.lastIndexOf("(")+1,zbmc.lastIndexOf(")"));
                        if (!StringUtils.isEmpty(jldw) && jldw.getBytes("gbk").length > 20) {
                            throw new ExcelHandleException("????????????\"" + jldw + "\"??????????????????,????????????");
                        }
                        zbmcSelf = zbmc.substring(0, zbmc.lastIndexOf("("));
                    } else {
                        zbmcSelf = zbmc;
                        noUnint+=zbmcSelf+"??????????????????????????????";
                    }
                    String currentZBKey = zbDataService.getCurrentKey(fileName.split("-")[0],zbmcSelf);
                    //??????
                    if(zbDataService.excelImp(fileName, title, data, first, excelImportRecord,currentZBKey)!=0){
                        pageData.add(data);
                    }
                }

                //??????????????????
                excelImportRecord.setResult("???????????????"+noUnint);
                excelImportRecordService.save(excelImportRecord);
                if (excelImportRecord.getUnderminedNum() > 0) {
                    genericResponseModel.setErrMsg("1");
                }else {
                    genericResponseModel.setErrMsg(fileName + "???????????????"+noUnint);
                }
                HashMap hashMap = new HashMap();
                hashMap.put("title", title);
                hashMap.put("data", pageData);
                genericResponseModel.setData(hashMap);
            } else {
                //??????????????????
                excelImportRecord.setResult("????????????");
                excelImportRecord.setReason((String) excelMap.get("message"));
                excelImportRecordService.save(excelImportRecord);
                genericResponseModel.setErrMsg(fileName + "???????????????" + excelMap.get("message"));
            }
        } catch (Exception e) {
            //????????????
            ExcelImportRecord excelImportRecord = new ExcelImportRecord();
            excelImportRecord.setId(UUID.randomUUID().toString());
            excelImportRecord.setImportDate(new Date());
            excelImportRecord.setExcelName(fileName);
            excelImportRecord.setResult("????????????");
            if(StringUtils.isEmpty(zblx)){
                excelImportRecord.setReason("\""+fileName+"\"????????????????????????");
            }else{
                excelImportRecord.setReason(e.getMessage());
            }
            excelImportRecordService.save(excelImportRecord);
            e.printStackTrace();
            genericResponseModel.setErrMsg(fileName + "????????????!" + excelImportRecord.getReason());
            return genericResponseModel;
        }

        return genericResponseModel;
    }

    @GetMapping("/getUnderminedList")
    public GenericResponseModel getUnderminedList(@RequestParam("fileNameList") String[] fileNameList){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //?????????
        genericResponseModel.setErrCode(200);
        //??????wrapper
        QueryWrapper<ExcelUndermined> queryWrapper = new QueryWrapper<>();
        //in??????
        queryWrapper.in("EXCEL_NAME", Arrays.asList(fileNameList));
        //??????
        List<ExcelUndermined> excelUnderminedList = excelUnderminedService.list(queryWrapper);
        genericResponseModel.setData(excelUnderminedList);
        return genericResponseModel;
    }

    @GetMapping("/xzb")
    public GenericResponseModel insertXzb(@RequestParam("zbIds")String[] zbIds) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        zbDataService.insertNewZb(zbIds);
        return genericResponseModel;
    }

    @GetMapping("/getJzbList")
    public GenericResponseModel getJzbList(@RequestParam(value = "zbmc", required = false) String zbmc){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<DicStatisticZb> dicStatisticZbQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(zbmc)) {
            dicStatisticZbQueryWrapper.like("ZBMC", zbmc);
        }
        genericResponseModel.setData(dicStatisticZbService.list(dicStatisticZbQueryWrapper));
        return genericResponseModel;
    }

    @GetMapping("/jzb")
    public GenericResponseModel insertJzb(@RequestParam("currentZb") String currentZb
            , @RequestParam("jzb") String jzb) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        zbDataService.insertOldZb(currentZb, jzb);
        return genericResponseModel;
    }

    @GetMapping("/getRecordList")
    public GenericResponseModel getRecordList(@RequestParam("pageNum") int pageNum
            , @RequestParam(value = "excelName", required = false)String excelName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<ExcelImportRecord> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(excelName)) {
            queryWrapper.like("EXCEL_NAME", excelName);
        }
        queryWrapper.orderByDesc("IMPORT_DATE");
        PageHelper.startPage(pageNum, 10);
        List<ExcelImportRecord> excelImportRecordList = excelImportRecordService.list(queryWrapper);
        genericResponseModel.setData(new PageInfo<>(excelImportRecordList));
        return genericResponseModel;
    }

    /**
     * ????????????????????????
     * @author yb
     * @param file
     * @return
     */
    @PostMapping("/trapUpload")
    public GenericResponseModel trapUpload(@RequestParam("file") MultipartFile file){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        if(is_upload == 1){
            genericResponseModel.setErrCode(204);
            genericResponseModel.setData("?????????????????????????????????");
            return genericResponseModel;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat day = new SimpleDateFormat("yyyy/MM/dd");
        String origiName = file.getOriginalFilename();
        if (StringUtils.isEmpty(origiName)) {
            return genericResponseModel;
        }
        String fileName = origiName.substring(0, origiName.indexOf("."));
        //??????
        int i = 0;
        //????????????
        int errorCount = 0;
        //????????????
        StringBuffer errorData = new StringBuffer();
        try {
            Reader reader = new InputStreamReader(file.getInputStream(), "GBK");
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            is_upload = 1;
            for (CSVRecord record : parser) {
                i++;
                try {
                    ThreatTrapOnlineLog threatTrapOnlineLog = new ThreatTrapOnlineLog();
                    threatTrapOnlineLog.setId(UUID.randomUUID().toString().replace("-", ""));
                    threatTrapOnlineLog.setCreateTimeSecond(formatter.parse(record.get(1)));
                    threatTrapOnlineLog.setCreateTime(formatter.parse(record.get(2)));
                    threatTrapOnlineLog.setCreateTimeHour(formatter.parse(record.get(3)));
                    threatTrapOnlineLog.setCreateTimeDay(day.parse(record.get(4)));
                    threatTrapOnlineLog.setTaskExecuteTime(record.get(5));
                    threatTrapOnlineLog.setConfirmedId(record.get(6));
                    threatTrapOnlineLog.setLogType(StringUtils.isEmpty(record.get(7)) ? null : Integer.valueOf(record.get(7)));
                    threatTrapOnlineLog.setPassed(record.get(8));
                    threatTrapOnlineLog.setRequest(record.get(9));
                    threatTrapOnlineLog.setInstruction(record.get(10));
                    threatTrapOnlineLog.setLevelId(StringUtils.isEmpty(record.get(11)) ? null : Integer.valueOf(record.get(11)));
                    threatTrapOnlineLog.setProductId(record.get(12));
                    threatTrapOnlineLog.setProtocolId(record.get(13));
                    threatTrapOnlineLog.setDstIp(record.get(14));
                    threatTrapOnlineLog.setDstLatitude(StringUtils.isEmpty(record.get(15)) ? null : Double.valueOf(record.get(15)));
                    threatTrapOnlineLog.setDstLongitude(StringUtils.isEmpty(record.get(16)) ? null : Double.valueOf(record.get(16)));
                    threatTrapOnlineLog.setEventEndTime(StringUtils.isEmpty(record.get(17)) ? null : formatter.parse(record.get(17)));
                    threatTrapOnlineLog.setEventIp(record.get(18));
                    threatTrapOnlineLog.setEventStartTime(StringUtils.isEmpty(record.get(19)) ? null : formatter.parse(record.get(19)));
                    threatTrapOnlineLog.setSrcCountry(record.get(20));
                    threatTrapOnlineLog.setSrcIp(record.get(21));
                    threatTrapOnlineLog.setSrcLatitude(StringUtils.isEmpty(record.get(22)) ? null : Double.valueOf(record.get(22)));
                    threatTrapOnlineLog.setSrcLongitude(StringUtils.isEmpty(record.get(23)) ? null : Double.valueOf(record.get(23)));
                    threatTrapOnlineLog.setSrcIsp(record.get(24));
                    threatTrapOnlineLog.setAppendInfoSymbol(record.get(25));
                    threatTrapOnlineLog.setRequestCreateTime(StringUtils.isEmpty(record.get(26)) ? null : formatter.parse(record.get(26)));
                    threatTrapOnlineLogService.save(threatTrapOnlineLog);
                }catch (Exception e){
                    errorCount++;
                    errorData.append("???"+(i+1)+"?????????????????????\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        is_upload = 0;
        ThreatTrapUploadRecord threatTrapUploadRecord = new ThreatTrapUploadRecord();
        threatTrapUploadRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        threatTrapUploadRecord.setImportDate(new Date());
        threatTrapUploadRecord.setExcelName(fileName);
        if(errorCount == i){
            genericResponseModel.setErrCode(500);
            genericResponseModel.setData(errorData.toString());
            threatTrapUploadRecord.setResult("????????????");
            threatTrapUploadRecord.setReason(errorData.toString());
        }else{
            genericResponseModel.setErrCode(200);
            threatTrapUploadRecord.setResult("????????????");
            if(errorCount > 0) {
                threatTrapUploadRecord.setReason("??????????????????????????????????????????\n" + errorData.toString());
            }
        }
        threatTrapUploadRecordService.save(threatTrapUploadRecord);
        return genericResponseModel;
    }

    /**
     * ??????????????????????????????
     * @author yb
     * @param pageNum
     * @param excelName
     * @return
     */
    @GetMapping("/getTrapLogRecord")
    public GenericResponseModel getTrapLogRecord(@RequestParam("pageNum") int pageNum
            , @RequestParam(value = "excelName", required = false)String excelName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<ThreatTrapUploadRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("IMPORT_DATE");
        if (!StringUtils.isEmpty(excelName)) {
            queryWrapper.like("EXCEL_NAME", excelName);
        }
        PageHelper.startPage(pageNum, 10);
        List<ThreatTrapUploadRecord> threatTrapUploadRecordList = threatTrapUploadRecordService.list(queryWrapper);
        genericResponseModel.setData(new PageInfo<>(threatTrapUploadRecordList));
        return genericResponseModel;
    }

    /**
     * ????????????????????????
     * @param file
     * @return
     */
    @PostMapping("/propertyUpload")
    public GenericResponseModel propertyUpload(@RequestParam("file") MultipartFile file){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        if(property_upload == 1){
            genericResponseModel.setErrCode(204);
            genericResponseModel.setData("?????????????????????????????????");
            return genericResponseModel;
        }
        property_upload = 1;
        String result = basicPropertyOnlineService.uploadBasicPorpertyOnline(file);
        property_upload = 0;
        if(StringUtils.isEmpty(result)){
            genericResponseModel.setErrCode(200);
        }else{
            genericResponseModel.setErrCode(500);
        }
        return genericResponseModel;
    }


    /**
     * ??????????????????????????????
     * @author yb
     * @param pageNum
     * @param excelName
     * @return
     */
    @GetMapping("/getPropertyRecord")
    public GenericResponseModel getPropertyRecord(@RequestParam("pageNum") int pageNum
            , @RequestParam(value = "excelName", required = false)String excelName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<PropertyOnlineUploadRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("IMPORT_DATE");
        if (!StringUtils.isEmpty(excelName)) {
            queryWrapper.like("EXCEL_NAME", excelName);
        }
        PageHelper.startPage(pageNum, 10);
        List<PropertyOnlineUploadRecord> propertyOnlineUploadRecordList = propertyOnlineUploadRecordService.list(queryWrapper);
        genericResponseModel.setData(new PageInfo<>(propertyOnlineUploadRecordList));
        return genericResponseModel;
    }


}
