package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author xff
 * @since 2020-10-20
 */
public class SDictionaryItems extends Model<SDictionaryItems> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String itemGuid;
    /**
     * 字典项对应字典表id
     */
    private Long groupNo;
    /**
     * 字典项id
     */
    private String itemKey;
    /**
     * 字典项父项
     */
    private String itemParent;
    /**
     * 字典项值
     */
    private String itemValue;
    /**
     * 字典项值全称
     */
    private String itemFullValue;
    /**
     * 地区级别（地区字典项）
     */
    private Integer areaLevel;
    /**
     * 排序号
     */
    private Long pxh;
    /**
     * 是否启用（1-启用）
     */
    private Integer enable;
    /**
     * 版本号
     */
    private Long version;
    /**
     * 字典项创建人id
     */
    private String createUserId;
    /**
     * 字典项创建人
     */
    private String createUserName;
    /**
     * 字典项创建时间
     */
    private Date createTime;
    /**
     * 字典项更新人id
     */
    private String updateUserId;
    /**
     * 字典项更新人
     */
    private String updateUserName;
    /**
     * 字典项更新时间
     */
    private Date updateTime;
    /**
     * 地区编码
     */
    private String areaCode;
    /**
     * 类型
     */
    private Integer dataType;


    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }

    public Long getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Long groupNo) {
        this.groupNo = groupNo;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemParent() {
        return itemParent;
    }

    public void setItemParent(String itemParent) {
        this.itemParent = itemParent;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemFullValue() {
        return itemFullValue;
    }

    public void setItemFullValue(String itemFullValue) {
        this.itemFullValue = itemFullValue;
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public Long getPxh() {
        return pxh;
    }

    public void setPxh(Long pxh) {
        this.pxh = pxh;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    @Override
    protected Serializable pkVal() {
        return this.itemGuid;
    }

    @Override
    public String toString() {
        return "SDictionaryItems{" +
        "itemGuid=" + itemGuid +
        ", groupNo=" + groupNo +
        ", itemKey=" + itemKey +
        ", itemParent=" + itemParent +
        ", itemValue=" + itemValue +
        ", itemFullValue=" + itemFullValue +
        ", areaLevel=" + areaLevel +
        ", pxh=" + pxh +
        ", enable=" + enable +
        ", version=" + version +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateTime=" + updateTime +
        ", areaCode=" + areaCode +
        ", dataType=" + dataType +
        "}";
    }
}
