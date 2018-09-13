package com.connor.hozon.bom.bomSystem.dto.relevance;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/7 15:12
 * @Modified By:
 */
public class HzRelevanceQueryDTO {
    /**
     * 项目UID
     */
    private String projectUid;
    /**
     * 特性代码
     */
    private String featureCode;
    /**
     * 特性值代码
     */
    private String featureValueCode;
    /**
     * 要排序的字段名
     */
    protected String sort;
    /**
     * 排序方式: desc \ asc
     */
    protected String order = "";
    /**
     * 获取一页行数
     */
    protected Integer limit;
    /**
     * 获取的页码
     */
    protected Integer page;
    /**
     * 起始记录
     */
    protected Integer offset;

    /**
     * 条件搜索映射
     *
     * @param code
     * @return
     */
    public static String relectSortToDB(String code) {
        switch (code) {
            case "relevance":
                return "RB_RELEVANCE";
            case "relevanceDesc":
                return "RB_RELEVANCE_DESC";
            case "relevanceCode":
                return "RB_RELEVANCE_CODE";
            default:
                return null;
        }
    }

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getFeatureValueCode() {
        return featureValueCode;
    }

    public void setFeatureValueCode(String featureValueCode) {
        this.featureValueCode = featureValueCode;
    }

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
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
