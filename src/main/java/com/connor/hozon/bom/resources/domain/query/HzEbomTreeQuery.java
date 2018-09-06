package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/12
 * @Description:
 */
public class HzEbomTreeQuery {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 当前查询树结构的根节点
     */
    private String puid;

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
