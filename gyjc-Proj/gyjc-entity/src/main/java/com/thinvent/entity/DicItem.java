package com.thinvent.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 *
 * @author yb
 * @since 2020-10-16
 */
public class DicItem extends Model<DicItem> {

    private static final long serialVersionUID = 1L;

    private String itemGuid;

    private Integer groupNo;

    private String itemKey;

    private String itemParent;

    private String itemValue;

    private String itemFullValue;

    private Integer areaLevel;

    private Integer pxh;

    private Integer enable;

    private Integer version;

    private String createUserId;

    private String createUserName;

    private Date createTime;

    private String updateUserId;

    private String updateUsername;

    private Date updateTime;

    private String areaCode;

    private Integer dataType;

    public String getItemGuid() {
        return itemGuid;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public String getItemKey() {
        return itemKey;
    }

    public String getItemParent() {
        return itemParent;
    }

    public String getItemValue() {
        return itemValue;
    }

    public String getItemFullValue() {
        return itemFullValue;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public Integer getPxh() {
        return pxh;
    }

    public Integer getEnable() {
        return enable;
    }

    public Integer getVersion() {
        return version;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public void setItemParent(String itemParent) {
        this.itemParent = itemParent;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public void setItemFullValue(String itemFullValue) {
        this.itemFullValue = itemFullValue;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public void setPxh(Integer pxh) {
        this.pxh = pxh;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
