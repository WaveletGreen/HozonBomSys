package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
public class HzEWOBasicInfoRespDTO extends BaseDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * ewo编号
     */
    private String ewoNo;
    /**
     *部门
     */
    private String dept;
    /**
     *变更类型
     */
    private String changeType;
    /**
     *原因代码
     */
    private String reasonCode;
    /**
     *表单创建时间
     */
    private String formCreateTime;
    /**
     *流程状态
     */
    private String flowStatus;
    /**
     *标题
     */
    private String title;
    /**
     *流程发起人
     */
    private String originator;
    /**
     *发起人电话号码
     */
    private String tel;
    /**
     *要求完成时间
     */
    private String finishTime;
    /**
     *费用承担部门
     */
    private String costAssumeDept;
    /**
     *上市类型 上市前 上市后
     */
    private String publicType;
    /**
     *关联EWO号
     */
    private String relationEwoNo;
    /**
     *原因描述
     */
    private String reasonDesc;
    /**
     *平台
     */
    private String platform;
    /**
     *车型代码
     */
    private String vehicleCode;
    /**
     *项目代码
     */
    private String projectCode;
    /**
     *生效时间
     */
    private String effectTime;
    /**
     *项目所属阶段
     */
    private String projectStage;
    /**
     *关联工程师
     */
    private String relationEngineer;
    /**
     *关联工程师部门
     */
    private String relationEngineerDept;
    /**
     *关联零件id
     */
    private String relationItemId;
    /**
     *关联零件名称
     */
    private String relationItemName;
    /**
     *变更描述
     */
    private String changeDesc;

    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getFormCreateTime() {
        return formCreateTime;
    }

    public void setFormCreateTime(String formCreateTime) {
        this.formCreateTime = formCreateTime;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCostAssumeDept() {
        return costAssumeDept;
    }

    public void setCostAssumeDept(String costAssumeDept) {
        this.costAssumeDept = costAssumeDept;
    }

    public String getPublicType() {
        return publicType;
    }

    public void setPublicType(String publicType) {
        this.publicType = publicType;
    }

    public String getRelationEwoNo() {
        return relationEwoNo;
    }

    public void setRelationEwoNo(String relationEwoNo) {
        this.relationEwoNo = relationEwoNo;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

    public String getRelationEngineer() {
        return relationEngineer;
    }

    public void setRelationEngineer(String relationEngineer) {
        this.relationEngineer = relationEngineer;
    }

    public String getRelationEngineerDept() {
        return relationEngineerDept;
    }

    public void setRelationEngineerDept(String relationEngineerDept) {
        this.relationEngineerDept = relationEngineerDept;
    }

    public String getRelationItemId() {
        return relationItemId;
    }

    public void setRelationItemId(String relationItemId) {
        this.relationItemId = relationItemId;
    }

    public String getRelationItemName() {
        return relationItemName;
    }

    public void setRelationItemName(String relationItemName) {
        this.relationItemName = relationItemName;
    }

    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }
}
