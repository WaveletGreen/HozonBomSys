package com.connor.hozon.bom.resources.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
public class HzWorkProcessRespDTO {
    /**
     * 主键id
     */
    private String puid;
    /**
     * 工作中心id
     */
    private String pWorkPuid;
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

    /**
     * 外键 物料信息id
     */
    private String materielId;

    /**
     * 物料代码
     */
    private Object pMaterielCode;

    /**
     * 物料中文描述
     */
    private Object pMaterielDesc;
    /**
     * 工厂代码
     */
    private String factoryCode;

    /**
     * 工作中心代码
     */
    private String workCenterCode;

    /**
     * 工作中心描述
     */
    private String workCenterDesc;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpWorkPuid() {
        return pWorkPuid;
    }

    public void setpWorkPuid(String pWorkPuid) {
        this.pWorkPuid = pWorkPuid;
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

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public Object getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(Object pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public Object getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(Object pMaterielDesc) {
        this.pMaterielDesc = pMaterielDesc;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getWorkCenterCode() {
        return workCenterCode;
    }

    public void setWorkCenterCode(String workCenterCode) {
        this.workCenterCode = workCenterCode;
    }

    public String getWorkCenterDesc() {
        return workCenterDesc;
    }

    public void setWorkCenterDesc(String workCenterDesc) {
        this.workCenterDesc = workCenterDesc;
    }
}
