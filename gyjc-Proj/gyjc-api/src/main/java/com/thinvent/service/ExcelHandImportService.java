package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.ExcelHandImport;
import com.thinvent.entity.HandImportRecord;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2020-09-24
 */
public interface ExcelHandImportService extends IService<ExcelHandImport> {

    /**
     * 解析手动导入的excel
     * @param fileType 文件类型
     * @param inputStream 文件输入流
     * @return 解析后的结果
     * @throws IOException
     */
    List<HashMap> importExcel(String fileType, InputStream inputStream) throws Exception;

    /**
     * 处理指标数据
     * @param zbfl 指标分类
     * @param hashMap 数据集合
     * @param handImportRecord 手动导入记录
     */
    void handleZbData(String zbfl, HashMap hashMap, HandImportRecord handImportRecord);

    /**
     * 获取指标分类list
     * @return
     */
    List<Object> getZbflSelectList();

    /**
     * 返回手动导入源数据
     * @param pageNum 页数
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param dqmc 地区名称
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return 分页数据
     */
    PageInfo<ExcelHandImport> getDataList(int pageNum, String zbmc, String zbfl, String dqmc, String startTime, String endTime);

    /**
     * 时间字典的保存
     * @param sjId
     * @param year
     * @param month
     */
    public void saveIfNotExistDateDic(String sjId, String year, String month);
}
