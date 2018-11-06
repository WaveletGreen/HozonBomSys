/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.modelColor;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配色方案附加bean
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzColorModel2 {

    //主键
    private String puid;
    //所属的车型
    private String modelUid;
    //特性外键
    private String pCfgUid;
    //颜色外键
    private String pColorUid;
    //特性颜色名
    private String colorName;
    //特性颜色code
    private String colorCode;
    //主配置UID
    private String pCfgMainUid;
    //创建时间
    private String createDate;
    //修改时间
    private String modifyDate;
    //创建者
    private String creator;
    //修改者
    private String modifier;
    //选项族名
    private String pOptionfamilyName;
    //选项族描述
    private String pOptionfamilyDesc;
    //油漆车身总成
    private String pModelShell;


    public String getPuid() {
        return puid;
    }

    public String getModelUid() {
        return modelUid;
    }

    public String getpCfgUid() {
        return pCfgUid;
    }

    public String getpColorUid() {
        return pColorUid;
    }

    public String getpCfgMainUid() {
        return pCfgMainUid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getModifier() {
        return modifier;
    }

    public String getpOptionfamilyName() {
        return pOptionfamilyName;
    }

    public String getpOptionfamilyDesc() {
        return pOptionfamilyDesc;
    }

    public String getpModelShell() {
        return pModelShell;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorCode() {
        return colorCode;
    }


    public void setPuid(String puid) {
        this.puid = puid;
    }

    public void setModelUid(String modelUid) {
        this.modelUid = modelUid;
    }

    public void setpCfgUid(String pCfgUid) {
        this.pCfgUid = pCfgUid;
    }

    public void setpColorUid(String pColorUid) {
        this.pColorUid = pColorUid;
    }

    public void setpCfgMainUid(String pCfgMainUid) {
        this.pCfgMainUid = pCfgMainUid;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public void setpOptionfamilyName(String pOptionfamilyName) {
        this.pOptionfamilyName = pOptionfamilyName;
    }

    public void setpOptionfamilyDesc(String pOptionfamilyDesc) {
        this.pOptionfamilyDesc = pOptionfamilyDesc;
    }

    public void setpModelShell(String pModelShell) {
        this.pModelShell = pModelShell;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
