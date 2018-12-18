package com.connor.hozon.bom.resources.domain.constant;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description: BOM 数据传输 常量
 */
public class BOMTransConstants {
    /**
     * 基本常量
     */
    public final static String Y = "Y";
    public final static String N = "N";
    public final static String INNER_PART = "内饰件";
    public final static String OUTER_PART = "外饰件";
    public final static String EMPTY ="";

    public final static String ALL="ALL";
    /**
     * 变更状态标志位
     */
    public final static String A ="A";
    public final static String U ="U";
    public final static String D ="D";

    /**
     * String 转 Integer
     * Y->1  N->0
     * 内饰件->1
     * 外饰件->0
     */
    public static Integer constantStringToInteger(String s){
        if(StringUtils.isBlank(s)){
            return null;
        }
        switch (s){
            case Y: return 1;
            case N: return 0;
            case INNER_PART:return 1;
            case OUTER_PART:return 0;
            default:return null;
        }
    }

    /**
     * Integer 转String 内外饰标识
     * @param in
     * @return
     */
    public static String inOutSideStringToInteger(Integer in){
        if(null == in){
            return EMPTY;
        }
        switch (in){
            case 1:return INNER_PART;
            case 0:return OUTER_PART;
            default:return EMPTY;
        }
    }

    /**
     * Integer 转 String
     * 1->Y
     * 0->N
     * @param in
     * @return
     */
    public static String YNStringToInteger(Integer in){
        if(null == in){
            return EMPTY;
        }
        switch (in){
            case 1:return Y;
            case 0:return N;
            default:return EMPTY;
        }
    }
}
