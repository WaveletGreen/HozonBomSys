/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.util;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 字符串检查助手
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class StringUtils {
    /**
     * 检查字符串是否为真空
     *
     * @param str
     * @return
     */
    public static boolean checkString(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查字符串是否为null，不排除 ""
     *
     * @param str
     * @return
     */
    public static boolean checkStringIsNotNull(String str) {
        if (str == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查字符串是否为"",不排除null
     *
     * @param str
     * @return
     */
    public static boolean checkStringIsEmpty(String str) {
        /**必须检查是否为空才行，否则有几率报异常*/
        if (str == null) {
            return false;
        }
        if (str.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
