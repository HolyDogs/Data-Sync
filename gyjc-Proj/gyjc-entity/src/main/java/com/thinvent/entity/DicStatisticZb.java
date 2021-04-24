package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2020-09-08
 */
public class DicStatisticZb extends Model<DicStatisticZb> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String zbtjzdId;
    private String zdlx;
    private String zbmc;
    private String jldw;
    private String zbbm;
    private Date createTime;


    public String getZbtjzdId() {
        return zbtjzdId;
    }

    public void setZbtjzdId(String zbtjzdId) {
        this.zbtjzdId = zbtjzdId;
    }

    public String getZdlx() {
        return zdlx;
    }

    public void setZdlx(String zdlx) {
        this.zdlx = zdlx;
    }

    public String getZbmc() {
        return zbmc;
    }

    public void setZbmc(String zbmc) {
        this.zbmc = zbmc;
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw;
    }

    public String getZbbm() {
        return zbbm;
    }

    public void setZbbm(String zbbm) {
        this.zbbm = zbbm;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.zbtjzdId;
    }

    @Override
    public String toString() {
        return "DicStatisticZb{" +
        "zbtjzdId=" + zbtjzdId +
        ", zdlx=" + zdlx +
        ", zbmc=" + zbmc +
        ", jldw=" + jldw +
        ", zbbm=" + zbbm +
        ", createTime=" + createTime +
        "}";
    }
}
