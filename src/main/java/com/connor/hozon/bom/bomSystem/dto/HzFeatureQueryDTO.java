/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class HzFeatureQueryDTO {
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
