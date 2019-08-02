/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.feature;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: x
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class HzMaterielFeatureBean {
    /**
     * 配置值的puid
     */
    private String puid;
    /**
     * 特性的puid
     */
    private String pCfg0FamilyPuid;
    /**
     * 配置值
     */
    private String pCfg0ObjectId;
    /**
     * 配置值描述
     */
    private String pCfg0Desc;
    /**
     * 配置组的值
     */
    private String pCfg0FamilyName;
    /**
     * 配置族的描述
     */
    private String pCfg0FamilyDesc;
    /**
     * 车型模型的名称
     */
    private String objectName;
    /***
     * 车型模型的基本信息
     */
    private String pCfg0ModelBasicDetail;
    /**
     * 车型模型的puid
     */
    private String pCfg0ModelRecord;
    /**
     * 主配置puid
     */
    private String pOfCfg0MainRecord;

    /**
     * 是否已发送过SAP
     */
    private Integer isSent;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpCfg0ObjectId() {
        return pCfg0ObjectId;
    }

    public void setpCfg0ObjectId(String pCfg0ObjectId) {
        this.pCfg0ObjectId = pCfg0ObjectId;
    }

    public String getpCfg0Desc() {
        return pCfg0Desc;
    }

    public void setpCfg0Desc(String pCfg0Desc) {
        this.pCfg0Desc = pCfg0Desc;
    }

    public String getpCfg0FamilyName() {
        return pCfg0FamilyName;
    }

    public void setpCfg0FamilyName(String pCfg0FamilyName) {
        this.pCfg0FamilyName = pCfg0FamilyName;
    }

    public String getpCfg0FamilyDesc() {
        return pCfg0FamilyDesc;
    }

    public void setpCfg0FamilyDesc(String pCfg0FamilyDesc) {
        this.pCfg0FamilyDesc = pCfg0FamilyDesc;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getpCfg0ModelRecord() {
        return pCfg0ModelRecord;
    }

    public void setpCfg0ModelRecord(String pCfg0ModelRecord) {
        this.pCfg0ModelRecord = pCfg0ModelRecord;
    }

    public String getpOfCfg0MainRecord() {
        return pOfCfg0MainRecord;
    }

    public void setpOfCfg0MainRecord(String pOfCfg0MainRecord) {
        this.pOfCfg0MainRecord = pOfCfg0MainRecord;
    }

    public String getpCfg0ModelBasicDetail() {
        return pCfg0ModelBasicDetail;
    }

    public void setpCfg0ModelBasicDetail(String pCfg0ModelBasicDetail) {
        this.pCfg0ModelBasicDetail = pCfg0ModelBasicDetail;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public String getpCfg0FamilyPuid() {
        return pCfg0FamilyPuid;
    }

    public void setpCfg0FamilyPuid(String pCfg0FamilyPuid) {
        this.pCfg0FamilyPuid = pCfg0FamilyPuid;
    }
}
