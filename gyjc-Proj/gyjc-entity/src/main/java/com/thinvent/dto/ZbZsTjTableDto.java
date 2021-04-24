package com.thinvent.dto;

import lombok.Data;

/**
 * 指标展示统计表的数据传输对象
 * @author xufeng
 * @version 1.0
 * @date 2020/10/13 10:40
 **/
@Data
public class ZbZsTjTableDto {
    private String id;
    private String zbmc;
    private String value;
    private String dataSource;
    private String nf;
    private String yforjd;
    private String timeId;
    private String newValue;
    private String dqmc;
    private String dqbm;
    private String jldw;
    private String statisticMark;
    private String zbkey;
    private String dwmc;
    private String hymc;
}
