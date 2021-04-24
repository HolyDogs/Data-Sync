package com.thinvent.entity.secure;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2021-01-28
 */
public class NetworkKeyNodesLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    private String id;
    /**
     * 触发时间
     */
    private Date triggerDate;
    /**
     * 警报分类
     */
    private String alarmCategory;
    /**
     * 事件类型
     */
    private String eventType;
    /**
     * 警报类型
     */
    private String eventTypeCode;
    /**
     * 警报名称
     */
    private String alarmName;
    /**
     * 行为名称
     */
    private String behaviorName;
    /**
     * 威胁等级
     */
    private String threatLevel;

    /**
     * 源IP
     */
    private String srcIp;
    /**
     * 目标IP
     */
    private String dstIp;
    /**
     * 源端口
     */
    private String srcPort;
    /**
     * 目的端口
     */
    private String dstPort;
    /**
     * 源国家名称
     */
    private String srcCountryName;
    /**
     * 源省份名称
     */
    private String srcProvinceName;
    /**
     * 源城市名称
     */
    private String srcCityName;
    private String xRealIp;
    private String xForwardedFor;
    private String payload;
    private String wlProxyClientIp;
    private String host;
    /**
     * 链路ID
     */
    private Long linkId;
    /**
     * 警报条件
     */
    private String alarmContition;
    /**
     * 链路名称
     */
    private String deviceidLinkid;
    /**
     * 前端设备
     */
    private String deviceId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(Date triggerDate) {
        this.triggerDate = triggerDate;
    }

    public String getAlarmCategory() {
        return alarmCategory;
    }

    public void setAlarmCategory(String alarmCategory) {
        this.alarmCategory = alarmCategory;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public String getSrcCountryName() {
        return srcCountryName;
    }

    public void setSrcCountryName(String srcCountryName) {
        this.srcCountryName = srcCountryName;
    }

    public String getSrcProvinceName() {
        return srcProvinceName;
    }

    public void setSrcProvinceName(String srcProvinceName) {
        this.srcProvinceName = srcProvinceName;
    }

    public String getSrcCityName() {
        return srcCityName;
    }

    public void setSrcCityName(String srcCityName) {
        this.srcCityName = srcCityName;
    }

    public String getxRealIp() {
        return xRealIp;
    }

    public void setxRealIp(String xRealIp) {
        this.xRealIp = xRealIp;
    }

    public String getxForwardedFor() {
        return xForwardedFor;
    }

    public void setxForwardedFor(String xForwardedFor) {
        this.xForwardedFor = xForwardedFor;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getWlProxyClientIp() {
        return wlProxyClientIp;
    }

    public void setWlProxyClientIp(String wlProxyClientIp) {
        this.wlProxyClientIp = wlProxyClientIp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getAlarmContition() {
        return alarmContition;
    }

    public void setAlarmContition(String alarmContition) {
        this.alarmContition = alarmContition;
    }

    public String getDeviceidLinkid() {
        return deviceidLinkid;
    }

    public void setDeviceidLinkid(String deviceidLinkid) {
        this.deviceidLinkid = deviceidLinkid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "NetworkKeyNodesLog{" +
        "id=" + id +
        ", triggerDate=" + triggerDate +
        ", alarmCategory=" + alarmCategory +
        ", eventType=" + eventType +
        ", eventTypeCode=" + eventTypeCode +
        ", alarmName=" + alarmName +
        ", behaviorName=" + behaviorName +
        ", level=" + threatLevel +
        ", srcIp=" + srcIp +
        ", dstIp=" + dstIp +
        ", srcPort=" + srcPort +
        ", dstPort=" + dstPort +
        ", srcCountryName=" + srcCountryName +
        ", srcProvinceName=" + srcProvinceName +
        ", srcCityName=" + srcCityName +
        ", xRealIp=" + xRealIp +
        ", xForwardedFor=" + xForwardedFor +
        ", payload=" + payload +
        ", wlProxyClientIp=" + wlProxyClientIp +
        ", host=" + host +
        ", linkId=" + linkId +
        ", alarmContition=" + alarmContition +
        ", deviceidLinkid=" + deviceidLinkid +
        ", deviceId=" + deviceId +
        "}";
    }
}
