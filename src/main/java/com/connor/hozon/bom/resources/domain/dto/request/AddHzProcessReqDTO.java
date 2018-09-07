package com.connor.hozon.bom.resources.domain.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
public class AddHzProcessReqDTO {
    /**
     * 工厂代码
     */
    private String factoryCode;
    /**
     * 用途
     */
    private String purpose;
    /**
     * 工作中心代码
     */
    private String pWorkCode;
    /**
     * 工作中心描述
     */
    private String pWorkDesc;
    /**
     * 工序代码
     */
    private String pProcedureCode;
    /**
     * 工序描述
     */
    private String pProcedureDesc;
    /**
     * 直接工时
     */
    private String pDirectLabor;
    /**
     * 间接工时
     */
    private String pIndirectLabor;
    /**
     * 机器工时
     */
    private String pMachineLabor;
    /**
     * 燃动能
     */
    private String pBurn;
    /**
     * 机物料消耗工时
     */
    private String pMachineMaterialLabor;
    /**
     * 其他费用
     */
    private String pOtherCost;
    /**
     * 基本数量
     */
    private Integer pCount;

    private String projectId;

    private String materielId;

    /**
     * 状态
     */
    private String state;
    /**
     * 控制码
     */
    private String controlCode;

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getpWorkCode() {
        return pWorkCode;
    }

    public void setpWorkCode(String pWorkCode) {
        this.pWorkCode = pWorkCode;
    }

    public String getpWorkDesc() {
        return pWorkDesc;
    }

    public void setpWorkDesc(String pWorkDesc) {
        this.pWorkDesc = pWorkDesc;
    }

    public String getpProcedureCode() {
        return pProcedureCode;
    }

    public void setpProcedureCode(String pProcedureCode) {
        this.pProcedureCode = pProcedureCode;
    }

    public String getpProcedureDesc() {
        return pProcedureDesc;
    }

    public void setpProcedureDesc(String pProcedureDesc) {
        this.pProcedureDesc = pProcedureDesc;
    }

    public String getpDirectLabor() {
        return pDirectLabor;
    }

    public void setpDirectLabor(String pDirectLabor) {
        this.pDirectLabor = pDirectLabor;
    }

    public String getpIndirectLabor() {
        return pIndirectLabor;
    }

    public void setpIndirectLabor(String pIndirectLabor) {
        this.pIndirectLabor = pIndirectLabor;
    }

    public String getpMachineLabor() {
        return pMachineLabor;
    }

    public void setpMachineLabor(String pMachineLabor) {
        this.pMachineLabor = pMachineLabor;
    }

    public String getpBurn() {
        return pBurn;
    }

    public void setpBurn(String pBurn) {
        this.pBurn = pBurn;
    }

    public String getpMachineMaterialLabor() {
        return pMachineMaterialLabor;
    }

    public void setpMachineMaterialLabor(String pMachineMaterialLabor) {
        this.pMachineMaterialLabor = pMachineMaterialLabor;
    }

    public String getpOtherCost() {
        return pOtherCost;
    }

    public void setpOtherCost(String pOtherCost) {
        this.pOtherCost = pOtherCost;
    }

    public Integer getpCount() {
        return pCount;
    }

    public void setpCount(Integer pCount) {
        this.pCount = pCount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }
}
