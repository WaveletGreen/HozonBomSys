/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 日期-字符串自首
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class DateStringHelper {
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
        String xxx = dateToString3(new Date());
        System.out.println(date);
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
