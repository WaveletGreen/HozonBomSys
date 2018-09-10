package com.connor.hozon.bom.resources.domain.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/8/16
 * @Description:
 */
public class SetLouReqDTO {
    private String projectId;

    private String lineIds;

    private String lineId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
