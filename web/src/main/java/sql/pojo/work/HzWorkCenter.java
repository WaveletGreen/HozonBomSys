package sql.pojo.work;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: haozt
 * @Date: 2018/6/29
 * @Description: 工作中心主数据
 */
public class HzWorkCenter implements Serializable {
    private static final long serialVersionUID = 7231886716932180279L;
    /**
     * 主键id
     */
    private String puid;

    /**
     * 工厂id
     */
    private String pFactoryPuid;
    /**
     * 工作中心代码
     */
    private String pWorkCode;
    /**
     * 工作中心描述
     */
    private String pWorkDesc;
    /**
     * 工作中心类别
     */
    private String pWorkType;
    /**
     * 用途
     */
    private String pPurpose;
    /**
     * 标准值码
     */
    private String pStandardCode;
    /**
     * 控制码
     */
    private String pControlCode;
    /**
     * 直接人工工时
     */
    private String pDirectLabor;
    /**
     * 间接人工工时
     */
    private String pIndirectLabor;
    /**
     * 机器工时
     */
    private String pMachineLabor;
    /**
     * 燃动
     */
    private String pBurn;
    /**
     * 机物料
     */
    private String pMachineMaterial;
    /**
     * 其他费用
     */
    private String pOtherCost;
    /**
     * 加工公式
     */
    private String pProcessExression;
    /**
     * 能力类别
     */
    private String pAbilityType;
    /**
     * 开始时间
     */
    private String pStartTime;
    /**
     * 结束时间
     */
    private String pEndTime;
    /**
     * 休息时间
     */
    private String pRestTime;
    /**
     * 能力数量
     */
    private String pAbilityCount;
    /**
     * 调度公式
     */
    private String pDispatchExpression;
    /**
     * 成本中心
     */
    private String pCostCenter;
    /**
     * 作业类型1
     */
    private String pTaskType1;
    /**
     * 作业类型2
     */
    private String pTaskType2;
    /**
     * 作业类型3
     */
    private String pTaskType3;
    /**
     * 作业类型4
     */
    private String pTaskType4;
    /**
     * 作业类型5
     */
    private String pTaskType5;
    /**
     * 作业类型6
     */
    private String pTaskType6;
    /**
     * 公式1
     */
    private String pExperssion1;
    /**
     * 公式2
     */
    private String pExperssion2;
    /**
     * 公式3
     */
    private String pExperssion3;
    /**
     * 公式4
     */
    private String pExperssion4;
    /**
     * 公式5
     */
    private String pExperssion5;
    /**
     * 公式6
     */
    private String pExperssion6;
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
    /**
     * 项目id
     */
    private String projectId;

    private String pFactoryId;
    private String pFactoryCode;

    public String getpFactoryId() {
        return pFactoryId;
    }

    public void setpFactoryId(String pFactoryId) {
        this.pFactoryId = pFactoryId;
    }

    public String getpFactoryCode() {
        return pFactoryCode;
    }

