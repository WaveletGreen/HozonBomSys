package com.connor.hozon.bom.resources.util;

import java.text.Collator;
import java.text.MessageFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by haozt on 2018/5/22
 */
public class StringUtil {
    private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    private static final Collator chineseCollator = Collator.getInstance(java.util.Locale.CHINA);;

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * 把指定的数据转化为16进制格式的字符串
     * @param data 待转化的数据
     * @return 16进制表示的数据
     */
    public static String toHexString(byte[] data) {
        return toHexString(data,0,data.length);
    }

    /**
     * 把指定的数据转化为16进制格式的字符串，
     * 如toHexString({8,9,12,4},1,3) = "090C"
     * @param data 待转化的数据
     * @param beginIndex 需转化的数据的起始索引
     * @param endIndex 需转化的数据的结束索引
     * @return 16进制表示的数据
     */
    public static String toHexString(byte[] data,int beginIndex,int endIndex) {
        if (data == null || beginIndex < 0) return null;
        StringBuilder strBuilder = new StringBuilder();
        for (int i = beginIndex;i < endIndex;i++) {
            strBuilder.append(hexDigits[data[i] >>> 4 & 0xf]);
            strBuilder.append(hexDigits[data[i]& 0xf]);
        }
        return strBuilder.toString();
    }


    /**
     * 使用<code>MessageFormat</code>格式化字符串.
     *
     * @param message 要格式化的字符串
     * @param params 参数表
     *
     * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
     */
    public static String formatMessage(String message, Object... params) {
        if ((message == null) || (params == null) || (params.length == 0)) {
            return message;
        }

        return MessageFormat.format(message, params);
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    private static final String STRING_POOL="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 根据输入的字符串，生成指定长度的随机字符串
     * @param strPool
     * @param length
     * @return
     */
    public static String randomString(String strPool,int length) {
        if (length < 1) {
            return null;
        }
        if (strPool == null)
            strPool = STRING_POOL;

        Random randGen = new Random();
        char[] numbersAndLetters = (strPool).toCharArray();

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(strPool.length())];
        }
        return new String(randBuffer);
    }



    /**
     * 将手机号匿名化
     * @param mobile
     * @return
     */
    public static String getMobileAnonymous(String mobile){
        if(StringUtil.isEmpty(mobile)) {
            return "";
        }
        if(mobile.length() < 8) {
            return mobile;
        }
        String head = mobile.substring(0, 4);
        String end = mobile.substring(mobile.length() - 4 , mobile.length());
        return head + "***" + end;
    }

    public static String truncate(String s, int len) {
        if (s == null) return "";
        return s.length() > len ? s.substring(0, len) : s;
    }

    public static int chineseCompare(String s1, String s2) {
        return chineseCollator.compare(s1, s2);
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     */
    public static boolean containsEmoji(String source) {
        if (isEmpty(source))
            return false;

        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint))
                return true; // 判断到了这里表明，确认有表情字符
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source))
            return source;

        StringBuilder buf = new StringBuilder(source.length());
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint))
                buf.append("?");
            else
                buf.append(codePoint);
        }
        return buf.toString();
    }


    /**
     * \n 回车(\u000a)  \t 水平制表符(\u0009) \s 空格(\u0008) \r 换行(\u000d)
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static boolean isChineseName(String mName){

        if (!mName.matches("[\u4e00-\u9fa5]{2,4}")) {
            return false;
        }else
            return true;

    }
}
