package com.connor.hozon.bom.process.dto;

import lombok.Data;

@Data
public class ProcessReciveDto {
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
