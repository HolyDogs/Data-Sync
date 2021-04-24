package com.thinvent.dto;

import lombok.Data;

/**
 * 动态创建的指标表数据传输对象
 * @author xufeng
 * @version 1.0
 * @date 2020/8/20 09:43
 **/
@Data
public class ZbTableDto {
    private String id;
    private String zbmc;
    private String value;
    private String dataSource;
    private String startDate;
    private String endDate;
    private String timeId;
    private String newValue;
    private String dwmc;
    private String dqmc;
    private String dqbm;
    private String jldw;
}
