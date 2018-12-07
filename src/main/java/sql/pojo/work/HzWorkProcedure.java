package sql.pojo.work;

import sql.pojo.BaseChangePOJO;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: haozt
 * @Date: 2018/6/29
 * @Description: 工艺路线
 */
public class HzWorkProcedure extends BaseChangePOJO {
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
     * 创建时间
     */
    private Date pCreateTime;
    /**
     * 更新时间
     */
    private Date pUpdateTime;

    /**
     * 创建人
     */
    private String pCreateName;

    /**
     * 更改人
     */
    private String pUpdateName;
    /**
     * 删除标志位
     */
    private Integer pStatus;

    private String projectId;

    private String materielId;

    private String purpose;

    /**
     * 物料代码
     */
    private String pMaterielCode;

    /**
     * 物料中文描述
     */
    private String pMaterielDesc;
    /**
     * 状态
     */
    private String state;
    /**
     * 控制码
     */
    private String controlCode;

    /**
     * 工厂ID
     */
    private String pFactoryId;

    /**
     * 类型 （1 半成品工艺路线 2整车工艺路线）
     */
    private Integer pType;

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
     * 是否已发送过SAP
     */
    private Integer isSent;

    private Integer dataType;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        if (this == obj) {
            return true;
        }
        if(obj instanceof HzWorkProcedure){
            HzWorkProcedure that = (HzWorkProcedure) obj;
            if(this.pMaterielCode.equals(that.pMaterielCode)){
                return true;
            }
            if(this.puid.equals(that.puid)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pMaterielCode);
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
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

    public String getMaterielId() {
        return materielId;
    }

    public void setMaterielId(String materielId) {
        this.materielId = materielId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(String pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public String getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(String pMaterielDesc) {
        this.pMaterielDesc = pMaterielDesc;
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

    public Integer getpStatus() {
        return pStatus;
    }

    public void setpStatus(Integer pStatus) {
        this.pStatus = pStatus;
    }

    public void setpFactoryId(String pFactoryId) {
        this.pFactoryId = pFactoryId;
    }

    public String getpFactoryId() {
        return pFactoryId;
    }

    public HzWorkProcedure clone(){
        HzWorkProcedure hzWorkProcedure = new HzWorkProcedure();
        hzWorkProcedure.setPuid(this.getPuid());
        hzWorkProcedure.setpWorkPuid(this.getpWorkPuid());
        hzWorkProcedure.setpProcedureCode(this.getpProcedureCode());
        hzWorkProcedure.setpProcedureDesc(this.getpProcedureDesc());
        hzWorkProcedure.setpDirectLabor(this.getpDirectLabor());
        hzWorkProcedure.setpIndirectLabor(this.getpIndirectLabor());
        hzWorkProcedure.setpMachineLabor(this.getpMachineLabor());
        hzWorkProcedure.setpBurn(this.getpBurn());
        hzWorkProcedure.setpMachineMaterialLabor(this.getpMachineMaterialLabor());
        hzWorkProcedure.setpOtherCost(this.getpOtherCost());
        hzWorkProcedure.setpCount(this.getpCount());
        hzWorkProcedure.setpCreateTime(this.getpCreateTime());
        hzWorkProcedure.setpUpdateTime(this.getpUpdateTime());
        hzWorkProcedure.setpCreateName(this.getpCreateName());
        hzWorkProcedure.setpUpdateName(this.getpUpdateName());
        hzWorkProcedure.setpStatus(this.getpStatus());
        hzWorkProcedure.setProjectId(this.getProjectId());
        hzWorkProcedure.setMaterielId(this.getMaterielId());
        hzWorkProcedure.setPurpose(this.getPurpose());
        hzWorkProcedure.setpMaterielCode(this.getpMaterielCode());
        hzWorkProcedure.setpMaterielDesc(this.getpMaterielDesc());
        hzWorkProcedure.setState(this.getState());
        hzWorkProcedure.setControlCode(this.getControlCode());
        hzWorkProcedure.setpFactoryId(this.getpFactoryId());
        return hzWorkProcedure;
    }
}
