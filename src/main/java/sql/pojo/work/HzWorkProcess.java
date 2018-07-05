package sql.pojo.work;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description: 工艺
 */
public class HzWorkProcess {

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
     * 类型 （1 半成品工艺路线 2整车工艺路线  3总成分总成工艺路线  ）
     */
    private Integer pType;


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
     * 创建者
     */
    private String pCreateName;
    /**
     * 更新者
     */
    private String pUpdateName;

    private Date pCreateTime;

    private Date pUpdateTime;

    public String getpCreateName() {
        return pCreateName;
    }

    public void setpCreateName(String pCreateName) {
        this.pCreateName = pCreateName;
    }

    public String getpUpdateName() {
        return pUpdateName;
    }

    public void setpUpdateName(String pUpdateName) {
        this.pUpdateName = pUpdateName;
    }

    public Date getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(Date pCreateTime) {
        this.pCreateTime = pCreateTime;
    }

    public Date getpUpdateTime() {
        return pUpdateTime;
    }

    public void setpUpdateTime(Date pUpdateTime) {
        this.pUpdateTime = pUpdateTime;
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

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
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
