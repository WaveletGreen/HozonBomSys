package com.connor.hozon.bom.resources.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class DeleteHzEbomReqDTO {
    private String puid;

    private String projectId;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
