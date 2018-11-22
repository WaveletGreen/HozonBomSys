/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.vwo;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 忘了
 * @Date: Created in 2018/10/16 15:58
 * @Modified By:
 */
@Data
public class HzVwoProcessDto {
    /**
     * VWO类型
     */
    private Integer vwoType;
    /**
     * 项目UID
     */
    private String projectUid;
    /**
     * VWO主键
     */
    private Long vwoId;

    /**
     * 表单ID，该ID目前在MySql中，需要从DIV里面获取ID
     */
    private Long formId;
}
