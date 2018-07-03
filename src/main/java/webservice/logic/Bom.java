package webservice.logic;

import webservice.base.bom.ZPPTCI005;
import webservice.option.ActionFlagOption;
import webservice.option.BomOption;

public class Bom {
    /**
     * 对应的SAP接口DTO
     */
    private ZPPTCI005 zpptci005;

    public Bom() {
        zpptci005 = new ZPPTCI005();
    }

    /**
     * 数据包号，随机数产生，ZPACKNO
     */
    private String packNo;
    /**
     * 行号，ZITEM
     */
    private String lineNum;

    /**
     * 动作描述代码,ZACTIONID
     */
    private String actionFlag;

    /**
     * 更改编号，ZAENNR,创建BOM可不输入，删除（D）和修改（U）必须有ECN（更改编号）
     */
    private String modifyCode;
    /**
     * 工厂,ZWERKS
     */
    private String factory;
    /**
     * BOM类型
     */
    private String bomType;
    /**
     * bomline首行,ZMATNR
     */
    private String headOfBomLine;

    /**
     * bomline下的子行,ZMATNR_C
     */
    private String childOfBomLine;
    /**
     * BOM顺序,ZSORTF
     */
    private String orderOfBomLine;
    /**
     * 数量,ZMENGE
     */
    private String number;

    /**
     * 单位,ZMEINS
     */
    private String unit;
    /**
     * 发料库存地点，Zlocation
     */
    private String stockLocation;

    /**
     * 装配位置，ZZPWZ
     */
    private String assemblyPoint;

    /**
     * 使用车间,ZWORKS
     */
    private String useWorkshop;

    /**
     * 工位,P_STATION
     */
    private String station;


    /////////////////////////////////////////setter/////////////////////////////

    public void setPackNo(String packNo) {
        zpptci005.setZPACKNO(packNo);
        this.packNo = packNo;
    }

    public void setLineNum(String lineNum) {
        zpptci005.setZITEM(lineNum);
        this.lineNum = lineNum;
    }

    public void setActionFlag(ActionFlagOption flag) {
        zpptci005.setZACTIONID(flag.GetDesc());
        this.actionFlag = flag.GetDesc();
    }

    public void setModifyCode(String modifyCode) {
        this.modifyCode = modifyCode;
        zpptci005.setZAENNR(modifyCode);
    }

    public void setFactory(String factory) {
        zpptci005.setZWERKS(factory);
        this.factory = factory;
    }

    public void setBomType(BomOption bomType) {
        this.bomType = bomType.GetDesc();
        zpptci005.setZUSE(bomType.GetDesc());
    }

    public void setHeadOfBomLine(String headOfBomLine) {
        zpptci005.setZMATNR(headOfBomLine);
        this.headOfBomLine = headOfBomLine;
    }

    public void setChildOfBomLine(String childOfBomLine) {
        zpptci005.setZMATNRC(headOfBomLine);
        this.childOfBomLine = childOfBomLine;
    }

    public void setOrderOfBomLine(String orderOfBomLine) {
        zpptci005.setZSORTF(orderOfBomLine);
        this.orderOfBomLine = orderOfBomLine;
    }

    public void setNumber(String number) {
        zpptci005.setZMENGE(number);
        this.number = number;
    }

    public void setUnit(String unit) {
        zpptci005.setZMEINS(unit);
        this.unit = unit;
    }

    public void setStockLocation(String stockLocation) {
        zpptci005.setZLOCATION(stockLocation);
        this.stockLocation = stockLocation;
    }

    public void setAssemblyPoint(String assemblyPoint) {
        zpptci005.setZZPWZ(assemblyPoint);
        this.assemblyPoint = assemblyPoint;
    }

    public void setUseWorkshop(String useWorkshop) {
        zpptci005.setZWORKS(useWorkshop);
        this.useWorkshop = useWorkshop;
    }

    public void setStation(String station) {
        zpptci005.setZSTATION(station);
        this.station = station;
    }


    /////////////////////////////////////////getter/////////////////////////////

    public ZPPTCI005 getZpptci005() {
        return zpptci005;
    }

    public String getPackNo() {
        return packNo;
    }

    public String getLineNum() {
        return lineNum;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public String getModifyCode() {
        return modifyCode;
    }

    public String getFactory() {
        return factory;
    }

    public String getBomType() {
        return bomType;
    }

    public String getHeadOfBomLine() {
        return headOfBomLine;
    }

    public String getChildOfBomLine() {
        return childOfBomLine;
    }

    public String getOrderOfBomLine() {
        return orderOfBomLine;
    }

    public String getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }

    public String getStockLocation() {
        return stockLocation;
    }

    public String getAssemblyPoint() {
        return assemblyPoint;
    }

    public String getUseWorkshop() {
        return useWorkshop;
    }

    public String getStation() {
        return station;
    }
}
