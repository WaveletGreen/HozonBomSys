package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
public class HzWorkProcessByPageQuery extends DefaultPageQuery {
    private String projectId;

    /**
     *类型（1 半成品工艺路线 2整车工艺路线  3总成分总成工艺路线  ）
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
