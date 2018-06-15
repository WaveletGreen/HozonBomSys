package com.connor.hozon.bom.bomSystem.dto;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/9 14:19
 */
public class HzMaterielFeatureBean {
    /**
     * 配置值的puid
     */
    private String puid;
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
}
