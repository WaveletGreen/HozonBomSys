/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: x
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Data
public class HzFeatureQueryDto {
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

}
