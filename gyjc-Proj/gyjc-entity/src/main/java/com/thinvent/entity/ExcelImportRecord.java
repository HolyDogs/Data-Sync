package com.thinvent.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2020-09-08
 */
public class ExcelImportRecord extends Model<ExcelImportRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 导入时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importDate;
    /**
     * 更新数据条数
     */
    private int updateNum;
    /**
     * 插入数据条数
     */
    private int insertNum;
    /**
     * 新加待定指标条数
     */
    private int underminedNum;
    /**
     * 导入是否成功
     */
    private String result;
    /**
     * 导入失败原因
     */
    private String reason;
    /**
     * 导入表格名
     */
    private String excelName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }

    public int getInsertNum() {
        return insertNum;
    }

    public void setInsertNum(int insertNum) {
        this.insertNum = insertNum;
    }

    public int getUnderminedNum() {
        return underminedNum;
    }

    public void setUnderminedNum(int underminedNum) {
        this.underminedNum = underminedNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExcelImportRecord{" +
        "id=" + id +
        ", importDate=" + importDate +
        ", updateNum=" + updateNum +
        ", insertNum=" + insertNum +
        ", underminedNum=" + underminedNum +
        ", result=" + result +
        ", reason=" + reason +
        ", excelName=" + excelName +
        "}";
    }
}
