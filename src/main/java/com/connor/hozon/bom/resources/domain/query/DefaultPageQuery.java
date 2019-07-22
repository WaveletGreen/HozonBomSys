package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/6/27
 * @Description:
 */
public abstract class DefaultPageQuery extends DefaultQuery{
    /**
     * 页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int  pageSize ;

    /**
     * 页面传进来的pageSize 有字符串类型
     */
    private  String limit;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
