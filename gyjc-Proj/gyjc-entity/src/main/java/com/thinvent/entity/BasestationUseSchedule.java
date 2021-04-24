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
public class BasestationUseSchedule extends Model<BasestationUseSchedule> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;
    /**
     * 5G基站索引号
     */
    private String jzsyh;
    /**
     * 台站设置使用人
     */
    private String tzszsyr;
    /**
     * 联系人
     */
    private String lxr;
    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 台址
     */
    private String sxtxdqz;
    /**
     * 东经坐标（°）
     */
    private String djzb;
    /**
     * 北纬坐标（°）
     */
    private String bwzb;
    /**
     * 最低工作频段（MHz）
     */
    private String zdgzpd;
    /**
     * 最高工作频段（MHz）
     */
    private String zggzpd;
    /**
     * 基站类型（室内基站、室外基站）
     */
    private String jzlx;
    /**
     * 基站全球小区码
     */
    private String jzqqxqm;
    /**
     * 基站状态（规划、已建设、已许可）
     */
    private String jzzt;
    /**
     * 建站时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date jzsj;
    /**
     * 电台执照编号
     */
    private String dtzzbh;
    /**
     * 是否需要协调
     */
    private String sfxyxt;
    /**
     * 应与哪些台协调
     */
    private String yynxtxt;
    /**
     * 是否完成协调
     */
    private String sfwcxt;
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

    public String getJzsyh() {
        return jzsyh;
    }

    public void setJzsyh(String jzsyh) {
        this.jzsyh = jzsyh;
    }

    public String getTzszsyr() {
        return tzszsyr;
    }

    public void setTzszsyr(String tzszsyr) {
        this.tzszsyr = tzszsyr;
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

    public String getZdgzpd() {
        return zdgzpd;
    }

    public void setZdgzpd(String zdgzpd) {
        this.zdgzpd = zdgzpd;
    }

    public String getZggzpd() {
        return zggzpd;
    }

    public void setZggzpd(String zggzpd) {
        this.zggzpd = zggzpd;
    }

    public String getJzlx() {
        return jzlx;
    }

    public void setJzlx(String jzlx) {
        this.jzlx = jzlx;
    }

    public String getJzqqxqm() {
        return jzqqxqm;
    }

    public void setJzqqxqm(String jzqqxqm) {
        this.jzqqxqm = jzqqxqm;
    }

    public String getJzzt() {
        return jzzt;
    }

    public void setJzzt(String jzzt) {
        this.jzzt = jzzt;
    }

    public Date getJzsj() {
        return jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }

    public String getDtzzbh() {
        return dtzzbh;
    }

    public void setDtzzbh(String dtzzbh) {
        this.dtzzbh = dtzzbh;
    }

    public String getSfxyxt() {
        return sfxyxt;
    }

    public void setSfxyxt(String sfxyxt) {
        this.sfxyxt = sfxyxt;
    }

    public String getYynxtxt() {
        return yynxtxt;
    }

    public void setYynxtxt(String yynxtxt) {
        this.yynxtxt = yynxtxt;
    }

    public String getSfwcxt() {
        return sfwcxt;
    }

    public void setSfwcxt(String sfwcxt) {
        this.sfwcxt = sfwcxt;
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
        return "BasestationUseSchedule{" +
        "id=" + id +
        ", jzsyh=" + jzsyh +
        ", tzszsyr=" + tzszsyr +
        ", lxr=" + lxr +
        ", lxdh=" + lxdh +
        ", sxtxdqz=" + sxtxdqz +
        ", djzb=" + djzb +
        ", bwzb=" + bwzb +
        ", zdgzpd=" + zdgzpd +
        ", zggzpd=" + zggzpd +
        ", jzlx=" + jzlx +
        ", jzqqxqm=" + jzqqxqm +
        ", jzzt=" + jzzt +
        ", jzsj=" + jzsj +
        ", dtzzbh=" + dtzzbh +
        ", sfxyxt=" + sfxyxt +
        ", yynxtxt=" + yynxtxt +
        ", sfwcxt=" + sfwcxt +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateTime=" + updateTime +
        "}";
    }
}
