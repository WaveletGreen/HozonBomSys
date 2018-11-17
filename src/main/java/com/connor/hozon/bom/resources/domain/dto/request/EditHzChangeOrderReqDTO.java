package com.connor.hozon.bom.resources.domain.dto.request;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
public class EditHzChangeOrderReqDTO {
    /**
     * 主键id
     */
    private Long id;
    /**
     *创建者工号
     */
    private String createNo;
    /**
     *流程发起时间
     */
    private Date originTime;
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
    private String hasRelatedChange;
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
     * 项目id
     */
    private String projectId;

    /**
     * 变更号
     */
    private String changeNo;



    public String getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(String changeNo) {
        this.changeNo = changeNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateNo() {
        return createNo;
    }

    public void setCreateNo(String createNo) {
        this.createNo = createNo;
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

    public String getHasRelatedChange() {
        return hasRelatedChange;
    }

    public void setHasRelatedChange(String hasRelatedChange) {
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
