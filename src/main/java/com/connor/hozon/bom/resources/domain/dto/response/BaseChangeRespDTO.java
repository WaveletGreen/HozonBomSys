package com.connor.hozon.bom.resources.domain.dto.response;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:变更基本返回值
 * 状态（A U D） 变更类型
 */
@Data
public abstract class BaseChangeRespDTO {
    /**
     * 变更类型
     */
    private String changeType;
    /**
     * 状态即将要执行的操作（A D U）  新增 删除 修改
     */
    private String state;
}
