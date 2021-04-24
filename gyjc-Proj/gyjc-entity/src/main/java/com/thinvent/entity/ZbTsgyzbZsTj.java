package com.thinvent.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2020-12-17
 */
public class ZbTsgyzbZsTj extends Model<ZbTsgyzbZsTj> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 级别
     */
    private String jb;
    /**
     * 行业名称
     */
    private String hymc;
    /**
     * 地区名称
     */
    private String dqmc;
    /**
     * 地区编码
     */
    private String dqbm;
    /**
     * 所属单位
     */
    private String ssdw;
    /**
     * 企业性质
     */
    private String qyxz;
    /**
     * 产品名称
     */
    private String cpmc;
    /**
     * 计量单位
     */
    private String jldw;
    /**
     * 许可能力
     */
    private Long xknl;
    /**
     * 生产量
     */
    private Long scl;
    /**
     * 销售量
     */
    private Long xsl;
    /**
     * 质量标杆名称
     */
    private String zlbgmc;
    /**
     * 服务型制造模式
     */
    private String fwxzzms;
    /**
     * 年份
     */
    private String nf;
    /**
     * 数据类别
     */
    private String dataType;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 数据类别名称
     */
    private String typeName;
    /**
     * 创建时间
     */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJb() {
        return jb;
    }

    public void setJb(String jb) {
        this.jb = jb;
    }

    public String getHymc() {
        return hymc;
    }

    public void setHymc(String hymc) {
        this.hymc = hymc;
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

    public String getSsdw() {
        return ssdw;
    }

    public void setSsdw(String ssdw) {
        this.ssdw = ssdw;
    }

    public String getQyxz() {
        return qyxz;
    }

    public void setQyxz(String qyxz) {
        this.qyxz = qyxz;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw;
    }

    public Long getXknl() {
        return xknl;
    }

    public void setXknl(Long xknl) {
        this.xknl = xknl;
    }

    public Long getScl() {
        return scl;
    }

    public void setScl(Long scl) {
        this.scl = scl;
    }

    public Long getXsl() {
        return xsl;
    }

    public void setXsl(Long xsl) {
        this.xsl = xsl;
    }

    public String getZlbgmc() {
        return zlbgmc;
    }

    public void setZlbgmc(String zlbgmc) {
        this.zlbgmc = zlbgmc;
    }

    public String getFwxzzms() {
        return fwxzzms;
    }

    public void setFwxzzms(String fwxzzms) {
        this.fwxzzms = fwxzzms;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ZbTsgyzbZsTj{" +
        "id=" + id +
        ", name=" + name +
        ", jb=" + jb +
        ", hymc=" + hymc +
        ", dqmc=" + dqmc +
        ", dqbm=" + dqbm +
        ", ssdw=" + ssdw +
        ", qyxz=" + qyxz +
        ", cpmc=" + cpmc +
        ", jldw=" + jldw +
        ", xknl=" + xknl +
        ", scl=" + scl +
        ", xsl=" + xsl +
        ", zlbgmc=" + zlbgmc +
        ", fwxzzms=" + fwxzzms +
        ", nf=" + nf +
        ", dataType=" + dataType +
        ", xmmc=" + xmmc +
        ", typeName=" + typeName +
        ", createTime=" + createTime +
        "}";
    }
}
