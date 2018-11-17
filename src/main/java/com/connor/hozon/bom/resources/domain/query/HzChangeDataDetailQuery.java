package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Data
public class HzChangeDataDetailQuery {
    /**
     * 变更数据
     */
    private List<String> puids;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 状态标志位（1 已生效 0 删除  2草稿状态  3废除状态 4删除状态 5草稿状态->审核中   6删除状态->审核中）
     */
    private Integer state;

    /**
     * 是否存在版本
     */
    private Boolean revision;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 变更表单id
     */
    private Long orderId;
}
