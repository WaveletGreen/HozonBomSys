package com.connor.hozon.bom.resources.domain.dto.request;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/20
 * Time: 19:16
 */
public class UpdateHzProcessFourRoutingReqDTO {
    private String projectId;

    private String puids;

    private String fourRouting;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPuids() {
        return puids;
    }

    public void setPuids(String puids) {
        this.puids = puids;
    }

    public String getFourRouting() {
        return fourRouting;
    }

    public void setFourRouting(String fourRouting) {
        this.fourRouting = fourRouting;
    }
}
