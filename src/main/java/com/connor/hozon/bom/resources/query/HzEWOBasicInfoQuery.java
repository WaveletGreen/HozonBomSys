package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
public class HzEWOBasicInfoQuery {
    private Long id;

    private String ewoNo;

    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }
}
