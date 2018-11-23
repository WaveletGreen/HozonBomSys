/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.task;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 任务传输对象
 * @Date: Created in 2018/10/19 15:27
 * @Modified By:
 */
@Data
public class HzTaskPostDto {
    /**
     * 跳转tab的url
     */
    private String url;
    /**
     * tab的id
     */
    private Long id;
    /**
     * tab显示的名称
     */
    private String text;
    /**
     * 表单类型，对应的审核表单，1：vwo表单,2:ewo表单,3:mwo表单
     */
    private Integer formType;
    /**
     * 目标的ID，VWO的ID时Long，如1，2，3
     */
    private Long targetId;
    /**
     * 目标的名，VWO的name格式未VCYYYYNNNN(20180001)
     */
    private String targetName;
    /**
     * 目标类型，1：特性变更，2：配色方案变更，3：配置物料特性表变更，4，全配置BOM一级清单变更，5：相关性变更
     */
    private Integer targetType;

    /**
     * 保留字段
     */
    private Object reserve;
    private Object reserve2;
    private Object reserve3;
    private Object reserve4;
    private Object reserve5;
    private Object reserve6;
    private Object reserve7;
    private Object reserve8;
    private Object reserve9;
    private Object reserve10;
}
