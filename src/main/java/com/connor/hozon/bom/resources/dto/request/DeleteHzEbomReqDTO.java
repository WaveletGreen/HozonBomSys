package com.connor.hozon.bom.resources.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class DeleteHzEbomReqDTO {
    private String puids;

    private String projectId;

    private String puid;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getPuids() {
        return puids;
    }

    public void setPuids(String puids) {
        this.puids = puids;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
