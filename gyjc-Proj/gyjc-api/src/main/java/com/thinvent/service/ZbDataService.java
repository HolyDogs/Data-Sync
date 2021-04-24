package com.thinvent.service;

import com.thinvent.entity.ExcelImportRecord;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ZbDataService {

    /**
     * 表格导入
     * @param fileName 文件名
     * @param title 表头
     * @param data 数据
     * @param first 是否第一次导入
     * @param excelImportRecord 导入记录对象
     * @param zbKey 指标名称key
     */
    int excelImp(String fileName, List<String> title, List data, String first, ExcelImportRecord excelImportRecord, String zbKey);

    /**
     * 表格解析
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param inputStream 文件流
     * @return 返回解析后的对象
     */
    Map<String, Object> importExcel(String fileName, String fileType, InputStream inputStream);

    /**
     * 将待定指标作为新指标插入
     * @param zbIds 作为新指标的待定指标id
     */
    void insertNewZb(String[] zbIds);

    /**
     * 将当前待定指标作为旧指标导入
     * @param currentZbId 当前指标id
     * @param jzbId 旧指标id
     */
    void insertOldZb(String currentZbId, String jzbId);

    /**
     * 获取指标该有的key
     * @param zbfl 指标分类
     * @param zbmc 指标名称
     * @return
     */
    String getCurrentKey(String zbfl, String zbmc);
}
