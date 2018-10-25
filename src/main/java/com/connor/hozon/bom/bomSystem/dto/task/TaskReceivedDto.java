/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.task;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 任务接收对象
 * @Date: Created in 2018/10/19 17:02
 * @Modified By:
 */
@Data
public class TaskReceivedDto {
    /**
     * 表单的ID，div中存了一个真实的表单ID，需要从当前的表单中获取div的id，然后后台获取到该div对应的url和id等信息，
     * 每次都可能需要进行查询该div的url，供前端自动跳转到具体的表单中
     */
    private Integer formId;
    /**
     * 表单类型，1：VWO表单，2：EWO表单，3：MVO表单
     */
    private Integer formType;
    /**
     * 流程的ID，如VWO的ID，EVO的ID
     */
    private Long targetId;
    /**
     * 目标类型，具体哪一类数据进行变更，1：特性变更，2:配色方案变更，3：配置物料特性数据变更，4：全配置BOM一级清单变更，5：相关性变更等
     */
    private Integer targetType;
}
