package com.thinvent.entity.secure;

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
public class LeakInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String leakName;
    private Date foundTime;
    private String leakLevel;
    private String simpleName;
    private String leakDesc;
    private Date insertTime;


    public String getLeakName() {
        return leakName;
    }

    public void setLeakName(String leakName) {
        this.leakName = leakName;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public String getLeakLevel() {
        return leakLevel;
    }

    public void setLeakLevel(String leakLevel) {
        this.leakLevel = leakLevel;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getLeakDesc() {
        return leakDesc;
    }

    public void setLeakDesc(String leakDesc) {
        this.leakDesc = leakDesc;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return "LeakInfo{" +
        "leakName=" + leakName +
        ", foundTime=" + foundTime +
        ", leakLevel=" + leakLevel +
        ", simpleName=" + simpleName +
        ", leakDesc=" + leakDesc +
        ", insertTime=" + insertTime +
        "}";
    }
}
