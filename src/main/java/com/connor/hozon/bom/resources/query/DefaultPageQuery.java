package com.connor.hozon.bom.resources.query;

/**
 * @Author: haozt
 * @Date: 2018/6/27
 * @Description:
 */
public abstract class DefaultPageQuery {
    /**
     * 页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int limit;

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
