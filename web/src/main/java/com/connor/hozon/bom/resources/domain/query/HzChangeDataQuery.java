/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Data
public class HzChangeDataQuery extends DefaultQuery{
    /**
     * 表单id
     */
    private Long orderId;

    /**
     * 超链接名称
     */
    private String hyperName;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 1 生成 6 财务
     */
    private Integer type;
}
