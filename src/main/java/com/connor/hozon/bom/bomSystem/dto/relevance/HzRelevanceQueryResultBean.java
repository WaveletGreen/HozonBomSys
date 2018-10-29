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
 * @Date: Created in 2018/9/7 15:16
 * @Modified By:
 */
@Data
public class HzRelevanceQueryResultBean {
    /**
     * 主键
     */
    private Long id;
    /**
     * 序号
     */
    private Integer index;
    /**
     * 相关性
     */
    private String relevance;
    /**
     * 相关性描述
     */
    private String relevanceDesc;
    /**
     * 相关性代码
     */
    private String relevanceCode;

}
