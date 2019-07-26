package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/9/21
 * @Description:
 */
public class HzMbomByIdQuery extends DefaultQuery{
    private String projectId;

    /**
     * 1 生产 6 财务
     */
    private Integer type;

    private String puid;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

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
}
