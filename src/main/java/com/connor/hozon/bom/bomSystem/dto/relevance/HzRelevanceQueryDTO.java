/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.relevance;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 相关性查询助手
 * @Date: Created in 2018/9/7 15:12
 * @Modified By:
 */
@Data
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

}
