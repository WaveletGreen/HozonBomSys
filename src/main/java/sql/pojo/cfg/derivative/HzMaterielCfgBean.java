/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.derivative;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: SAP集成用
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzMaterielCfgBean {
    /**
     * 品牌代号
     */
    private String pBrandCode;
    /**
     * 品牌名称
     */
    private String pBrandName;
    /**
     * 平台代码
     */
    private String pPlatformCode;
    /**
     * 平台名称
     */
    private String pPlatformName;
    /**
     * 车型代码
     */
    private String pVehicleCode;
    /**
     * 车型名称
     */
    private String pVehicleName;
    /**
     * 内饰颜色代码
     */
    private String pInColorCode;
    /**
     * 内饰颜色名称
     */
    private String pInColorName;
    /**
     * 颜色代码
     */
    private String pColorCode;
    /**
     * 颜色名称
     */
    private String pColorName;
    /**
     * 电池型号
     */
    private String pBatteryModel;
    /**
     * 电机型号
     */
    private String pMotorModel;
    /**
     * 车型名称号码  整车编码
     */
    private String objectName;
    /**
     * 车型模型的主键
     */
    private String puid;
    /**
     * 是否已发送过SAP
     */
    private Integer isSent;
    /**
     * 项目主键，外键
     */
    private String projectUid;

    public String getpBrandCode() {
        return pBrandCode;
    }

    public void setpBrandCode(String pBrandCode) {
        this.pBrandCode = pBrandCode;
    }

    public String getpBrandName() {
        return pBrandName;
    }

    public void setpBrandName(String pBrandName) {
        this.pBrandName = pBrandName;
    }

    public String getpPlatformCode() {
        return pPlatformCode;
    }

    public void setpPlatformCode(String pPlatformCode) {
        this.pPlatformCode = pPlatformCode;
    }

    public String getpPlatformName() {
        return pPlatformName;
    }

    public void setpPlatformName(String pPlatformName) {
        this.pPlatformName = pPlatformName;
    }

    public String getpVehicleCode() {
        return pVehicleCode;
    }

    public void setpVehicleCode(String pVehicleCode) {
        this.pVehicleCode = pVehicleCode == null ? null : pVehicleCode.trim();
    }

    public String getpVehicleName() {
        return pVehicleName;
    }

    public void setpVehicleName(String pVehicleName) {
        this.pVehicleName = pVehicleName;
    }

    public String getpInColorCode() {
        return pInColorCode;
    }

    public void setpInColorCode(String pInColorCode) {
        this.pInColorCode = pInColorCode == null ? null : pInColorCode.trim();
    }

    public String getpInColorName() {
        return pInColorName;
    }

    public void setpInColorName(String pInColorName) {
        this.pInColorName = pInColorName;
    }

    public String getpColorCode() {
        return pColorCode;
    }

    public void setpColorCode(String pColorCode) {
        this.pColorCode = pColorCode == null ? null : pColorCode.trim();
    }

    public String getpColorName() {
        return pColorName;
    }

    public void setpColorName(String pColorName) {
        this.pColorName = pColorName;
    }

    public String getpBatteryModel() {
        return pBatteryModel;
    }

    public void setpBatteryModel(String pBatteryModel) {
        this.pBatteryModel = pBatteryModel;
    }

    public String getpMotorModel() {
        return pMotorModel;
    }

    public void setpMotorModel(String pMotorModel) {
        this.pMotorModel = pMotorModel;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }
}