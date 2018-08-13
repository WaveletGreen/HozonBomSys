package integration.logic;

import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;

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
    private String actionFlag;
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
    private String workNumber1;
    /**
     * 作业数量1,P_DATA2
     */
    private String workNumber2;
    /**
     * 作业数量1,P_DATA3
     */
    private String workNumber3;
    /**
     * 作业数量1,P_DATA4
     */
    private String workNumber4;
    /**
     * 作业数量1,P_DATA5
     */
    private String workNumber5;
    /**
     * 作业数量1,P_DATA6
     */
    private String workNumber6;


    public static ProcessRoute getProcessRoute(String materielId,HzWorkProcessService hzWorkProcessService){
        HzWorkProcessRespDTO respDTO = hzWorkProcessService.findHzWorkProcess(materielId, "e5969e81-0339-4e3a-98a9-a918f64e4289");
        ProcessRoute processRoute = new ProcessRoute();
        processRoute.setFactory(respDTO.getFactoryCode());
        processRoute.setMaterialCode(respDTO.getMaterielId());
        if(respDTO.getpCount()==null){
            processRoute.setBasedAmount("1");
        }else {
            processRoute.setBasedAmount(String.valueOf(respDTO.getpCount()));
        }
        processRoute.setEffectiveDate(respDTO.get);
    }

    /////////////////////////////setter/////////////////////////////

    public void setPackNo(String packNo) {
        this.packNo = packNo;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public void setBasedAmount(String basedAmount) {
        this.basedAmount = basedAmount;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public void setWorkNumber1(String workNumber1) {
        this.workNumber1 = workNumber1;
    }

    public void setWorkNumber2(String workNumber2) {
        this.workNumber2 = workNumber2;
    }

    public void setWorkNumber3(String workNumber3) {
        this.workNumber3 = workNumber3;
    }

    public void setWorkNumber4(String workNumber4) {
        this.workNumber4 = workNumber4;
    }

    public void setWorkNumber5(String workNumber5) {
        this.workNumber5 = workNumber5;
    }

    public void setWorkNumber6(String workNumber6) {
        this.workNumber6 = workNumber6;
    }



    /////////////////////////getter//////////////////////

    public String getPackNo() { return packNo; }

    public String getLineNum() { return lineNum; }

    public String getActionFlag() { return actionFlag; }

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

    public String getWorkNumber1() { return workNumber1; }

    public String getWorkNumber2() { return workNumber2; }

    public String getWorkNumber3() { return workNumber3; }

    public String getWorkNumber4() { return workNumber4; }

    public String getWorkNumber5() { return workNumber5; }

    public String getWorkNumber6() { return workNumber6; }
}
