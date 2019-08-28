package com.connor.hozon.resources.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haozt on 2018/5/22
 */
public class DateUtil {

    public static String DEFAULT_PATTERN = "yyyy-MM-dd";
    public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static String getTodayTextY() {
        DateFormat df = new SimpleDateFormat("yyyy");
        return df.format(new Date());
    }

    public static Date getToday() {
        return new Date();
    }


    public static String formatDate(String format, Date dt) {
        if (dt == null) {
            return "";
        }
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_PATTERN;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(dt);
    }

    /**
     * 日期转换为字符串
     *
     * @param date
     *            日期
     * @param format
     *            日期格式
     * @return 指定格式的日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 转换为默认格式(yyyy-MM-dd)的日期字符串
     * @param date
     * @return
     */
    public static String formatDefaultDate(Date date) {
        return formatDateByFormat(date, DEFAULT_PATTERN);
    }



    /**
     * 转换为完整格式(yyyy-MM-dd HH:mm:ss)的日期字符串
     * @param date
     * @return
     */
    public static String formatTimestampDate(Date date) {
        return formatDateByFormat(date, TIMESTAMP_PATTERN);
    }


    /**
     * 日期格式字符串转换为日期对象
     * @param strDate
     *            日期格式字符串
     * @param pattern
     *            日期对象
     * @return
     */
    public static Date parseDate(String strDate, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date nowDate = format.parse(strDate);
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换为(yyyy-MM-dd)日期对象
     * @param date
     * @return
     * @throws Exception
     */
    public static Date parseDefaultDate(String date) {
        return parseDate(date, DEFAULT_PATTERN);
    }

}
