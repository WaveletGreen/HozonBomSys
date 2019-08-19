/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 日期-字符串自首
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class DateStringUtils {
    /**
     * 格式
     */
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";

    private static final String pattern2 = "yyyy-MM-dd";
    private static final String pattern3 = "yyyy年MM月dd日";
    /**
     * VWO号用
     */
    private static final String pattern4 = "yyyy";
    /**
     * 转换器
     */
    private static SimpleDateFormat format = new SimpleDateFormat(pattern);
    private static SimpleDateFormat format2 = new SimpleDateFormat(pattern2);
    private static SimpleDateFormat format3 = new SimpleDateFormat(pattern3);
    private static SimpleDateFormat format4 = new SimpleDateFormat(pattern4);

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        } else return format.format(date);
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString2(Date date) {
        if (date == null) {
            return null;
        } else return format2.format(date);
    }

    public static String dateToString3(Date date) {
        if (date == null) {
            return null;
        } else return format3.format(date);
    }

    public static String dateToString4(Date date) {
        if (date == null) {
            return null;
        } else return format4.format(date);
    }

    /**
     * 字符串转日期
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str) throws ParseException {
        if (StringUtils.checkString(str)) {
            return format.parse(str);
        } else {
            return null;
        }
    }

    /**
     * 字符串转日期，没有秒
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date stringToDate2(String str) throws ParseException {
        if (StringUtils.checkString(str)) {
            return format2.parse(str);
        } else {
            return null;
        }
    }

    /**
     * 永久时间点
     *
     * @return
     */
    public static Date forever() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(9999, 11, 31, 23, 59, 59);
        return calendar.getTime();
    }
}
