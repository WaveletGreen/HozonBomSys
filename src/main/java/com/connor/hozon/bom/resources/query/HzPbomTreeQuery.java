package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/7/7
 * @Description: 查找Pbom树状结构
 */
public class HzPbomTreeQuery {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 当前查询树结构的根节点
     */
    private String puid;

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
}
