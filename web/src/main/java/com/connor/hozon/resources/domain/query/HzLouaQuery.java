package com.connor.hozon.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/18
 * @Description:
 */
public class HzLouaQuery  extends DefaultQuery{
    private String projectId;

    private String puid;

    private Integer type;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
