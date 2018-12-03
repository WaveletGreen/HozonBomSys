/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.relevance;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
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
    /**
     * 相关性状态
     */
    private Integer status;

}
