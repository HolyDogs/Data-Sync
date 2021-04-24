package com.thinvent.entity.secure;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xff
 * @since 2021-01-25
 */
public class BasicPropertyOnline implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * ip
     */
    private String ip;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 类型
     */
    private String type;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 厂商
     */
    private String manufacturer;
    /**
     * 型号
     */
    private String model;
    /**
     * 运营商
     */
    private String operators;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 资产信息
     */
    private String info;
    /**
     * 所属企业
     */
    private String belongCompany;
    /**
     * 发现时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date foundTime;
    /**
     * 漏洞编号
     */
    private String leakId;
    /**
     * 地区名称
     */
    private String areaId;

    /**
     * 漏洞描述
     */
    private String leakDesc;

    public String getLeakDesc() {
        return leakDesc;
    }

    public void setLeakDesc(String leakDesc) {
        this.leakDesc = leakDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBelongCompany() {
        return belongCompany;
    }

    public void setBelongCompany(String belongCompany) {
        this.belongCompany = belongCompany;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public String getLeakId() {
        return leakId;
    }

    public void setLeakId(String leakId) {
        this.leakId = leakId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "BasicPropertyOnline{" +
        "id=" + id +
        ", ip=" + ip +
        ", port=" + port +
        ", type=" + type +
        ", lng=" + lng +
        ", lat=" + lat +
        ", manufacturer=" + manufacturer +
        ", model=" + model +
        ", operators=" + operators +
        ", province=" + province +
        ", city=" + city +
        ", info=" + info +
        ", belongCompany=" + belongCompany +
        ", foundTime=" + foundTime +
        ", leakId=" + leakId +
        ", areaId=" + areaId +
        "}";
    }
}
