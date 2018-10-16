/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.vwo;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
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
}
