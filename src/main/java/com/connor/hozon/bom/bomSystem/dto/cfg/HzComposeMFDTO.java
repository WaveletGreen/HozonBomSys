package com.connor.hozon.bom.bomSystem.dto.cfg;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配置物料特性传输对象，用于接收前端回传的配置物料
 * @Date: Created in 2018/8/30 14:15
 * @Modified By:
 */
public class HzComposeMFDTO {
    /**
     * 项目ID
     */
    private String projectUid;
    /**
     * 车型模型ID
     */
    private String modelUid;
    /**
     * 颜色车型ID
     */
    private String colorModel;
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

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }

    public String getModelUid() {
        return modelUid;
    }

    public void setModelUid(String modelUid) {
        this.modelUid = modelUid;
    }

    public String getColorModel() {
        return colorModel;
    }

    public void setColorModel(String colorModel) {
        this.colorModel = colorModel;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
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
        this.purchaseType = purchaseType;
    }

    public String getMrpController() {
        return mrpController;
    }

    public void setMrpController(String mrpController) {
        this.mrpController = mrpController;
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
        this.vinCode = vinCode;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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
}
