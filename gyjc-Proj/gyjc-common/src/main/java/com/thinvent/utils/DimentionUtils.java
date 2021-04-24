package com.thinvent.utils;

import com.thinvent.mapper.DicMapper;
import com.thinvent.mapper.ZsTjTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 维度生成工具类
 */
@Component
public class DimentionUtils {

    @Autowired
    @SuppressWarnings("all")
    private DicMapper dicMapper;

    @Autowired
    @SuppressWarnings("all")
    private ZsTjTableMapper zsTjTableMapper;

    private static Pattern NUM_PATTERN = Pattern.compile("^\\d{5,6}");

    /**
     * 计算月度累计值
     * @param dataMap 数据集合
     * @return 计算结果
     */
    public List<HashMap> convertMonthTotal(List<HashMap> dataMap) {
        HashMap midMap = new HashMap();
        for (int i=1; i<13; i++) {
            //循环12次，每次加入一个月份的累计值数据
            for (HashMap<String, Object> valueMap:dataMap) {
                if (i == Integer.parseInt((String) valueMap.get("YF"))) {
                    String dqmc = (String) valueMap.get("DQMC");
                    if (StringUtils.isEmpty(dqmc)) {
                        dqmc = "";
                    }
                    if (i == 1) {
                        //一月份直接放入
                        midMap.put((String) valueMap.get("NF") + i + dqmc, valueMap.get("SUMVALUE"));
                    } else {
                        int b = i;
                        while (b > 0) {

                            //如果已有上一月份的累计数据，相加即可得当前月份的累计数据
                            Object value = midMap.get((String) valueMap.get("NF") + (b-1) + dqmc);
                            if (midMap.containsKey((String) valueMap.get("NF") + (b-1) + dqmc)
                                    && null != value) {
                                //当前值
                                BigDecimal sumValue = (BigDecimal) valueMap.get("SUMVALUE");
                                if (sumValue == null) {
                                    sumValue = new BigDecimal(0);
                                }
                                //值相加
                                midMap.put((String) valueMap.get("NF") + i + dqmc
                                        , sumValue.add((BigDecimal) value));
                                break;
                            } else {
                                //如果无上一月的数据，则继续查询前一月是否有累计数据（再循环）
                                b = b-1;
                                if (b == 0) {
                                    //如果当前月之前无累计数据，则当前月数据则为累计值
                                    midMap.put((String) valueMap.get("NF") + i + dqmc, valueMap.get("SUMVALUE"));
                                }
                            }
                        }
                    }

                }
            }
        }
        Iterator iterator = midMap.entrySet().iterator();
        //用于存放返回的集合数据
        List<HashMap> hashMapList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            //key 为 年份+月份+地区名称的组合
            String key = (String) entry.getKey();
            //累计值
            String value = null;
            if (null != entry.getValue()) {
                value = String.valueOf(entry.getValue());
            }

            //获取key的前缀数字
            Matcher matcher = NUM_PATTERN.matcher(key);
            if (matcher.find()) {
                String time = matcher.group();
                //取年份
                String year = time.substring(0, 4);
                //月份
                String month = time.substring(4);
                if (month.length() < 2){
                    //不足两位补0
                    month = "0" + month;
                }
                HashMap hashMap = new HashMap();
                //累计值
                hashMap.put("SUMVALUE", value);
                //年份
                hashMap.put("NF", year);
                //月份
                hashMap.put("YF", month);
                //地区名称
                String dqmc = key.replace(time, "");
                hashMap.put("DQMC", dqmc);
                //地区编码
                String dqbm = dicMapper.getItemKeyByValue(dqmc, "1");
                hashMap.put("DQBM", dqbm);
                //计量单位
                hashMap.put("JLDW", dataMap.get(0).get("JLDW"));
                hashMapList.add(hashMap);
            }
        }
        return hashMapList;
    }


    /**
     * 计算月度同比
     * @param dataMap 数据集合
     * @param tjTableName 统计表名
     * @param zbkey 指标编码
     * @param tjType 统计类型
     * @return 计算结果
     */
    public List<HashMap> calcuMonthTB(List<HashMap> dataMap, String tjTableName
            , String zbkey, String tjType) {
        List<HashMap> resultList = new ArrayList<>();
        for (HashMap hashMap:dataMap) {
            //年份
            String nf = (String) hashMap.get("NF");
            //月份
            String yf = (String) hashMap.get("YF");
            //地区名称
            String dqmc = (String) hashMap.get("DQMC");
            //地区编码
            String dqbm = (String) hashMap.get("DQBM");

            String lastValueStr = null;
            switch (tjType){
                case "YDTB":
                    //上一年
                    int lastNf = Integer.parseInt(nf) - 1;
                    //查询上一年数据
                    lastValueStr = zsTjTableMapper.selectTjValue(tjTableName
                            , String.valueOf(lastNf), yf, zbkey, dqbm, "月度值");
                    break;
                case "YDHB":
                    //上一月
                    int lastMonth = Integer.parseInt(yf) -1;
                    if (lastMonth < 1) {
                        //如果为1月直接跳出
                        break;
                    }
                    String lastMonthStr = String.valueOf(lastMonth);
                    if (lastMonthStr.length() < 2){
                        //转为0x的形式
                        lastMonthStr = "0" + lastMonthStr;
                    }
                    //查询上一月的数据
                    lastValueStr = zsTjTableMapper.selectTjValue(tjTableName
                            , nf, lastMonthStr, zbkey, dqbm, "月度值");
                    break;
                case "YDLJTB":
                    //上一年
                    int lastNf2 = Integer.parseInt(nf) - 1;
                    //查询上一年数据
                    lastValueStr = zsTjTableMapper.selectTjValue(tjTableName
                            , String.valueOf(lastNf2), yf, zbkey, dqbm, "月度累计值");
                    break;
                case "YDLJHB":
                    //上一月
                    int lastMonth2 = Integer.parseInt(yf) -1;
                    if (lastMonth2 < 1) {
                        //如果为1月直接跳出
                        break;
                    }
                    String lastMonthStr2 = String.valueOf(lastMonth2);
                    if (lastMonthStr2.length() < 2){
                        //转为0x的形式
                        lastMonthStr2 = "0" + lastMonthStr2;
                    }
                    //查询上一月的数据
                    lastValueStr = zsTjTableMapper.selectTjValue(tjTableName
                            , nf, lastMonthStr2, zbkey, dqbm, "月度累计值");
                    break;
                case "JDTB":
                    //季度同比
                    //上一年
                    int lastNf3 = Integer.parseInt(nf) - 1;
                    //查询上一年数据
                    lastValueStr = zsTjTableMapper.selectTjValue(tjTableName
                            , String.valueOf(lastNf3), yf, zbkey, dqbm, "季度值");
                    break;
                case "JDHB":
                    //季度环比
                    //上一季度
                    int lastQuater = Integer.parseInt(yf) - 1;
                    if (lastQuater < 1) {
                        //如果为1季度直接跳出
                        break;
                    }
                    String lastQuaterStr = String.valueOf(lastQuater);
                    //查询上一季度的数据
                    lastValueStr = zsTjTableMapper.selectTjValue(tjTableName
                            , nf, lastQuaterStr, zbkey, dqbm, "季度值");
                    break;
                default:
                    break;
            }

            if (StringUtils.isEmpty(lastValueStr)) {
                continue;
            }
            //当前值
            Object currentValue = hashMap.get("SUMVALUE");
            BigDecimal nowValue = null;
            if (currentValue instanceof BigDecimal) {
                nowValue = (BigDecimal) currentValue;
            } else {
                if (StringUtils.isEmpty(currentValue)) {
                    //跳过
                    continue;
                }
                nowValue = new BigDecimal((String) currentValue);
            }
            //同期值
            BigDecimal lastValue = new BigDecimal(lastValueStr);
            BigDecimal result;
            //修复bug，同比环比时过去值为0则设置同比环比为0
            if (lastValue.compareTo(BigDecimal.ZERO) == 0) {
                //如果上一期是0，则直接为100%
                result = new BigDecimal(100);
            } else {
                //计算月季度同比或环比值 正常情况
                result = nowValue.subtract(lastValue)
                        .divide(lastValue, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100))
                        .setScale(2, BigDecimal.ROUND_DOWN);
            }
            //封装返回
            HashMap resultMap = new HashMap();
            resultMap.put("NF", nf);
            resultMap.put("YF", yf);
            resultMap.put("TIME_ID", hashMap.get("TIME_ID"));
            resultMap.put("DQMC", dqmc);
            resultMap.put("DQBM", dqbm);
            resultMap.put("JLDW", "%");
            resultMap.put("SUMVALUE", result.toPlainString());
            resultList.add(resultMap);
        }
        return resultList;
    }

}
