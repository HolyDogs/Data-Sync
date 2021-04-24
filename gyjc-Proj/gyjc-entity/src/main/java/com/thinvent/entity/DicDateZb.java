package com.thinvent.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2020-09-08
 */
public class DicDateZb extends Model<DicDateZb> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 起始时间
     */
    private String qssj;
    /**
     * 截止时间
     */
    private String jzsj;
    /**
     * 时间名称
     */
    private String sjmc;
    /**
     * 时间值
     */
    private String sjz;
    /**
     * 时间别名
     */
    private String sjbm;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    public String getSjz() {
        return sjz;
    }

    public void setSjz(String sjz) {
        this.sjz = sjz;
    }

    public String getSjbm() {
        return sjbm;
    }

    public void setSjbm(String sjbm) {
        this.sjbm = sjbm;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DicDateZb{" +
        "id=" + id +
        ", qssj=" + qssj +
        ", jzsj=" + jzsj +
        ", sjmc=" + sjmc +
        ", sjz=" + sjz +
        ", sjbm=" + sjbm +
        "}";
    }
}
