package com.connor.hozon.bom.resources.dbdo.config;

/**
 * Created by haozt on 2018/5/22
 */
public class HzCfg0Record {
    /**
     * puid
     */
    private String puid;
    /**
     *选项值的objectId
     */
    private String pCfg0ObjectId;

    /**HzCfg0RecordMapper.xml
     * 配置描述
     */
    private String pCfg0Desc;

    /**
     * 选项族的名称，也是选项族的objectId
     */
    private String pCfg0FamilyName;
    /**
     * 选项族的数据库puid
     */
    private String pCfg0FamilyPuid;
    /**
     * 主配置的puid，用这个可以找到主配置的对象
     */
    private String pCfg0MainItemPuid;

    /**
     * 特性名称
     */
    private String pH9FeatureName;

    /**
     * 特性英文名
     */
    private String pH9FeatureEnName;

    /**
     * 特性描述
     */
    private String pH9FeatureDesc;

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

    public String getpCfg0FamilyPuid() {
        return pCfg0FamilyPuid;
    }

    public void setpCfg0FamilyPuid(String pCfg0FamilyPuid) {
        this.pCfg0FamilyPuid = pCfg0FamilyPuid;
    }

    public String getpCfg0MainItemPuid() {
        return pCfg0MainItemPuid;
    }

    public void setpCfg0MainItemPuid(String pCfg0MainItemPuid) {
        this.pCfg0MainItemPuid = pCfg0MainItemPuid;
    }

    public String getpH9FeatureName() {
        return pH9FeatureName;
    }

    public void setpH9FeatureName(String pH9FeatureName) {
        this.pH9FeatureName = pH9FeatureName;
    }

    public String getpH9FeatureEnName() {
        return pH9FeatureEnName;
    }

    public void setpH9FeatureEnName(String pH9FeatureEnName) {
        this.pH9FeatureEnName = pH9FeatureEnName;
    }

    public String getpH9FeatureDesc() {
        return pH9FeatureDesc;
    }

    public void setpH9FeatureDesc(String pH9FeatureDesc) {
        this.pH9FeatureDesc = pH9FeatureDesc;
    }
}

