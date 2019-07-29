/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.query.relevance;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性查询助手，该查询助手字段定义必须与前端对应的table定义的field进行一一对应，方便Spring进行自动化
 * @Date: Created in 2018/9/7 15:12
 * @Modified By:
 */
@Data
public class HzRelevanceQuery {
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
    protected String limit;
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
     * @param query 查询对象，从前端传过来的对象
     * @return
     */
    public static String relectSortToDB(HzRelevanceQuery query) {
        switch (query.getSort()) {
            case "relevance":
                return "RB_RELEVANCE";
            case "relevanceDesc":
                return "RB_RELEVANCE_DESC";
            case "relevanceCode":
                return "RB_RELEVANCE_CODE";
            case "rbFeatureValueCode"://这里有2个查询条件，1是特性值查询，有升降序查询;2是颜色代码的查询，有升降序查询
                return "RB_FEATURE_VALUE_CODE "+query.getOrder()+",RB_COLOR_CODE";//这里为了增加颜色的排序方式，默认是ASC，后面的SQL会自动拼接上Order这一段，因此不用追加Order
            default:
                return "rbFeatureValueCode";
        }
    }

}
