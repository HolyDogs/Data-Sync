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
 * @since 2020-11-12
 */
public class SToken extends Model<SToken> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 登录用户ID
     */
    private String loginUserId;
    /**
     * 登录用户名
     */
    private String loginUserName;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 失效时间
     */
    private Date failureTime;
    /**
     * 创建人ID
     */
    private String createUserId;
    /**
     * 创建人
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * TOKEN
     */
    private String tokenNo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
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

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SToken{" +
        "id=" + id +
        ", loginUserId=" + loginUserId +
        ", loginUserName=" + loginUserName +
        ", loginTime=" + loginTime +
        ", failureTime=" + failureTime +
        ", createUserId=" + createUserId +
        ", createUserName=" + createUserName +
        ", createTime=" + createTime +
        ", tokenNo=" + tokenNo +
        "}";
    }
}
