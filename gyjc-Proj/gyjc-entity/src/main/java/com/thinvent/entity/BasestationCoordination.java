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
 * @author yb
 * @since 2020-11-12
 */
public class BasestationCoordination extends Model<BasestationCoordination> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;
    /**
     * 保护清单识别号
     */
    private String bhqdsbh;
    /**
     * 用户单位名称
     */
    private String yhdwmc;
    /**
     * 台站经度（°）
     */
    private String tzjd;
    /**
     * 台站纬度（°）
     */
    private String tzwd;
    /**
     * 是否需要协调
     */
    private String sfxyxt;
    /**
     * 是否已发起协调请求
     */
    private String sfyfqxtqq;
    /**
     * 是否已确认协调请求
     */
    private String sfyqrxtqq;
    /**
     * 是否已完成协调
     */
    private String sfywcxt;
    /**
     * 是否撤站
     */
    private String sfcz;
    /**
     * 撤站时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date czsj;
    /**
     * 是否迁址
     */
    private String sfqz;
    /**
     * 迁址后位置
     */
    private String qzhwz;
    /**
     * 迁址后东经（°）
     */
    private String qzhdj;
    /**
     * 迁址后北纬（°）
     */
    private String qzhbw;
    /**
     * 迁址时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date qzsj;
    /**
     * 是否调整频率
     */
    private String sftzpl;
    /**
     * 调整后接收频率（起）MHz
     */
    private String tzhjsplq;
    /**
     * 调整后接收频率（止）MHz
     */
    private String tzhjsplz;
    /**
     * 调整频率时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date tzplsj;
    /**
     * 是否属于受影响台站
     */
    private String sfsysyxtz;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建人
     */
    private String createUserName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新人id
     */
    private String updateUserId;
    /**
     * 更新人
     */
    private String updateUserName;
    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBhqdsbh() {
        return bhqdsbh;
    }

    public void setBhqdsbh(String bhqdsbh) {
        this.bhqdsbh = bhqdsbh;
    }

    public String getYhdwmc() {
        return yhdwmc;
    }

    public void setYhdwmc(String yhdwmc) {
        this.yhdwmc = yhdwmc;
    }

    public String getTzjd() {
        return tzjd;
    }

    public void setTzjd(String tzjd) {
        this.tzjd = tzjd;
    }

    public String getTzwd() {
        return tzwd;
    }

    public void setTzwd(String tzwd) {
        this.tzwd = tzwd;
    }

    public String getSfxyxt() {
        return sfxyxt;
    }

    public void setSfxyxt(String sfxyxt) {
        this.sfxyxt = sfxyxt;
    }

    public String getSfyfqxtqq() {
        return sfyfqxtqq;
    }

    public void setSfyfqxtqq(String sfyfqxtqq) {
        this.sfyfqxtqq = sfyfqxtqq;
    }

    public String getSfyqrxtqq() {
        return sfyqrxtqq;
    }

    public void setSfyqrxtqq(String sfyqrxtqq) {
        this.sfyqrxtqq = sfyqrxtqq;
    }

    public String getSfywcxt() {
        return sfywcxt;
    }

    public void setSfywcxt(String sfywcxt) {
        this.sfywcxt = sfywcxt;
    }

    public String getSfcz() {
        return sfcz;
    }

    public void setSfcz(String sfcz) {
        this.sfcz = sfcz;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getSfqz() {
        return sfqz;
    }

    public void setSfqz(String sfqz) {
        this.sfqz = sfqz;
    }

    public String getQzhwz() {
        return qzhwz;
    }

    public void setQzhwz(String qzhwz) {
        this.qzhwz = qzhwz;
    }

    public String getQzhdj() {
        return qzhdj;
    }

    public void setQzhdj(String qzhdj) {
        this.qzhdj = qzhdj;
    }

    public String getQzhbw() {
        return qzhbw;
    }

    public void setQzhbw(String qzhbw) {
        this.qzhbw = qzhbw;
    }

    public Date getQzsj() {
        return qzsj;
    }

    public void setQzsj(Date qzsj) {
        this.qzsj = qzsj;
    }

    public String getSftzpl() {
        return sftzpl;
    }

    public void setSftzpl(String sftzpl) {
        this.sftzpl = sftzpl;
    }

    public String getTzhjsplq() {
        return tzhjsplq;
    }

    public void setTzhjsplq(String tzhjsplq) {
        this.tzhjsplq = tzhjsplq;
    }

    public String getTzhjsplz() {
        return tzhjsplz;
    }

    public void setTzhjsplz(String tzhjsplz) {
        this.tzhjsplz = tzhjsplz;
    }

    public Date getTzplsj() {
        return tzplsj;
    }

    public void setTzplsj(Date tzplsj) {
        this.tzplsj = tzplsj;
    }

    public String getSfsysyxtz() {
        return sfsysyxtz;
    }

    public void setSfsysyxtz(String sfsysyxtz) {
        this.sfsysyxtz = sfsysyxtz;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BasestationCoordination{" +
        "id=" + id +
        ", bhqdsbh=" + bhqdsbh +
        ", yhdwmc=" + yhdwmc +
        ", tzjd=" + tzjd +
        ", tzwd=" + tzwd +
        ", sfxyxt=" + sfxyxt +
        ", sfyfqxtqq=" + sfyfqxtqq +
        ", sfyqrxtqq=" + sfyqrxtqq +
        ", sfywcxt=" + sfywcxt +
        ", sfcz=" + sfcz +
        ", czsj=" + czsj +
        ", sfqz=" + sfqz +
        ", qzhwz=" + qzhwz +
        ", qzhdj=" + qzhdj +
        ", qzhbw=" + qzhbw +
        ", qzsj=" + qzsj +
        ", sftzpl=" + sftzpl +
        ", tzhjsplq=" + tzhjsplq +
        ", tzhjsplz=" + tzhjsplz +
        ", tzplsj=" + tzplsj +
        ", sfsysyxtz=" + sfsysyxtz +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", updateUserId=" + updateUserId +
        ", updateUserName=" + updateUserName +
        ", updateTime=" + updateTime +
        "}";
    }
}
