/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.derivative;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配置物料特性表中衍生物料，充当衍生物料详情数据
 * <strong>目前衍生物料详情数据已不再存储，只能充当一张保留表的作用</strong>
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzCfg0ModelFeature {
    /**
     * 主键
     */

    private String puid;
    /**
     * 归属车型
     */
    private String pPertainToModel;
    /**
     * 归属颜色车身
     */
    private String pPertainToColorModel;
    /**
     * 基本信息描述
     */
    private String pFeatureCnDesc;
    /**
     * 基本信息代码
     * //     * 单车配置码
     */
    private String pFeatureSingleVehicleCode;
    /**
     * 工厂，默认是1001,作为物料数据传到SAP,一下的字段都是需要传到SAP的字段
     */
    private String factoryCode;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 旧物料编码
     */
    private String oldMaterielCode;
    /**
     * 物料中文描述
     */
    private String materielDesc;
    /**
     * 物料英文描述
     */
    private String materielEnDesc;
    /**
     * 基本计量单位
     */
    private String basicCountUnit;
    /**
     * 物料类型
     */
    private String materielType;
    /**
     * 虚拟件标识
     */
    private Integer virtualFlag;
    /**
     * 采购类型
     */
    private String purchaseType;
    /**
     * MRP控制者，衍生物料都是整车
     */
    private String mrpController;
    /**
     * 公告号，（只有MRP=整车才有，否则传空置）
     */
    private String bulletinNum;
    /**
     * vin前置码
     */
    private String vinCode;
    /**
     * 颜色件标识
     */
    private Integer color;
    /**
     * 毛重
     */
    private String grossWeight;
    /**
     * 内外饰标识
     */
    private Integer labelFlag;
    /**
     * 3C件标识
     */
    private Integer rulesFlag;
    /**
     * 删除标识
     */
    private Integer deleteFlag;
    /**
     * 重要度
     */
    private Integer importance;
    /**
     * 在materielFeature中是主键
     */
    private String modelFeaturePuid;
    /**
     * 主键
     */
    private String puidOfModelFeature;
    /**
     * 是否已发送过SAP
     */
    private Integer isSent;

    /**
     * 创建时间
     */
    private Date pMfCreateDate;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpPertainToModel() {
        return pPertainToModel;
    }

    public void setpPertainToModel(String pPertainToModel) {
        this.pPertainToModel = pPertainToModel == null ? null : pPertainToModel.trim();
    }

    public String getpPertainToColorModel() {
        return pPertainToColorModel;
    }

    public void setpPertainToColorModel(String pPertainToColorModel) {
        this.pPertainToColorModel = pPertainToColorModel == null ? null : pPertainToColorModel.trim();
    }

    public String getpFeatureCnDesc() {
        return pFeatureCnDesc;
    }

    public void setpFeatureCnDesc(String pFeatureCnDesc) {
        this.pFeatureCnDesc = pFeatureCnDesc;
    }

    public String getpFeatureSingleVehicleCode() {
        return pFeatureSingleVehicleCode;
    }

    public void setpFeatureSingleVehicleCode(String pFeatureSingleVehicleCode) {
        this.pFeatureSingleVehicleCode = pFeatureSingleVehicleCode;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode == null ? null : factoryCode.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getOldMaterielCode() {
        return oldMaterielCode;
    }

    public void setOldMaterielCode(String oldMaterielCode) {
        this.oldMaterielCode = oldMaterielCode;
    }

    public String getMaterielDesc() {
        return materielDesc;
    }

    public void setMaterielDesc(String materielDesc) {
        this.materielDesc = materielDesc;
    }

    public String getMaterielEnDesc() {
        return materielEnDesc;
    }

    public void setMaterielEnDesc(String materielEnDesc) {
        this.materielEnDesc = materielEnDesc;
    }

    public String getBasicCountUnit() {
        return basicCountUnit;
    }

    public void setBasicCountUnit(String basicCountUnit) {
        this.basicCountUnit = basicCountUnit;
    }

    public String getMaterielType() {
        return materielType;
    }

    public void setMaterielType(String materielType) {
        this.materielType = materielType;
    }

    public Integer getVirtualFlag() {
        return virtualFlag;
    }

    public void setVirtualFlag(Integer virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType == null ? null : purchaseType.trim();
    }

    public String getMrpController() {
        return mrpController;
    }

    public void setMrpController(String mrpController) {
        this.mrpController = mrpController == null ? null : mrpController.trim();
    }

    public String getBulletinNum() {
        return bulletinNum;
    }

    public void setBulletinNum(String bulletinNum) {
        this.bulletinNum = bulletinNum;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode == null ? null : vinCode.trim();
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color == null ? null : color;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Integer getLabelFlag() {
        return labelFlag;
    }

    public void setLabelFlag(Integer labelFlag) {
        this.labelFlag = labelFlag;
    }

    public Integer getRulesFlag() {
        return rulesFlag;
    }

    public void setRulesFlag(Integer rulesFlag) {
        this.rulesFlag = rulesFlag;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getModelFeaturePuid() {
        return modelFeaturePuid;
    }

    public void setModelFeaturePuid(String modelFeaturePuid) {
        this.modelFeaturePuid = modelFeaturePuid;
    }

    public String getPuidOfModelFeature() {
        return puidOfModelFeature;
    }

    public void setPuidOfModelFeature(String puidOfModelFeature) {
        this.puidOfModelFeature = puidOfModelFeature;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public Date getpMfCreateDate() {
        return pMfCreateDate;
    }

    public void setpMfCreateDate(Date pMfCreateDate) {
        this.pMfCreateDate = pMfCreateDate;
    }
}