package webservice.logic;

import webservice.base.options.ZPPTCI004;
import webservice.option.ActionFlagOption;
import webservice.option.CorrelateTypeOption;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Correlate {
    /**
     * 对应的SAP接口DTO
     */
    private ZPPTCI004 zpptci004;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Correlate() {
        zpptci004 = new ZPPTCI004();
    }

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
     * 相关性
     */
    private String correlate;
    /**
     * 相关性描述
     */
    private String correlateDescript;
    /**
     * 相关性类型
     */
    private String correlateType;
    /**
     * 相关性状态
     */
    private String correlateState;
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 修改日期
     */
    private String modifyDate;
    /**
     * 相关性代码
     */
    private String correlateCode;
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

    public ZPPTCI004 getZpptci004() {
        return zpptci004;
    }

    public String getPackNo() {
        return packNo;
    }

    public void setPackNo(String packNo) {
        zpptci004.setZPACKNO(packNo);
        this.packNo = packNo;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        zpptci004.setZITEM(lineNum);
        this.lineNum = lineNum;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(ActionFlagOption flag) {
        zpptci004.setZACTIONID(flag.GetDesc());
        this.actionFlag = flag.GetDesc();
    }

    public String getCorrelate() {
        return correlate;
    }

    public void setCorrelate(String correlate) {
        zpptci004.setZKNNAM(correlate);
        this.correlate = correlate;
    }

    public String getCorrelateDescript() {
        return correlateDescript;
    }

    public void setCorrelateDescript(String correlateDescript) {
        zpptci004.setZKNKTX(correlateDescript);
        this.correlateDescript = correlateDescript;
    }

    public String getCorrelateType() {
        return correlateType;
    }

    public void setCorrelateType(CorrelateTypeOption type) {
        zpptci004.setZKNART(type.GetDesc());
        this.correlateType = type.GetDesc();
    }

    public String getCorrelateState() {
        return correlateState;
    }

    public void setCorrelateState(String correlateState) {
        zpptci004.setZKNSTA(correlateState);
        this.correlateState = correlateState;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date date) {
        createDate = sdf.format(date);
        zpptci004.setZKNEDT(createDate);
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date date) {
        modifyDate = sdf.format(date);
        zpptci004.setZKNADT(modifyDate);
        this.modifyDate = modifyDate;
    }

    public String getCorrelateCode() {
        return correlateCode;
    }

    public void setCorrelateCode(String correlateCode) {
        zpptci004.setZKNNAMCODE(correlateCode);
        this.correlateCode = correlateCode;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) {
        zpptci004.setRESERVE01(reservedField1);
        this.reservedField1 = reservedField1;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) {
        zpptci004.setRESERVE02(reservedField2);
        this.reservedField2 = reservedField2;
    }

    public String getReservedField3() {
        return reservedField3;
    }

    public void setReservedField3(String reservedField3) {
        zpptci004.setRESERVE03(reservedField3);
        this.reservedField3 = reservedField3;
    }

    public String getReservedField4() {
        return reservedField4;
    }

    public void setReservedField4(String reservedField4) {
        zpptci004.setRESERVE04(reservedField4);
        this.reservedField4 = reservedField4;
    }

    public String getReservedField5() {
        return reservedField5;
    }

    public void setReservedField5(String reservedField5) {
        zpptci004.setRESERVE05(reservedField5);
        this.reservedField5 = reservedField5;
    }
}
