package com.connor.hozon.resources.domain.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description:
 */
@Deprecated
public class RecoverHzBomReqDTO {
    private Integer type;

    private String puid;

    private String projectId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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
