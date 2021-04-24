package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-12-09
 */
public class HandImportRecord extends Model<HandImportRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 导入时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date importDate;
    /**
     * 更新指标条数
     */
    private int updateNum;
    /**
     * 插入指标条数
     */
    private int insertNum;
    /**
     * 导入结果
     */
    private String result;
    /**
     * 导入文件名
     */
    private String excelName;
    /**
     * 失败原因
     */
    private String reason;


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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HandImportRecord{" +
        "id=" + id +
        ", importDate=" + importDate +
        ", updateNum=" + updateNum +
        ", insertNum=" + insertNum +
        ", result=" + result +
        ", excelName=" + excelName +
        ", reason=" + reason +
        "}";
    }
}
