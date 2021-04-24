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
 * @author yb
 * @since 2020-11-27
 */
public class ApiWatcher extends Model<ApiWatcher> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String jktgf;
    private String jkdyf;
    private String result;
    private String resultLog;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dyTime;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJktgf() {
        return jktgf;
    }

    public void setJktgf(String jktgf) {
        this.jktgf = jktgf;
    }

    public String getJkdyf() {
        return jkdyf;
    }

    public void setJkdyf(String jkdyf) {
        this.jkdyf = jkdyf;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultLog() {
        return resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }

    public Date getDyTime() {
        return dyTime;
    }

    public void setDyTime(Date dyTime) {
        this.dyTime = dyTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApiWatcher{" +
        "id=" + id +
        ", jktgf=" + jktgf +
        ", jkdyf=" + jkdyf +
        ", result=" + result +
        ", resultLog=" + resultLog +
        ", dyTime=" + dyTime +
        "}";
    }
}
