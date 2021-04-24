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
public class SatelliteEarthStation extends Model<SatelliteEarthStation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 保护清单识别号
     */
    private String bhqdsbh;
    /**
     * 用户单位名称
     */
    private String yhdwmc;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 双向通信地球站
     */
    private String sxtxdqz;
    /**
     * 单收地球站
     */
    private String dsdqz;
    /**
     * 广播电视地球站
     */
    private String gbdsdqz;
    /**
     * 微波站
     */
    private String wbz;
    /**
     * 射电天文台
     */
    private String sdtwt;
    /**
     * 其他
     */
    private String qt;
    /**
     * 最低发射频率范围
     */
    private String zdfsplfw;
    /**
     * 最高发射频率范围
     */
    private String zgfsplfw;
    /**
     * 最低接收频率范围
     */
    private String zdjsplfw;
    /**
     * 最高接收频率范围
     */
    private String zgjsplfw;
    /**
     * 空间无线电台名称
     */
    private String kjwxdzmc;
    /**
     * 位置
     */
    private String wz;
    /**
     * 东经坐标
     */
    private String djzb;
    /**
     * 北纬坐标
     */
    private String bwzb;
    /**
     * 台站信息确认
     */
    private String tzxxqr;
    /**
     * 确认时间
     */
    private String qrsj;
    /**
     * 核验人
     */
    private String jyr;
    /**
     * 备注
     */
    private String bz;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建人
     */
    private String createUserName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
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
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBhqdsbh() {
        return bhqdsbh;
    }

    public void setBhqdsbh(String bhqdsbh) {
        this.bhqdsbh = bhqdsbh;
    }

    public String getYhdwmc() {
        return yhdwmc;
    }

    public void setYhdwmc(String yhdwmc) {
        this.yhdwmc = yhdwmc;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getSxtxdqz() {
        return sxtxdqz;
    }

    public void setSxtxdqz(String sxtxdqz) {
        this.sxtxdqz = sxtxdqz;
    }

    public String getDsdqz() {
        return dsdqz;
    }

    public void setDsdqz(String dsdqz) {
        this.dsdqz = dsdqz;
    }

    public String getGbdsdqz() {
        return gbdsdqz;
    }

    public void setGbdsdqz(String gbdsdqz) {
        this.gbdsdqz = gbdsdqz;
    }

    public String getWbz() {
        return wbz;
    }

    public void setWbz(String wbz) {
        this.wbz = wbz;
    }

    public String getSdtwt() {
        return sdtwt;
    }

    public void setSdtwt(String sdtwt) {
        this.sdtwt = sdtwt;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getZdfsplfw() {
        return zdfsplfw;
    }

    public void setZdfsplfw(String zdfsplfw) {
        this.zdfsplfw = zdfsplfw;
    }

    public String getZgfsplfw() {
        return zgfsplfw;
    }

    public void setZgfsplfw(String zgfsplfw) {
        this.zgfsplfw = zgfsplfw;
    }

    public String getZdjsplfw() {
        return zdjsplfw;
    }

    public void setZdjsplfw(String zdjsplfw) {
        this.zdjsplfw = zdjsplfw;
    }

    public String getZgjsplfw() {
        return zgjsplfw;
    }

    public void setZgjsplfw(String zgjsplfw) {
        this.zgjsplfw = zgjsplfw;
    }

    public String getKjwxdzmc() {
        return kjwxdzmc;
    }

    public void setKjwxdzmc(String kjwxdzmc) {
        this.kjwxdzmc = kjwxdzmc;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
    }

    public String getDjzb() {
        return djzb;
    }

    public void setDjzb(String djzb) {
        this.djzb = djzb;
    }

    public String getBwzb() {
        return bwzb;
    }

    public void setBwzb(String bwzb) {
        this.bwzb = bwzb;
    }

    public String getTzxxqr() {
        return tzxxqr;
    }

    public void setTzxxqr(String tzxxqr) {
        this.tzxxqr = tzxxqr;
    }

    public String getQrsj() {
        return qrsj;
    }

    public void setQrsj(String qrsj) {
        this.qrsj = qrsj;
    }

    public String getJyr() {
        return jyr;
    }

    public void setJyr(String jyr) {
        this.jyr = jyr;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SatelliteEarthStation{" +
        "id=" + id +
        ", bhqdsbh=" + bhqdsbh +
        ", yhdwmc=" + yhdwmc +
        ", lxr=" + lxr +
        ", lxdh=" + lxdh +
        ", sxtxdqz=" + sxtxdqz +
        ", dsdqz=" + dsdqz +
        ", gbdsdqz=" + gbdsdqz +
        ", wbz=" + wbz +
        ", sdtwt=" + sdtwt +
        ", qt=" + qt +
        ", zdfsplfw=" + zdfsplfw +
        ", zgfsplfw=" + zgfsplfw +
        ", zdjsplfw=" + zdjsplfw +
        ", zgjsplfw=" + zgjsplfw +
        ", kjwxdzmc=" + kjwxdzmc +
        ", wz=" + wz +
        ", djzb=" + djzb +
        ", bwzb=" + bwzb +
        ", tzxxqr=" + tzxxqr +
        ", qrsj=" + qrsj +
        ", jyr=" + jyr +
        ", bz=" + bz +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateTime=" + updateTime +
        "}";
    }
}
