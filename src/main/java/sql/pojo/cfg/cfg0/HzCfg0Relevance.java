/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.cfg0;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Deprecated
public class HzCfg0Relevance {
    //特性uid
    private String pCfgUid;
    //特性
    private String pOptionfamilyName;
    //特性值
    private String pCfg0ObjectId;
    //特性描述
    private String pCfg0Desc;
    //相关性代码
    private String cfg0Relevance;
    //颜色代码
    private String colorCode;
    //颜色描述
    private String colorDesc;


    public String getpCfgUid() {
        return pCfgUid;
    }

    public String getpOptionfamilyName() {
        return pOptionfamilyName;
    }

    public String getpCfg0ObjectId() {
        return pCfg0ObjectId;
    }

    public String getpCfg0Desc() {
        return pCfg0Desc;
    }

    public String getCfg0Relevance() {
        return cfg0Relevance;
    }

    public String getColorCode() { return colorCode; }

    public String getColorDesc() { return colorDesc; }


    public void setpCfgUid(String pCfgUid) {
        this.pCfgUid = pCfgUid;
    }

    public void setpOptionfamilyName(String pOptionfamilyName) {
        this.pOptionfamilyName = pOptionfamilyName;
    }

    public void setpCfg0ObjectId(String pCfg0ObjectId) {
        this.pCfg0ObjectId = pCfg0ObjectId;
    }

    public void setpCfg0Desc(String pCfg0Desc) {
        this.pCfg0Desc = pCfg0Desc;
    }

    public void setCfg0Relevance(String cfg0Relevance) {
        this.cfg0Relevance = cfg0Relevance;
    }

    public void setColorCode(String colorCode) { this.colorCode = colorCode; }

    public void setColorDesc(String colorDesc) { this.colorDesc = colorDesc; }

}
