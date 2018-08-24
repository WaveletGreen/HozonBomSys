package com.connor.hozon.bom.bomSystem.helper;

public class StringHelper {
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
        if (str.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }
}