    public void setpFactoryCode(String pFactoryCode) {
        this.pFactoryCode = pFactoryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HzWorkCenter that = (HzWorkCenter) o;
        return Objects.equals(puid, that.puid) &&
                Objects.equals(pWorkCode, that.pWorkCode) &&
                Objects.equals(pWorkDesc, that.pWorkDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(puid, pWorkCode, pWorkDesc);
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

    public String getpFactoryPuid() {
        return pFactoryPuid;
    }

    public void setpFactoryPuid(String pFactoryPuid) {
        this.pFactoryPuid = pFactoryPuid;
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

    public String getpWorkType() {
        return pWorkType;
    }

    public void setpWorkType(String pWorkType) {
        this.pWorkType = pWorkType;
    }

    public String getpPurpose() {
        return pPurpose;
    }

    public void setpPurpose(String pPurpose) {
        this.pPurpose = pPurpose;
    }

    public String getpStandardCode() {
        return pStandardCode;
    }

    public void setpStandardCode(String pStandardCode) {
        this.pStandardCode = pStandardCode;
    }

    public String getpControlCode() {
        return pControlCode;
    }

    public void setpControlCode(String pControlCode) {
        this.pControlCode = pControlCode;
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

    public String getpMachineMaterial() {
        return pMachineMaterial;
    }

    public void setpMachineMaterial(String pMachineMaterial) {
        this.pMachineMaterial = pMachineMaterial;
    }

    public String getpOtherCost() {
        return pOtherCost;
    }

    public void setpOtherCost(String pOtherCost) {
        this.pOtherCost = pOtherCost;
    }

    public String getpProcessExression() {
        return pProcessExression;
    }

    public void setpProcessExression(String pProcessExression) {
        this.pProcessExression = pProcessExression;
    }

    public String getpAbilityType() {
        return pAbilityType;
    }

    public void setpAbilityType(String pAbilityType) {
        this.pAbilityType = pAbilityType;
    }

    public String getpStartTime() {
        return pStartTime;
    }

    public void setpStartTime(String pStartTime) {
        this.pStartTime = pStartTime;
    }

    public String getpEndTime() {
        return pEndTime;
    }

    public void setpEndTime(String pEndTime) {
        this.pEndTime = pEndTime;
    }

    public String getpRestTime() {
        return pRestTime;
    }

    public void setpRestTime(String pRestTime) {
        this.pRestTime = pRestTime;
    }

    public String getpAbilityCount() {
        return pAbilityCount;
    }

    public void setpAbilityCount(String pAbilityCount) {
        this.pAbilityCount = pAbilityCount;
    }

    public String getpDispatchExpression() {
        return pDispatchExpression;
    }

    public void setpDispatchExpression(String pDispatchExpression) {
        this.pDispatchExpression = pDispatchExpression;
    }

    public String getpCostCenter() {
        return pCostCenter;
    }

    public void setpCostCenter(String pCostCenter) {
        this.pCostCenter = pCostCenter;
    }

    public String getpTaskType1() {
        return pTaskType1;
    }

    public void setpTaskType1(String pTaskType1) {
        this.pTaskType1 = pTaskType1;
    }

    public String getpTaskType2() {
        return pTaskType2;
    }

    public void setpTaskType2(String pTaskType2) {
        this.pTaskType2 = pTaskType2;
    }

    public String getpTaskType3() {
        return pTaskType3;
    }

    public void setpTaskType3(String pTaskType3) {
        this.pTaskType3 = pTaskType3;
    }

    public String getpTaskType4() {
        return pTaskType4;
    }

    public void setpTaskType4(String pTaskType4) {
        this.pTaskType4 = pTaskType4;
    }

    public String getpTaskType5() {
        return pTaskType5;
    }

    public void setpTaskType5(String pTaskType5) {
        this.pTaskType5 = pTaskType5;
    }

    public String getpTaskType6() {
        return pTaskType6;
    }

    public void setpTaskType6(String pTaskType6) {
        this.pTaskType6 = pTaskType6;
    }

    public String getpExperssion1() {
        return pExperssion1;
    }

    public void setpExperssion1(String pExperssion1) {
        this.pExperssion1 = pExperssion1;
    }

    public String getpExperssion2() {
        return pExperssion2;
    }

    public void setpExperssion2(String pExperssion2) {
        this.pExperssion2 = pExperssion2;
    }

    public String getpExperssion3() {
        return pExperssion3;
    }

    public void setpExperssion3(String pExperssion3) {
        this.pExperssion3 = pExperssion3;
    }

    public String getpExperssion4() {
        return pExperssion4;
    }

    public void setpExperssion4(String pExperssion4) {
        this.pExperssion4 = pExperssion4;
    }

    public String getpExperssion5() {
        return pExperssion5;
    }

    public void setpExperssion5(String pExperssion5) {
        this.pExperssion5 = pExperssion5;
    }

    public String getpExperssion6() {
        return pExperssion6;
    }

    public void setpExperssion6(String pExperssion6) {
        this.pExperssion6 = pExperssion6;
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
}
