package webservice.logic;

import webservice.base.masterMaterial.ZPPTCI001;
import webservice.option.ActionFlagOption;
import webservice.option.MRPControlOption;
import webservice.option.MaterialOption;
import webservice.option.PurchaseOption;

public class MasterMaterial {

    /**
     * 对应的SAP接口DTO
     */
    private ZPPTCI001 zpptci001;

    public MasterMaterial() {
        zpptci001 = new ZPPTCI001();
    }

    /**
     * 数据包号，随机数产生 -----后台控制
     */
    private String GUID;
    /**
     * 行号，------后台控制
     */
    private String lineNum;

    /**
     * 工厂
     */
    private String factory;

    /**
     * 动作描述代码，-------后台控制
     */
    private String actionFlag;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 旧物料编码
     */
    private String oldMaterialCode;
    /**
     * 物料中文描述
     */
    private String materialName;
    /**
     * 物料英文描述
     */
    private String materialEnglishName;
    /**
     * 基本单位
     */
    private String unit;
    /**
     * 物料类型
     */
    private String materialType;
    /***
     * 虚拟件标识
     */
    private String vertureFlag;
    /**
     * 采购类型
     */
    private String purchaseType;
    /**
     * VIN前置号
     */
    private String perVIN;
    /**
     * 颜色件标识
     */
    private String colorFlag;
    /**
     * 毛重
     */
    private String grossWeight;
    /**
     * 内外饰标识
     */
    private String inAndOutFlag;
    /**
     * 3C件标识
     */
    private String threeCFlag;
    /**
     * 零件重要度
     */
    private String partImportance;
    /**
     * 删除标示
     */
    private String delFlag;
    /**
     * MRP控制着，------后台控制
     */
    private String MRPController;
    /**
     * 散件标示
     */
    private String bulkFlag;
    /**
     * 备件+原材料双属性标示
     */
    private String doubleAttribute;
    /**
     * 预留字段2
     */
    private String reservedField2;
    /**
     * 预留字段3
     */
    private String reservedField3;
    /**
     * 预留字段4
     */
    private String reservedField4;
    /**
     * 预留字段5
     */
    private String reservedField5;
    /**
     * 公告号，新加入的,注意：SAP没有发布新的字段
     */
    private String announcement;

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        zpptci001.setGUID(GUID);
        this.GUID = GUID;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        zpptci001.setZITEM(lineNum);
        this.lineNum = lineNum;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        zpptci001.setZWERKS(factory);
        this.factory = factory;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(ActionFlagOption flag) {
        zpptci001.setZACTIONID(flag.GetDesc());
        this.actionFlag = flag.GetDesc();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        zpptci001.setZMATNR(materialCode);
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        zpptci001.setZMAKTX(materialName);
        this.materialName = materialName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        zpptci001.setZMEINS(unit);
        this.unit = unit;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialOption materialType) {
        zpptci001.setZMTART(materialType.GetDesc());
        this.materialType = materialType.GetDesc();
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
        zpptci001.setZMTART(materialType);

    }

    public String getVertureFlag() {
        return vertureFlag;
    }

    public void setVertureFlag(String vertureFlag) {
        zpptci001.setZMATKL(vertureFlag);
        this.vertureFlag = vertureFlag;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    /*public void setPurchaseType(MaterialOption purchaseType) {
        zpptci001.setZBESKZ(purchaseType.GetDesc());
        this.purchaseType = purchaseType.GetDesc();
    }*/

    public String getMRPController() {
        return MRPController;
    }

    public void setMRPController(String MRPController) {
        zpptci001.setZMRPC(MRPController);
        this.MRPController = MRPController;
    }

    public ZPPTCI001 getZpptci001() {
        return zpptci001;
    }

    public String getOldMaterialCode() {
        return oldMaterialCode;
    }

    public void setOldMaterialCode(String oldMaterialCode) {
        zpptci001.setZBISMT(oldMaterialCode);
        this.oldMaterialCode = oldMaterialCode;
    }

    public String getMaterialEnglishName() {
        return materialEnglishName;
    }

    public void setMaterialEnglishName(String materialEnglishName) {
        zpptci001.setZMAKTXE(materialEnglishName);
        this.materialEnglishName = materialEnglishName;
    }

    public String getPerVIN() {
        return perVIN;
    }

    public void setPerVIN(String perVIN) {
        zpptci001.setZVIN(perVIN);
        this.perVIN = perVIN;
    }

    public String getColorFlag() {
        return colorFlag;
    }

    public void setColorFlag(String colorFlag) {
        zpptci001.setZCOLOR(colorFlag);
        this.colorFlag = colorFlag;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        zpptci001.setZBRGEW(grossWeight);
        this.grossWeight = grossWeight;
    }

    public String getInAndOutFlag() {
        return inAndOutFlag;
    }

    public void setInAndOutFlag(String inAndOutFlag) {
        zpptci001.setZLABEL(inAndOutFlag);
        this.inAndOutFlag = inAndOutFlag;
    }

    public String getThreeCFlag() {
        return threeCFlag;
    }

    public void setThreeCFlag(String threeCFlag) {
        zpptci001.setZRULES(threeCFlag);
        this.threeCFlag = threeCFlag;
    }

    public String getPartImportance() {
        return partImportance;
    }

    public void setPartImportance(String partImportance) {
        zpptci001.setZIMPOT(partImportance);
        this.partImportance = partImportance;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        zpptci001.setZDELETE(delFlag);
        this.delFlag = delFlag;
    }

    public String getBulkFlag() {
        return bulkFlag;
    }

    public void setBulkFlag(String bulkFlag) {
        zpptci001.setZSJBS(bulkFlag);
        this.bulkFlag = bulkFlag;
    }

    public String getDoubleAttribute() {
        return doubleAttribute;
    }

    public void setDoubleAttribute(String doubleAttribute) {
        zpptci001.setRESERVE01(doubleAttribute);
        this.doubleAttribute = doubleAttribute;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) {
        zpptci001.setRESERVE02(reservedField2);
        this.reservedField2 = reservedField2;
    }

    public String getReservedField3() {
        return reservedField3;
    }

    public void setReservedField3(String reservedField3) {
        zpptci001.setRESERVE03(reservedField3);
        this.reservedField3 = reservedField3;
    }

    public String getReservedField4() {
        return reservedField4;
    }

    public void setReservedField4(String reservedField4) {
        zpptci001.setRESERVE04(reservedField4);
        this.reservedField4 = reservedField4;
    }

    public String getReservedField5() {
        return reservedField5;
    }

    public void setReservedField5(String reservedField5) {
        zpptci001.setRESERVE05(reservedField5);
        this.reservedField5 = reservedField5;
    }

    public void setMRPController(MRPControlOption mrpAssembly) {
        zpptci001.setZMRPC(mrpAssembly.GetDesc());
        this.MRPController = mrpAssembly.GetDesc();
    }

    public void setPurchaseType(PurchaseOption purchaseEmpty) {
        zpptci001.setZBESKZ(purchaseEmpty.GetDesc());
        this.purchaseType = purchaseEmpty.GetDesc();
    }

    public void setPurchaseType(String purchaseType) {
        zpptci001.setZBESKZ(purchaseType);
        this.purchaseType = purchaseType;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}
