package com.connor.hozon.bom.common.base.entity;

/*
* 类描述：查询基础类
* @auther linzf
* @create 2017/8/11 0011 
*/
public class QueryBase {

    /** 要排序的字段名 */
    protected String sort;
    /** 排序方式: desc \ asc */
    protected String order = "";
    /** 获取一页行数 */
    protected Integer limit;
    /** 获取的页码 */
    protected Integer page;
    /** 起始记录 */
    protected Integer offset;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getOffset() {
        return (page==null||limit==null)?null:(this.page-1)*limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
