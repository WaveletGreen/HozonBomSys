package sql.pojo.change;

import sql.pojo.BasePOJO;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description: 变更清单
 */
public class HzChangeOrderRecord extends BasePOJO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     *变更号
     */
    private String changeNo;
    /**
     *创建者工号
     */
    private String createNo;
    /**
     *流程发起人
     */
    private Long originatorId;

    /**
     * 流程发起人姓名
     */
    private String originator;
    /**
     * 申请人表对应审核表ID
     */
    private String auditRecordId;
    /**
     * 审核表ID
     */
    private String auditId;
    /**
     *流程发起时间
     */
    private Date originTime;
    /**
     *流程审核时间
     */
    private Date auditTime;
    /**
     *联系电话
     */
    private String tel;
    /**
     *变更类型
     */
    private String changeType;
    /**
     *是否有关联变更
     */
    private Integer hasRelatedChange;
    /**
     *关联变更号
     */
    private String relationChangeNo;
    /**
     *上市类型 1上市前  0上市后
     */
    private Integer marketType;
    /**
     *项目所处阶段
     */
    private String projectStage;
    /**
     *备注
     */
    private String remark;
    /**
     *当前变更表单状态（1 已完成 2进行中 3已取消）
     */
    private Integer state;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 表单信息是否同步自TC
     */
    private Integer fromTc;
    /**
     * 部门名称 来自TC
     */
    private String deptNameTC;
    /**
     * 创建者 来自TC
     */
    private String createNameTC;
    /**
     * 流程发起时间（多条记录获取申请表的申请时间）
     */
    private Date applicantTime;
    /**
     *是否为变更接口人,TC端同步的表单需要通知到接口人
     * ==1:TC端同步过来任务未结束
     * !=1:任务结束
     */
    private String changeAccepter;

    public String getChangeAccepter() {
        return changeAccepter;
    }

    public void setChangeAccepter(String changeAccepter) {
        this.changeAccepter = changeAccepter;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getApplicantTime() {
        return applicantTime;
    }

    public void setApplicantTime(Date applicantTime) {
        this.applicantTime = applicantTime;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getAuditRecordId() {
        return auditRecordId;
    }

    public void setAuditRecordId(String auditRecordId) {
        this.auditRecordId = auditRecordId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(String changeNo) {
        this.changeNo = changeNo;
    }

    public String getCreateNo() {
        return createNo;
    }

    public void setCreateNo(String createNo) {
        this.createNo = createNo;
    }

    public Long getOriginatorId() {
        return originatorId;
    }

    public void setOriginatorId(Long originatorId) {
        this.originatorId = originatorId;
    }

    public Date getOriginTime() {
        return originTime;
    }

    public void setOriginTime(Date originTime) {
        this.originTime = originTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Integer getHasRelatedChange() {
        return hasRelatedChange;
    }

    public void setHasRelatedChange(Integer hasRelatedChange) {
        this.hasRelatedChange = hasRelatedChange;
    }

    public String getRelationChangeNo() {
        return relationChangeNo;
    }

    public void setRelationChangeNo(String relationChangeNo) {
        this.relationChangeNo = relationChangeNo;
    }

    public Integer getMarketType() {
        return marketType;
    }

    public void setMarketType(Integer marketType) {
        this.marketType = marketType;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getFromTc() {
        return fromTc;
    }

    public void setFromTc(Integer fromTc) {
        this.fromTc = fromTc;
    }

    public String getDeptNameTC() {
        return deptNameTC;
    }

    public void setDeptNameTC(String deptNameTC) {
        this.deptNameTC = deptNameTC;
    }

    public String getCreateNameTC() {
        return createNameTC;
    }

    public void setCreateNameTC(String createNameTC) {
        this.createNameTC = createNameTC;
    }
}
