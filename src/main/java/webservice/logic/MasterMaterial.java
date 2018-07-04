package webservice.logic;

import webservice.base.masterMaterial.ZPPTCI001;
import webservice.option.ActionFlagOption;
import webservice.option.MaterialOption;

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
     * 物料中文描述
     */
    private String materialName;

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
     * MRP控制着，------后台控制
     */
    private String MRPController;

    /**
     * 公告号,ZGGH
     */
    private String annount;




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

    public void setPurchaseType(MaterialOption purchaseType) {
        zpptci001.setZBESKZ(purchaseType.GetDesc());
        this.purchaseType = purchaseType.GetDesc();
    }

    public String getMRPController() {
        return MRPController;
    }

    public void setMRPController(MaterialOption MRPController) {
        zpptci001.setZMRPC(MRPController.GetDesc());
        this.MRPController = MRPController.GetDesc();
    }

    public ZPPTCI001 getZpptci001() {
        return zpptci001;
    }

}
