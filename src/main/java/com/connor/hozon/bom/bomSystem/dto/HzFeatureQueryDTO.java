package com.connor.hozon.bom.bomSystem.dto;

import com.connor.hozon.bom.common.base.entity.QueryBase;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class HzFeatureQueryDTO extends QueryBase {
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
     * 特性
     */
    private String feature;
    /**
     * 特性值
     */
    private String featureValue;
    /**
     * 项目UID
     */
    private String projectUid;

    @Override
    public String getSort() {
        return sort;
    }

    @Override
    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }
}
