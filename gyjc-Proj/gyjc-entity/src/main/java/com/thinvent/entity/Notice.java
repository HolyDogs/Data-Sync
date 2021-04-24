package com.thinvent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2021-01-28
 */
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 公告主题

     */
    private String noticeSubject;
    /**
     * 公告内容

     */
    private String noticeContent;
    /**
     * 公告来源

     */
    private String noticeSource;
    /**
     * 公告类型：
GZDT-工作动态
TZGG-通知公告
ZCJD-政策解读
     */
    private String noticeType;
    /**
     * 发布状态：
XZ-新增
DFB-待发布
YFB-已发布
     */
    private String publishStatus;
    /**
     * 发布人ID

     */
    private String publishUserId;
    /**
     * 发布时间

     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;
    /**
     * 排序

     */
    private Long sorted;
    /**
     * 点击量

     */
    private Long clickCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeSubject() {
        return noticeSubject;
    }

    public void setNoticeSubject(String noticeSubject) {
        this.noticeSubject = noticeSubject;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeSource() {
        return noticeSource;
    }

    public void setNoticeSource(String noticeSource) {
        this.noticeSource = noticeSource;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getSorted() {
        return sorted;
    }

    public void setSorted(Long sorted) {
        this.sorted = sorted;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "Notice{" +
        "id=" + id +
        ", noticeSubject=" + noticeSubject +
        ", noticeContent=" + noticeContent +
        ", noticeSource=" + noticeSource +
        ", noticeType=" + noticeType +
        ", publishStatus=" + publishStatus +
        ", publishUserId=" + publishUserId +
        ", publishTime=" + publishTime +
        ", sorted=" + sorted +
        ", clickcount=" + clickCount +
        "}";
    }
}
