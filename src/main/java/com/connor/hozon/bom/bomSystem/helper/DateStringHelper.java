package com.connor.hozon.bom.bomSystem.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

public class DateStringHelper {
    /**
     * 格式
     */
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";

    private static final String pattern2 = "yyyy-MM-dd HH:mm";
    /**
     * 转换器
     */
    private static SimpleDateFormat format = new SimpleDateFormat(pattern);
    private static SimpleDateFormat format2 = new SimpleDateFormat(pattern2);

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

    /**
     * 字符串转日期
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str) throws ParseException {
        if (checkString(str)) {
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
        if (checkString(str)) {
            return format2.parse(str);
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws ParseException {
        Date date = stringToDate2("2018-07-04 05:00");
        System.out.println(date);
    }
}
