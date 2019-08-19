/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
public class HzWorkProcessRespDTO extends BaseChangeRespDTO{
    private Integer No;
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
    private String pWorkCode;

    /**
     * 工作中心描述
     */
    private String pWorkDesc;
    /**
     * 用途
     */
    private String purpose;
    /**
     * 状态
     */
    private String state;
    /**
     * 控制码
     */
    private String controlCode;
    /**
     * 是否已发送过SAP
     */
    private Integer isSent;
    /**
     * 数据状态 删除状态 草稿状态 审核中等
     * 因为工艺路本身有一个字段名为 状态
     * 这里做一下区分
     */
    private Integer status;


    private Integer dataType;

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

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

    public Integer getIsSent() { return isSent; }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
