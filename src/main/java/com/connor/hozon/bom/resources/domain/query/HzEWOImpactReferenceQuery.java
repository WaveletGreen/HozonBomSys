package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description:
 */
public class HzEWOImpactReferenceQuery extends DefaultQuery{
    private String projectId;

    private String ewoNo;

    private Long impactAnalysisId;

    public Long getImpactAnalysisId() {
        return impactAnalysisId;
    }

    public void setImpactAnalysisId(Long impactAnalysisId) {
        this.impactAnalysisId = impactAnalysisId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }
}
