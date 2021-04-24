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
 * @since 2020-11-12
 */
public class SSystemLog extends Model<SSystemLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 登录名称
     */
    private String userName;
    /**
     * 用户名称
     */
    private String fullName;
    /**
     * 日志
     */
    private String log;
    /**
     * 日志记录时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logTime;
    /**
     * 更新人id
     */
    private String updateUserId;
    /**
     * 更新人
     */
    private String updateUserName;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除（0-未删除,1-删除）
     */
    private Integer isDelete;

    /**
     * 是否为异常，0-否，1-是
     */
    private Integer isException;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getIsException() {
        return isException;
    }

    public void setIsException(Integer isException) {
        this.isException = isException;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SSystemLog{" +
        "id=" + id +
        ", clientIp=" + clientIp +
        ", userId=" + userId +
        ", userName=" + userName +
        ", log=" + log +
        ", logTime=" + logTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateTime=" + updateTime +
        ", isDelete=" + isDelete +
        "}";
    }
}
