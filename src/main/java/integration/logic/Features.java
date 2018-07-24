package integration.logic;

import integration.base.feature.ZPPTCI002;
import integration.option.ActionFlagOption;


/**
 * 特性映射
 */
public class Features {
    /**
     * 对应的SAP接口DTO
     */
    private ZPPTCI002 zpptci002;

    /**
     * 数据包号
     */
    private String packNo;
    /**
     * 行号
     */
    private String lineNum;
    /**
     * 动作描述代码
     */
    private String actionFlag;
    /**
     * 特性编码
     */
    private String featuresCode;
    /**
     * 特性描述
     */
    private String featuresDescribe;
    /**
     * 特性值编码
     */
    private String propertyValuesCode;
    /**
     * 特性值描述
     */
    private String propertyValuesDescribe;
    /**
     * 预留字段1
     */
    private String reservedField1;
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

    public Features() {
        zpptci002 = new ZPPTCI002();
    }

    public ZPPTCI002 getZpptci002() {
        return zpptci002;
    }

    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        zpptci002.setPPACKNO(packNo);
        this.packNo = packNo;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        zpptci002.setPITEM(lineNum);
        this.lineNum = lineNum;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(ActionFlagOption actionFlag) {
        this.actionFlag = actionFlag.GetDesc();
        zpptci002.setPACTIONID(actionFlag.GetDesc());
    }

    public String getFeaturesCode() {
        return featuresCode;
    }

    public void setFeaturesCode(String featuresCode) {
        zpptci002.setPATNAM(featuresCode);
        this.featuresCode = featuresCode;
    }

    public String getFeaturesDescribe() {
        return featuresDescribe;
    }

    public void setFeaturesDescribe(String featuresDescribe) {
        zpptci002.setPATBEZ(featuresDescribe);
        this.featuresDescribe = featuresDescribe;
    }

    public String getPropertyValuesCode() {
        return propertyValuesCode;
    }

    public void setPropertyValuesCode(String propertyValuesCode) {
        zpptci002.setPATWRT(propertyValuesCode);
        this.propertyValuesCode = propertyValuesCode;
    }

    public String getPropertyValuesDescribe() {
        return propertyValuesDescribe;
    }

    public void setPropertyValuesDescribe(String propertyValuesDescribe) {
        zpptci002.setPATWTB(propertyValuesDescribe);
        this.propertyValuesDescribe = propertyValuesDescribe;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) {
        zpptci002.setPRESERVE1(reservedField1);
        this.reservedField1 = reservedField1;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) {
        zpptci002.setPRESERVE2(reservedField2);
        this.reservedField2 = reservedField2;
    }

    public String getReservedField3() {
        return reservedField3;
    }

    public void setReservedField3(String reservedField3) {
        zpptci002.setPRESERVE3(reservedField3);
        this.reservedField3 = reservedField3;
    }

    public String getReservedField4() {
        return reservedField4;
    }

    public void setReservedField4(String reservedField4) {
        zpptci002.setPRESERVE4(reservedField4);
        this.reservedField4 = reservedField4;
    }

    public String getReservedField5() {
        return reservedField5;
    }

    public void setReservedField5(String reservedField5) {
        zpptci002.setPRESERVE5(reservedField5);
        this.reservedField5 = reservedField5;
    }
}
