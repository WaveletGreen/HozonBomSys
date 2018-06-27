package com.connor.hozon.bom.resources.dto.request;

/**
 * Created by haozt on 2018/06/05
 *
 */
public class FindHzEPLRecordReqDTO extends FindForPageReqDTO{
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 页码
     */
    private int page;
    /**
     * 条数
     */
    private int limit;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
