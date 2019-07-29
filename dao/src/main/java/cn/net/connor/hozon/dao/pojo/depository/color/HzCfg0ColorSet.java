/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.depository.color;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzCfg0ColorSet {
    /**
     * 主键
     */
    private String puid;

    /**
     * 颜色集
     */
    private String pColorOfSet;

    /**
     * 颜色名称
     */
    private String pColorName;
    /**
     * 颜色代码
     */
    private String pColorCode;
    /**
     * 备注
     */
    private String pColorComment;
    /**
     * 是否多色
     */
    private String pColorIsMultiply;
    /**
     * 色板
     */
    private String pColorPlate;
    /**
     * 修改者
     */
    private String pColorModifier;

    /**
     * 创建时间
     */
    private Date pColorCreateDate;
    /**
     * 生效时间
     */
    private Date pColorEffectedDate;
    /**
     * 废止时间
     */
    private Date pColorAbolishDate;
    /**
     * 生效时间
     */
    private String strColorEffectedDate;
    /**
     * 废止时间
     */
    private String strColorAbolishDate;
    /**
     * 修改时间
     */
    private Date pColorModifyDate;
    /**
     * 可用状态,1生效，0草稿状态，-1废止状态
     */
    private Integer pColorStatus;

    /**
     * 是否删除,0删除，1现存
     */
    private Integer pColorIsDeleted;
    /**
     * 油漆物料号集合，以<br>进行字符串拆分
     */
    @Getter
    @Setter
    private String csPaintMaterielCodes;
    /**
     * 辅料库中的油漆物料号主键，以竖线'|'进行字符串拆分
     */
    @Getter
    @Setter
    private String csPaintMaterielUids;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

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

    public String getpColorComment() {
        return pColorComment;
    }

    public void setpColorComment(String pColorComment) {
        this.pColorComment = pColorComment;
    }

    public String getpColorIsMultiply() {
        return pColorIsMultiply;
    }

    public void setpColorIsMultiply(String pColorIsMultiply) {
        this.pColorIsMultiply = pColorIsMultiply;
    }

    public String getpColorPlate() {
        return pColorPlate;
    }

    public void setpColorPlate(String pColorPlate) {
        this.pColorPlate = pColorPlate;
    }

    public String getpColorModifier() {
        return pColorModifier;
    }

    public void setpColorModifier(String pColorModifier) {
        this.pColorModifier = pColorModifier;
    }

    public Date getpColorCreateDate() {
        return pColorCreateDate;
    }

    public void setpColorCreateDate(Date pColorCreateDate) {
        this.pColorCreateDate = pColorCreateDate;
    }

    public Date getpColorEffectedDate() {
        return pColorEffectedDate;
    }

    public void setpColorEffectedDate(Date pColorEffectedDate) {
        this.pColorEffectedDate = pColorEffectedDate;
    }

    public Date getpColorAbolishDate() {
        return pColorAbolishDate;
    }

    public void setpColorAbolishDate(Date pColorAbolishDate) {
        this.pColorAbolishDate = pColorAbolishDate;
    }

    public String getStrColorEffectedDate() {
        return strColorEffectedDate;
    }

    public void setStrColorEffectedDate(String strColorEffectedDate) {
        this.strColorEffectedDate = strColorEffectedDate;
    }

    public String getStrColorAbolishDate() {
        return strColorAbolishDate;
    }

    public void setStrColorAbolishDate(String strColorAbolishDate) {
        this.strColorAbolishDate = strColorAbolishDate;
    }

    public Date getpColorModifyDate() {
        return pColorModifyDate;
    }

    public void setpColorModifyDate(Date pColorModifyDate) {
        this.pColorModifyDate = pColorModifyDate;
    }

    public Integer getpColorStatus() {
        return pColorStatus;
    }

    public void setpColorStatus(Integer pColorStatus) {
        this.pColorStatus = pColorStatus;
    }

    public Integer getpColorIsDeleted() {
        return pColorIsDeleted;
    }

    public void setpColorIsDeleted(Integer pColorIsDeleted) {
        this.pColorIsDeleted = pColorIsDeleted;
    }
}