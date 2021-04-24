package com.thinvent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 指标新建表记录
 * @author xufeng
 * @version 1.0
 * @date 2020/8/12 10:07
 **/
@Data
@TableName(value = "POOL_ZB_NEW_TABLE")
public class PoolZbNewTable {

    @TableId(value = "ID")
    private String id;

    @TableField(value = "TABLE_NAME")
    private String tableName;

    @TableField(value = "ZBID")
    private String zbId;

    @TableField(value = "CREATE_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "TABLE_COMMENTS")
    private String tableComments;

    @TableField(value = "START_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @TableField(value = "END_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @TableField(value = "ZTKBM")
    private String ztkbm;

    @TableField(value = "ZTKMC")
    private String ztkmc;

    private List<PoolZb> poolZbList;
}
