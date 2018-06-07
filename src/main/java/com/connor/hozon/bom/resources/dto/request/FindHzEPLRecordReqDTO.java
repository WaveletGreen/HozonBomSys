package com.connor.hozon.bom.resources.dto.request;

/**
 * Created by haozt on 2018/06/05
 *
 */
public class FindHzEPLRecordReqDTO {
    /**
     * 项目id
     */
    private String projectId;

    private int pageSize;

    private int pageNum;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
