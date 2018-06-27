package com.connor.hozon.bom.resources.dto.request;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class UpdateHzEbomReqDTO {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 需要更改的内容
     */
    private Map<String,Object> updateContent;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, Object> getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(Map<String, Object> updateContent) {
        this.updateContent = updateContent;
    }
}
