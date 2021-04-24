package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2020-08-26
 */
@TableName("POOL_ZB_NEW_TABLE_ZS")
public class PoolZbNewTableZs extends Model<PoolZbNewTableZs> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String tableName;
    private String zbid;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String tableComments;
    private String parentTable;
    private Date startTime;
    private Date endTime;
    private String zskbm;
    private String zskmc;

    /**
     * 标记非数据库字段
     */
    @TableField(exist = false)
    private List<PoolZb> poolZbList;

    public List<PoolZb> getPoolZbList() {
        return poolZbList;
    }

    public void setPoolZbList(List<PoolZb> poolZbList) {
        this.poolZbList = poolZbList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getZbid() {
        return zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTableComments() {
        return tableComments;
    }

    public void setTableComments(String tableComments) {
        this.tableComments = tableComments;
    }

    public String getParentTable() {
        return parentTable;
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getZskbm() {
        return zskbm;
    }

    public void setZskbm(String zskbm) {
        this.zskbm = zskbm;
    }

    public String getZskmc() {
        return zskmc;
    }

    public void setZskmc(String zskmc) {
        this.zskmc = zskmc;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "PoolZbNewTableZs{" +
                "id='" + id + '\'' +
                ", tableName='" + tableName + '\'' +
                ", zbid='" + zbid + '\'' +
                ", createTime=" + createTime +
                ", tableComments='" + tableComments + '\'' +
                ", parentTable='" + parentTable + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", poolZbList=" + poolZbList +
                '}';
    }
}
