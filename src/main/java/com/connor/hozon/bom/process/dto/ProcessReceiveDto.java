/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.dto;

import lombok.Data;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:  前端回传的审核表单和审核人员数据
 * @Date: Created in  2018/11/22 11:14
 * @Modified By:
 */
@Data
public class ProcessReceiveDto {
    /***
     * 审核人ID
     */
    private Long userId;
    /**
     * 变更表单ID
     */
    private Long orderId;
    /**
     * 当前表单的Div ID
     */
    private Integer activeDivId;
}
