package com.thinvent.entity.secure;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2021-01-05
 */
public class ActiveConnect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;
    /**
     * 源IP类型
     */
    private Integer srciptype;
    /**
     * 目的IP类型
     */
    private Integer dstiptype;
    /**
     * 源IP
     */
    private String srcip;
    /**
     * 源端口
     */
    private String srcport;
    /**
     * 源IP单位名称
     */
    private String srcdwmc;
    /**
     * 目的IP
     */
    private String dstip;
    /**
     * 目的端口
     */
    private String dstport;
    /**
     * 目的1P单位名称
     */
    private String dstdwmc;
    /**
     * 上行流量，单位：b
     */
    private Long ultraffic;
    /**
     * 下行流量，单位：b
     */
    private Long dltraffic;
    /**
     * 上行包数量，单位:个
     */
    private Long ulpacktes;
    /**
     * 下行包数量，单位:个
     */
    private Long dlpacktes;
    /**
     * 设备端位置
 1：源IP是设备，2：目 的IP是设备，3：两端 均是设备
     */
    private Integer isdevice;
    /**
     * 平台端位置
 1：源IP是平台，2：目 的IP是平台，3：两端  均是平台
     */
    private Integer isplat;
    /**
     * 传输层协议类型 
1: tcp； 2: udp
     */
    private Integer proto;
    /**
     * 应用层协议类型
     */
    private String protocol;
    /**
     * 涉及终端厂商
     */
    private String manufacturer;
    /**
     * 终端类别
     */
    private String assettype;
    /**
     * 涉及平台名称
     */
    private String platform;
    /**
     * 通信开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date starttime;
    /**
     * 通信结束时间
     */
    private Date endtime;
    /**
     * 备注
     */
    private String remarks;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSrciptype() {
        return srciptype;
    }

    public void setSrciptype(Integer srciptype) {
        this.srciptype = srciptype;
    }

    public Integer getDstiptype() {
        return dstiptype;
    }

    public void setDstiptype(Integer dstiptype) {
        this.dstiptype = dstiptype;
    }

    public String getSrcip() {
        return srcip;
    }

    public void setSrcip(String srcip) {
        this.srcip = srcip;
    }

    public String getSrcport() {
        return srcport;
    }

    public void setSrcport(String srcport) {
        this.srcport = srcport;
    }

    public String getSrcdwmc() {
        return srcdwmc;
    }

    public void setSrcdwmc(String srcdwmc) {
        this.srcdwmc = srcdwmc;
    }

    public String getDstip() {
        return dstip;
    }

    public void setDstip(String dstip) {
        this.dstip = dstip;
    }

    public String getDstport() {
        return dstport;
    }

    public void setDstport(String dstport) {
        this.dstport = dstport;
    }

    public String getDstdwmc() {
        return dstdwmc;
    }

    public void setDstdwmc(String dstdwmc) {
        this.dstdwmc = dstdwmc;
    }

    public Long getUltraffic() {
        return ultraffic;
    }

    public void setUltraffic(Long ultraffic) {
        this.ultraffic = ultraffic;
    }

    public Long getDltraffic() {
        return dltraffic;
    }

    public void setDltraffic(Long dltraffic) {
        this.dltraffic = dltraffic;
    }

    public Long getUlpacktes() {
        return ulpacktes;
    }

    public void setUlpacktes(Long ulpacktes) {
        this.ulpacktes = ulpacktes;
    }

    public Long getDlpacktes() {
        return dlpacktes;
    }

    public void setDlpacktes(Long dlpacktes) {
        this.dlpacktes = dlpacktes;
    }

    public Integer getIsdevice() {
        return isdevice;
    }

    public void setIsdevice(Integer isdevice) {
        this.isdevice = isdevice;
    }

    public Integer getIsplat() {
        return isplat;
    }

    public void setIsplat(Integer isplat) {
        this.isplat = isplat;
    }

    public Integer getProto() {
        return proto;
    }

    public void setProto(Integer proto) {
        this.proto = proto;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAssettype() {
        return assettype;
    }

    public void setAssettype(String assettype) {
        this.assettype = assettype;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ActiveConnect{" +
        "id=" + id +
        ", srciptype=" + srciptype +
        ", dstiptype=" + dstiptype +
        ", srcip=" + srcip +
        ", srcport=" + srcport +
        ", srcdwmc=" + srcdwmc +
        ", dstip=" + dstip +
        ", dstport=" + dstport +
        ", dstdwmc=" + dstdwmc +
        ", ultraffic=" + ultraffic +
        ", dltraffic=" + dltraffic +
        ", ulpacktes=" + ulpacktes +
        ", dlpacktes=" + dlpacktes +
        ", isdevice=" + isdevice +
        ", isplat=" + isplat +
        ", proto=" + proto +
        ", protocol=" + protocol +
        ", manufacturer=" + manufacturer +
        ", assettype=" + assettype +
        ", platform=" + platform +
        ", starttime=" + starttime +
        ", endtime=" + endtime +
        ", remarks=" + remarks +
        "}";
    }
}
