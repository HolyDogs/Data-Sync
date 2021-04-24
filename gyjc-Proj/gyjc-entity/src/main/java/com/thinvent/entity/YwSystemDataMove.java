package com.thinvent.entity;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 各业务系统数据迁移
 * </p>
 *
 * @author xff
 * @since 2020-08-18
 */
@DS("slave")
@TableName("YW_SYSTEM_DATA_MOVE")
public class YwSystemDataMove extends Model<YwSystemDataMove> {

    private static final long serialVersionUID = 1L;

    @TableId("SJQY_ID")
    private String sjqyId;
    private String qssj;
    private String jzsj;
    private String sjid;
    private String zbid;
    private String zbmc;
    private String zbmcfz;
    private String sz;
    private String dwid;
    private String dwmc;
    private String dqid;
    private String dqmc;
    private String lyid;
    /**
     * 时间字典映射ID
     */
    private String timeid;


    public String getSjqyId() {
        return sjqyId;
    }

    public void setSjqyId(String sjqyId) {
        this.sjqyId = sjqyId;
    }

    public String getQssj() {
        return qssj;
    }

    public void setQssj(String qssj) {
        this.qssj = qssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
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

    public String getZbmcfz() {
        return zbmcfz;
    }

    public void setZbmcfz(String zbmcfz) {
        this.zbmcfz = zbmcfz;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public String getDwid() {
        return dwid;
    }

    public void setDwid(String dwid) {
        this.dwid = dwid;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getDqid() {
        return dqid;
    }

    public void setDqid(String dqid) {
        this.dqid = dqid;
    }

    public String getDqmc() {
        return dqmc;
    }

    public void setDqmc(String dqmc) {
        this.dqmc = dqmc;
    }

    public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid;
    }

    public String getTimeid() {
        return timeid;
    }

    public void setTimeid(String timeid) {
        this.timeid = timeid;
    }

    @Override
    protected Serializable pkVal() {
        return this.sjqyId;
    }

    @Override
    public String toString() {
        return "YwSystemDataMove{" +
        "sjqyId=" + sjqyId +
        ", qssj=" + qssj +
        ", jzsj=" + jzsj +
        ", sjid=" + sjid +
        ", zbid=" + zbid +
        ", zbmc=" + zbmc +
        ", zbmcfz=" + zbmcfz +
        ", sz=" + sz +
        ", dwid=" + dwid +
        ", dwmc=" + dwmc +
        ", dqid=" + dqid +
        ", dqmc=" + dqmc +
        ", lyid=" + lyid +
        ", timeid=" + timeid +
        "}";
    }
}
