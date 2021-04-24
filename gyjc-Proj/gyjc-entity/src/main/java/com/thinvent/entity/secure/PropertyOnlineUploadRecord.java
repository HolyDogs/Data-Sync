package com.thinvent.entity.secure;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2021-02-05
 */
public class PropertyOnlineUploadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 导入时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date importDate;

    /**
     * 导入结果
     */
    private String result;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 导入excel名字
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
    public String toString() {
        return "PropertyOnlineUploadRecord{" +
        "id=" + id +
        ", importDate=" + importDate +
        ", result=" + result +
        ", reason=" + reason +
        ", excelName=" + excelName +
        "}";
    }
}
