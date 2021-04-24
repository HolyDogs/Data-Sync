package com.thinvent.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xff
 * @since 2020-09-11
 */
public class PoolZbNewTableZbInfo extends Model<PoolZbNewTableZbInfo> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 主题表ID
     */
    private String tableId;
    /**
     * 主题表名
     */
    private String tableName;
    /**
     * 指标ID
     */
    private String zbid;
    /**
     * 进入时带入的指标ID
     */
    private String bindId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PoolZbNewTableZbInfo{" +
        "id=" + id +
        ", tableId=" + tableId +
        ", tableName=" + tableName +
        ", zbid=" + zbid +
        ", bindId=" + bindId +
        "}";
    }
}
