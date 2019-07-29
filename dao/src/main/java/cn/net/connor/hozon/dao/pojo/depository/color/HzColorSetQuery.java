/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.depository.color;

import cn.net.connor.hozon.common.entity.QueryBase;

public class HzColorSetQuery extends QueryBase {

    private String pColorOfSet;

    private String pColorName;

    private String pColorCode;

    private String pColorPlate;


    public String getpColorOfSet() {
        return pColorOfSet;
    }

    public void setpColorOfSet(String pColorOfSet) {
        this.pColorOfSet = pColorOfSet;
    }

    public String getpColorName() {
        return pColorName;
    }

    public void setpColorName(String pColorName) {
        this.pColorName = pColorName;
    }

    public String getpColorCode() {
        return pColorCode;
    }

    public void setpColorCode(String pColorCode) {
        this.pColorCode = pColorCode;
    }

    public String getpColorPlate() {
        return pColorPlate;
    }

    public void setpColorPlate(String pColorPlate) {
        this.pColorPlate = pColorPlate;
    }


    /**
     * 映射到数据库字段
     *
     * @param property
     * @return
     */
    public static String reflectToDBField(String property) {
        switch (property) {
            /**
             * 主键
             */
            case "puid":
                return "PUID";
            /**
             * 颜色集
             */
            case "pColorOfSet":
                return "p_Color_Of_Set";
            /**
             * 颜色名称
             */
            case "pColorName":
                return "p_Color_Name";
            /**
             * 颜色代码
             */
            case "pColorCode":
                return "p_Color_Code";
            /**
             * 备注
             */
            case "pColorComment":
                return "p_Color_Comment";
            /**
             * 是否多色
             */
            case "pColorIsMultiply":
                return "p_Color_Is_Multiply";
            /**
             * 色板
             */
            case "pColorPlate":
                return "p_Color_Plate";
            /**
             * 修改者
             */
            case "pColorModifier":
                return "p_Color_Modifier";
            /**
             * 创建时间
             */
            case "pColorCreateDate":
                return "p_Color_Create_Date";
            /**
             * 废止时间
             */
            case "pColorAbolishDate":
                return "p_Color_Abolish_Date";
            /**
             * 生效时间
             */
            case "pColorEffectedDate":
                return "p_Color_Effected_Date";
            default:
                return null;
        }
    }
}
