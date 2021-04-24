package com.thinvent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.HandImportRecord;
import com.thinvent.exception.ExcelHandleException;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.ExcelHandImportService;
import com.thinvent.service.HandImportRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * 手动导入Controller
 * @author xufeng
 * @version 1.0
 * @date 2020/9/24
 **/
@RestController
@RequestMapping(value = "/gyProj")
public class ManualImportController {

    @Autowired
    private ExcelHandImportService excelHandImportService;

    @Autowired
    private HandImportRecordService handImportRecordService;

    @PostMapping(value = "/manualUpload")
    public GenericResponseModel manualUpload(@RequestParam("file") MultipartFile file) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        HandImportRecord handImportRecord = new HandImportRecord();
        try {

            String origiName = file.getOriginalFilename();
            if (StringUtils.isEmpty(origiName)) {
                return genericResponseModel;
            }
            //文件名
            String fileName = origiName.substring(0, origiName.indexOf("."));
            //文件类型
            String fileType = origiName.substring(origiName.indexOf(".") + 1);
            //导入文件名
            handImportRecord.setExcelName(fileName);
            List<HashMap> dataList = excelHandImportService.importExcel(fileType, file.getInputStream());
            List<String> dateStrs = new ArrayList<>();
            for (HashMap map:dataList) {
                List titles = (List) MapUtils.getObject(map, "title");
                if(titles!=null&&titles.size()>1){
                    for (int i=1;i<titles.size();i++) {
                        dateStrs.add(titles.get(i).toString());
                    }
                }
            }
            HashSet set = new HashSet<>(dateStrs);
            dateStrs.clear();
            dateStrs.addAll(set);
            if(dateStrs.size() > 0){
                for(String dateStr:dateStrs) {
                    String[] dateArr = dateStr.split("-");
                    String year = "";
                    String month = "";
                    if (dateArr.length > 1) {
                        //年份
                        year = dateArr[0];
                        //月份
                        month = dateArr[1];
                    }
                    String sjid = "M-" + year + month;
                    excelHandImportService.saveIfNotExistDateDic(sjid, year, month);
                }
            }
            for (HashMap map:dataList) {
                excelHandImportService.handleZbData(fileName, map, handImportRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
            genericResponseModel.setErrCode(204);
            genericResponseModel.setErrMsg("文件上传失败");
            if (e instanceof ExcelHandleException) {
                genericResponseModel.setErrMsg(e.getMessage());
            }
            handImportRecord.setResult("导入失败");
            //导入时间
            handImportRecord.setImportDate(new Date());
            //导入失败原因
            handImportRecord.setReason(e.getMessage());
            handImportRecordService.save(handImportRecord);
            return genericResponseModel;
        }
        handImportRecord.setResult("导入成功");
        handImportRecord.setImportDate(new Date());
        //保存记录
        handImportRecordService.save(handImportRecord);
        return genericResponseModel;
    }

    @GetMapping("/getHandRecordList")
    public GenericResponseModel getHandRecordList(@RequestParam("pageNum") int pageNum
            , @RequestParam(value = "excelName", required = false)String excelName) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        QueryWrapper<HandImportRecord> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(excelName)) {
            queryWrapper.like("EXCEL_NAME", excelName);
        }
        queryWrapper.orderByDesc("IMPORT_DATE");
        PageHelper.startPage(pageNum, 10);
        List<HandImportRecord> handImportRecordList = handImportRecordService.list(queryWrapper);
        genericResponseModel.setData(new PageInfo<>(handImportRecordList));
        return genericResponseModel;
    }
}
