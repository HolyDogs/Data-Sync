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
 * @author xff
 * @since 2020-09-24
 */
public class ExcelHandImport extends Model<ExcelHandImport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;
    /**
     * 指标分类
     */
    private String zbfl;
    /**
     * 指标名称
     */
    private String zbmc;
    /**
     * 计量单位
     */
    private String jldw;
    /**
     * 数值
     */
    private String sz;
    /**
     * 地区名称
     */
    private String dqmc;
    /**
     * 时间ID
     */
    private String sjid;
    /**
     * 数据来源说明
     */
    private String sjly;
    /**
     * 地区编码
     **/
    private String dqbm;
    /**
     * 指标编码
     */
    private String zbkey;

    /**
     * 导入时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    public String getZbkey() {
        return zbkey;
    }

    public void setZbkey(String zbkey) {
        this.zbkey = zbkey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZbfl() {
        return zbfl;
    }

    public void setZbfl(String zbfl) {
        this.zbfl = zbfl;
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

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public String getDqmc() {
        return dqmc;
    }

    public void setDqmc(String dqmc) {
        this.dqmc = dqmc;
    }

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getDqbm() {
        return dqbm;
    }

    public void setDqbm(String dqbm) {
        this.dqbm = dqbm;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExcelHandImport{" +
        "id=" + id +
        ", zbfl=" + zbfl +
        ", zbmc=" + zbmc +
        ", jldw=" + jldw +
        ", sz=" + sz +
        ", dqmc=" + dqmc +
        ", sjid=" + sjid +
        ", sjly=" + sjly +
        "}";
    }
}
