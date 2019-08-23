package integration.logic;

import com.connor.hozon.resources.service.work.HzWorkProcessService;
import integration.base.processRoute.ZPPTCI006;
import integration.option.ActionFlagOption;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工艺路线接口映射been
 */
public class ProcessRoute {
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
    private ActionFlagOption actionFlag;
    /**
     * 工厂,ZWERKS
     */
    private String factory;
    /**
     * 物料编码,P_MATNR
     */
    private String materialCode;
    /**
     * 基本数量,P_BASED
     */
    private String basedAmount;
    /**
     * 有效日期自,P_DATE
     */
    private String effectiveDate;
    /**
     * 用途,P_USE
     */
    private String use;
    /**
     * 状态,P_STA
     */
    private String status;
    /**
     * 工序序号,P_number
     */
    private String processNumber;
    /**
     * 工作中心,P_WORK
     */
    private String workCenter;
    /**
     * 控制码,P_CON
     */
    private String  controlCode;
    /**
     * 工序描述,P_ROUT
     */
    private String  processDescription;
    /**
     * 作业数量1,P_DATA1
     */
    private BigDecimal workNumber1;
    /**
     * 作业数量1,P_DATA2
     */
    private BigDecimal workNumber2;
    /**
     * 作业数量1,P_DATA3
     */
    private BigDecimal workNumber3;
    /**
     * 作业数量1,P_DATA4
     */
    private BigDecimal workNumber4;
    /**
     * 作业数量1,P_DATA5
     */
    private BigDecimal workNumber5;
    /**
     * 作业数量1,P_DATA6
     */
    private BigDecimal workNumber6;

    private ZPPTCI006 zpptci006;

    /**
     * 无参构造函数
     */
    public ProcessRoute(){
        this.zpptci006 = new ZPPTCI006();
        this.setWorkNumber1(new BigDecimal(1));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        this.setEffectiveDate(date);
    }



    /////////////////////////////setter/////////////////////////////

    public void setPackNo(String packNo) {
        zpptci006.setPPACKNO(packNo);
        this.packNo = packNo;
    }

    public void setLineNum(String lineNum) {
        zpptci006.setPITEM(lineNum);
        this.lineNum = lineNum;
    }

    public void setActionFlag(ActionFlagOption actionFlag) {
        zpptci006.setPACTIONID(actionFlag.GetDesc());
        this.actionFlag = actionFlag;
    }

    public void setFactory(String factory) {
        zpptci006.setPWERKS(factory);
        this.factory = factory;
    }

    public void setMaterialCode(String materialCode) {
        zpptci006.setPMATNR(materialCode);
        this.materialCode = materialCode;
    }

    public void setBasedAmount(String basedAmount) {
        zpptci006.setPBASED(basedAmount);
        this.basedAmount = basedAmount;
    }

    public void setEffectiveDate(String effectiveDate) {
        zpptci006.setPDATE(effectiveDate);
        this.effectiveDate = effectiveDate;
    }

    public void setUse(String use) {
        zpptci006.setPUSE(use);
        this.use = use;
    }

    public void setStatus(String status) {
        zpptci006.setPSTA(status);
        this.status = status;
    }

    public void setProcessNumber(String processNumber) {
        zpptci006.setPNUMBER(processNumber);
        this.processNumber = processNumber;
    }

    public void setWorkCenter(String workCenter) {
        zpptci006.setPWORK(workCenter);
        this.workCenter = workCenter;
    }

    public void setControlCode(String controlCode) {
        zpptci006.setPCON(controlCode);
        this.controlCode = controlCode;
    }

    public void setProcessDescription(String processDescription) {
        zpptci006.setPROUT(processDescription);
        this.processDescription = processDescription;
    }

    public void setWorkNumber1(BigDecimal workNumber1) {
        zpptci006.setPDATA1(workNumber1);
        this.workNumber1 = workNumber1;
    }

    public void setWorkNumber2(BigDecimal workNumber2) {
        zpptci006.setPDATA2(workNumber2);
        this.workNumber2 = workNumber2;
    }

    public void setWorkNumber3(BigDecimal workNumber3) {
        zpptci006.setPDATA3(workNumber3);
        this.workNumber3 = workNumber3;
    }

    public void setWorkNumber4(BigDecimal workNumber4) {
        zpptci006.setPDATA4(workNumber4);
        this.workNumber4 = workNumber4;
    }

    public void setWorkNumber5(BigDecimal workNumber5) {
        zpptci006.setPDATA5(workNumber5);
        this.workNumber5 = workNumber5;
    }

    public void setWorkNumber6(BigDecimal workNumber6) {
        zpptci006.setPDATA6(workNumber6);
        this.workNumber6 = workNumber6;
    }



    /////////////////////////getter//////////////////////

    public String getPackNo() { return packNo; }

    public String getLineNum() { return lineNum; }

    public ActionFlagOption getActionFlag() { return actionFlag; }

    public String getFactory() { return factory; }

    public String getMaterialCode() { return materialCode; }

    public String getBasedAmount() { return basedAmount; }

    public String getEffectiveDate() { return effectiveDate; }

    public String getUse() { return use; }

    public String getStatus() { return status; }

    public String getProcessNumber() { return processNumber; }

    public String getWorkCenter() { return workCenter; }

    public String getControlCode() { return controlCode; }

    public String getProcessDescription() { return processDescription; }

    public BigDecimal getWorkNumber1() { return workNumber1; }

    public BigDecimal getWorkNumber2() { return workNumber2; }

    public BigDecimal getWorkNumber3() { return workNumber3; }

    public BigDecimal getWorkNumber4() { return workNumber4; }

    public BigDecimal getWorkNumber5() { return workNumber5; }

    public BigDecimal getWorkNumber6() { return workNumber6; }

    public ZPPTCI006 getZpptci006() { return zpptci006;  }
}
