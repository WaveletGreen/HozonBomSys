/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.task;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/19 17:02
 * @Modified By:
 */
@Data
public class TaskReceivedDto {
    /**
     * 表单的ID，div中存了一个真实的表单ID
     */
    private Integer formId;
    /**
     * 表单类型
     */
    private Integer formType;
    /**
     * 流程的ID，如VWO的ID
     */
    private Long targetId;
    /**
     * 目标类型
     */
    private Integer targetType;
}
