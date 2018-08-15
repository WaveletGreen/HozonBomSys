package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/8/14
 * @Description:
 */
public class HzEWOChangeFormQuery {
    /**
     * 项目id
     */
    private String projectId;

    /**
     * ewo编号
     */
    private String ewoNo;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }
}
