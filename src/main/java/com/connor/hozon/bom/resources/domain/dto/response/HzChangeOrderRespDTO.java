package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
public class HzChangeOrderRespDTO extends BaseDTO {

    private static final String ORDER_FROM_TC="TC端";

    private static final String ORDER_FROM_BOM="BOM端";

    /**
     * 主键id
     */
    private Long id;
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
    private String originator;
    /**
     *流程发起时间
     */
    private String originTime;
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
    private String marketType;
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
    private String state;
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目name
     */
    private String projectName;

    /**
     * 表单创建者
     */
    private String createName;
    /**
     * 表单创建时间
     */
    private String createTime;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 来源(TC/BOM)
     */
    private String source;

    /**
     * 审批时间
     */
    private String auditTime;
    /**
     * 审批表ID
     */
    private Long auditId;
    /**
     * 流程发起时间（多条记录获取申请表的申请时间）
     */
    private String applicantTime;

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

    public String getApplicantTime() {
        return applicantTime;
    }

    public void setApplicantTime(String applicantTime) {
        this.applicantTime = applicantTime;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    /**
     * 表单来源 BOM端 TC端
     */
    private String orderResource;

    /**
     * 表单来源
     */
    private Integer isFromTc;

    /**
     * 表单状态值
     */
    private Integer Status;

    public Integer getIsFromTc() {
        return isFromTc;
    }

    public void setIsFromTc(Integer isFromTc) {
        this.isFromTc = isFromTc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getOriginTime() {
        return originTime;
    }

    public void setOriginTime(String originTime) {
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

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
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

    public String getState() {
        return state;
    }

    public void setState(Integer status) {
        if(status!=null){
            switch (status){
                case 1:this.state="已完成";break;
                case 2:this.state="进行中";break;
                case 3:this.state="流程中";break;
                default:this.state="已取消";break;
            }
        }else {
            this.state="未知";
        }
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOrderResource() {
        return orderResource;
    }

    public void setOrderResource(Integer from) {
        this.orderResource = Integer.valueOf(1).equals(from)?ORDER_FROM_TC:ORDER_FROM_BOM;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
