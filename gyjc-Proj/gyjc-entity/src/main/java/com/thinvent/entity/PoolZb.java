package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 指标池
 * @author xufeng
 * @version 1.0
 * @date 2020/7/17 15:13
 **/
@Data
@TableName(value = "POOL_ZB")
public class PoolZb {

    @TableId(value = "ID")
    private String id;

    @TableField(value = "SOURCE_MARK")
    private String sourceMark;

    @TableField(value = "ZBMC")
    private String zbmc;

    @TableField(value = "BIND_ZB")
    private String bindZb;

    @TableField(value = "COMMENTS")
    private String comments;

    /**
     * 状态：
     * 0：默认空白
     * 1：已操作，无匹配项
     * 2：已操作，有匹配项
     * 3：已操作，有疑似匹配项
     */
    @TableField(value = "STATE")
    private String state;

    /**
     * 指标分类
     */
    @TableField(value = "ZBFL")
    private String zbfl;

    /**
     * 计量单位
     */
    @TableField(value = "JLDW")
    private String jldw;

    /**
     * 绑定指标来源表
     */
    private String bindZbTable;

    /**
     * 指标来源
     */
    private String zbly;

    /**
     * 指标在字典表的key
     */
    private String zbkey;

    /**
     * 创建/刷新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     *  是否为新指标
     */
    private String isNewZb;
}
