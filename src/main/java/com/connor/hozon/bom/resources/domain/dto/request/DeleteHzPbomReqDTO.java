package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/7/11
 * @Description:
 */
public class DeleteHzPbomReqDTO extends BaseDTO {
    private String projectId;

    private String puids;

    private String eBomPuid;

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

    public String geteBomPuid() {
        return eBomPuid;
    }

    public void seteBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }
}
