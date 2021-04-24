package com.thinvent.entity.secure;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2021-01-27
 */
public class ThreatTrapUploadRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Date importDate;
    private String result;
    private String reason;
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
        return "ThreatTrapUploadRecord{" +
        "id=" + id +
        ", importDate=" + importDate +
        ", result=" + result +
        ", reason=" + reason +
        "}";
    }
}
