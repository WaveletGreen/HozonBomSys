package com.connor.hozon.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/7/13
 * @Description:
 */
public class HzBomRecycleByPageQuery  extends DefaultPageQuery {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 找回类型 1 EBOM  2 PBOM  3MBOM
     */
    private Integer type;

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
}
