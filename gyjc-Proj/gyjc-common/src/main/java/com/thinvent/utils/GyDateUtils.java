package com.thinvent.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * @author xufeng
 * @version 1.0
 * @date 2020/10/23 17:26
 **/
public class GyDateUtils {


    /**
     * 处理成yyyy-MM-dd的形式返回
     * @param date 时间字符串
     * @param plusDay 添加的天数
     * @return
     */
    public static String formatDate(String date, int plusDay) {
        if (!StringUtils.isEmpty(date)) {
            LocalDate start = LocalDate.parse(date.substring(0, 10));
            //增加天数(时区原因，前台选择的时间较传来时间晚8小时)
            start = start.plusDays(plusDay);
            date = start.toString();
        }
        return date;
    }

    public static String yearEndStrFormat(String endDate) {
        //日期格式化
        if (!StringUtils.isEmpty(endDate)) {
            LocalDate localDate = LocalDate.parse(endDate);
            int year = localDate.getYear();
            endDate = year + "-12-31";
        }
        return endDate;
    }

    public static String quaterEndStrFormat(String endDate) {
        if (!StringUtils.isEmpty(endDate)) {
            String endYear = endDate.substring(0, 4);
            char quater = endDate.charAt(5);
            switch (quater) {
                case '1':
                    endDate = endYear + "-03-31";
                    break;
                case '2':
                    endDate = endYear + "-06-30";
                    break;
                case '3':
                    endDate = endYear + "-09-30";
                    break;
                case '4':
                    endDate = endYear + "-12-31";
                    break;
                default:
                    endDate = null;
                    break;
            }
        }
        return endDate;
    }

    public static String quaterStartStrFormat(String startDate) {
        if (!StringUtils.isEmpty(startDate)) {
            String startYear = startDate.substring(0, 4);
            char quater = startDate.charAt(5);
            switch (quater) {
                case '1':
                    startDate = startYear + "-01-01";
                    break;
                case '2':
                    startDate = startYear + "-04-01";
                    break;
                case '3':
                    startDate = startYear + "-07-01";
                    break;
                case '4':
                    startDate = startYear + "-10-01";
                    break;
                default:
                    startDate = null;
                    break;
            }
        }
        return startDate;
    }
}
