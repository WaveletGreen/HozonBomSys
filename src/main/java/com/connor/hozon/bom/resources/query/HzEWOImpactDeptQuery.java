package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public class HzEWOImpactDeptQuery {
    private String ewoNo;

    private Long deptId;

    private String projectId;

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
