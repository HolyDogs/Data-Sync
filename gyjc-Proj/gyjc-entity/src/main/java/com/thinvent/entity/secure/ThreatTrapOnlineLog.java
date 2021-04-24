package com.thinvent.entity.secure;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yb
 * @since 2021-01-26
 */
public class ThreatTrapOnlineLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID

     */
    private String id;
    /**
     * 创建时间-精确到秒

     */
    private Date createTimeSecond;
    /**
     * 创建时间

     */
    private Date createTime;
    /**
     * 创建时间-精确到小时

     */
    private Date createTimeHour;
    /**
     * 创建时间-精确到天

     */
    private Date createTimeDay;
    /**
     * 执行任务花费的时间，秒

     */
    private String taskExecuteTime;
    /**
     * 日志类型为任务执行时，此字段为任务拉取日志的 id

     */
    private String confirmedId;
    /**
     * 日志类型（0 : 攻击日志
1 : 告警日志
2 : 告警事件
3 : 状态上传
4 : 任务拉取
5 : 任务执行）
     */
    private Integer logType;
    /**
     * 是否通过

     */
    private String passed;
    /**
     * 请求数据

     */
    private String request;
    /**
     * 请求创建时间

     */
    private Date requestCreateTime;
    private String instruction;
    /**
     * 日志等级（0 : 未知
1 : 低
2 : 中
3 : 高）
     */
    private Integer levelId;
    private String productId;
    /**
     * 协议名称

     */
    private String protocolId;
    /**
     * 被攻击者(设备) ip

     */
    private String dstIp;
    /**
     * 被攻击者(设备) 纬度

     */
    private Double dstLatitude;
    /**
     * 被攻击者(设备) 经度

     */
    private Double dstLongitude;
    /**
     * log_type 为 2 才有,
攻击事件结束时间
     */
    private Date eventEndTime;
    /**
     * log_type 为 2 才有, 攻击事件 ip
     */
    private String eventIp;
    /**
     * log_type 为 2 才有,
攻击事件开始时间
     */
    private Date eventStartTime;
    /**
     * 攻击者 ip 属于哪个国家

     */
    private String srcCountry;
    /**
     * 攻击者 ip
     */
    private String srcIp;
    /**
     * 攻击者纬度

     */
    private Double srcLatitude;
    /**
     * 攻击者经度

     */
    private Double srcLongitude;
    /**
     * 攻击者 ip 所属运营商名称

     */
    private String srcIsp;
    /**
     * 保存时添加额外信息标志, 如果是 0, 保存的时候添加一些额外信息,
        是 1 不添加, 是-1 表示添加信息时出错
     */
    private String appendInfoSymbol;

    /**
     * 协议名称
     */
    private String protocol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTimeSecond() {
        return createTimeSecond;
    }

    public void setCreateTimeSecond(Date createTimeSecond) {
        this.createTimeSecond = createTimeSecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTimeHour() {
        return createTimeHour;
    }

    public void setCreateTimeHour(Date createTimeHour) {
        this.createTimeHour = createTimeHour;
    }

    public Date getCreateTimeDay() {
        return createTimeDay;
    }

    public void setCreateTimeDay(Date createTimeDay) {
        this.createTimeDay = createTimeDay;
    }

    public String getTaskExecuteTime() {
        return taskExecuteTime;
    }

    public void setTaskExecuteTime(String taskExecuteTime) {
        this.taskExecuteTime = taskExecuteTime;
    }

    public String getConfirmedId() {
        return confirmedId;
    }

    public void setConfirmedId(String confirmedId) {
        this.confirmedId = confirmedId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Date getRequestCreateTime() {
        return requestCreateTime;
    }

    public void setRequestCreateTime(Date requestCreateTime) {
        this.requestCreateTime = requestCreateTime;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public Double getDstLatitude() {
        return dstLatitude;
    }

    public void setDstLatitude(Double dstLatitude) {
        this.dstLatitude = dstLatitude;
    }

    public Double getDstLongitude() {
        return dstLongitude;
    }

    public void setDstLongitude(Double dstLongitude) {
        this.dstLongitude = dstLongitude;
    }

    public Date getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Date eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventIp() {
        return eventIp;
    }

    public void setEventIp(String eventIp) {
        this.eventIp = eventIp;
    }

    public Date getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Date eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getSrcCountry() {
        return srcCountry;
    }

    public void setSrcCountry(String srcCountry) {
        this.srcCountry = srcCountry;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public Double getSrcLatitude() {
        return srcLatitude;
    }

    public void setSrcLatitude(Double srcLatitude) {
        this.srcLatitude = srcLatitude;
    }

    public Double getSrcLongitude() {
        return srcLongitude;
    }

    public void setSrcLongitude(Double srcLongitude) {
        this.srcLongitude = srcLongitude;
    }

    public String getSrcIsp() {
        return srcIsp;
    }

    public void setSrcIsp(String srcIsp) {
        this.srcIsp = srcIsp;
    }

    public String getAppendInfoSymbol() {
        return appendInfoSymbol;
    }

    public void setAppendInfoSymbol(String appendInfoSymbol) {
        this.appendInfoSymbol = appendInfoSymbol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "ThreatTrapOnlineLog{" +
        "id=" + id +
        ", createTimeSecond=" + createTimeSecond +
        ", createTime=" + createTime +
        ", createTimeHour=" + createTimeHour +
        ", createTimeDay=" + createTimeDay +
        ", taskExecuteTime=" + taskExecuteTime +
        ", confirmedId=" + confirmedId +
        ", logType=" + logType +
        ", passed=" + passed +
        ", request=" + request +
        ", requestCreateTime=" + requestCreateTime +
        ", instruction=" + instruction +
        ", levelId=" + levelId +
        ", productId=" + productId +
        ", protocolId=" + protocolId +
        ", dstIp=" + dstIp +
        ", dstLatitude=" + dstLatitude +
        ", dstLongitude=" + dstLongitude +
        ", eventEndTime=" + eventEndTime +
        ", eventIp=" + eventIp +
        ", eventStartTime=" + eventStartTime +
        ", srcCountry=" + srcCountry +
        ", srcIp=" + srcIp +
        ", srcLatitude=" + srcLatitude +
        ", srcLongitude=" + srcLongitude +
        ", srcIsp=" + srcIsp +
        ", appendInfoSymbol=" + appendInfoSymbol +
        "}";
    }
}
