package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Data
public class HzChangeDataQuery {
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

}
