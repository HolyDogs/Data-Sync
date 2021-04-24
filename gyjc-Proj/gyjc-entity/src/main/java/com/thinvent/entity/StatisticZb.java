package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 外部导入指标数据对象
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
@TableName("STATISTIC_ZB")
public class StatisticZb extends Model<StatisticZb> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String zbtjId;
    private String nf;
    private String yf;
    private String zbid;
    private String zbmc;
    private String sz;
    private String sjly;
    private String zbfl;
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private String dqmc;
    private String dqbm;
    /**
     * 时间字典表ID
     */
    private String sjid;

    private String zbkey;

    public String getZbtjId() {
        return zbtjId;
    }

    public String getZbkey() {
        return zbkey;
    }

    public void setZbkey(String zbkey) {
        this.zbkey = zbkey;
    }

    public void setZbtjId(String zbtjId) {
        this.zbtjId = zbtjId;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getYf() {
        return yf;
    }

    public void setYf(String yf) {
        this.yf = yf;
    }

    public String getZbid() {
        return zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getZbmc() {
        return zbmc;
    }

    public void setZbmc(String zbmc) {
        this.zbmc = zbmc;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getZbfl() {
        return zbfl;
    }

    public void setZbfl(String zbfl) {
        this.zbfl = zbfl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getDqmc() {
        return dqmc;
    }

    public void setDqmc(String dqmc) {
        this.dqmc = dqmc;
    }

    public String getDqbm() {
        return dqbm;
    }

    public void setDqbm(String dqbm) {
        this.dqbm = dqbm;
    }

    @Override
    protected Serializable pkVal() {
        return this.zbtjId;
    }

    @Override
    public String toString() {
        return "StatisticZb{" +
        "zbtjId=" + zbtjId +
        ", nf=" + nf +
        ", yf=" + yf +
        ", zbid=" + zbid +
        ", zbmc=" + zbmc +
        ", sz=" + sz +
        ", sjly=" + sjly +
        ", zbfl=" + zbfl +
        ", createTime=" + createTime +
        ", sjid=" + sjid +
        "}";
    }
}
