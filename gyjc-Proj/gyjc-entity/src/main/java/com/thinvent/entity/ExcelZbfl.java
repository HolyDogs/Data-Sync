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
 * @since 2020-09-08
 */
public class ExcelZbfl extends Model<ExcelZbfl> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 数据来源
     */
    private String sjly;
    /**
     * 指标分类
     */
    private String zbfl;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExcelZbfl{" +
        "id=" + id +
        ", sjly=" + sjly +
        ", zbfl=" + zbfl +
        ", createTime=" + createTime +
        "}";
    }
}
