package com.connor.hozon.bom.resources.query;

/**
 * \* User: xulf
 * \* Date: 2018/7/2
 * \* Time: 13:39
 * \
 */
public class HzWorkByPageQuery extends DefaultPageQuery {
    /**
     * 项目id
     */
    private String projectId;

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {

        return projectId;
    }
}
