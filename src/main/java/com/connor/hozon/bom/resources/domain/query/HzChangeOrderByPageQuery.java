package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
@Data
public class HzChangeOrderByPageQuery extends DefaultPageQuery {
    /**
     *变更号
     */
    private String changeNo;
    /**
     *流程发起人
     */
    private String originator;
    /**
     *流程发起时间
     */
    private String firstOriginTime;

    /**
     *流程发起时间
     */
    private String lastOriginTime;
    /**
     *当前变更表单状态（1 已完成 2进行中 3已取消）
     */
    private Integer state;

    /**
     * 表单创建者
     */
    private String createName;
    /**
     * 表单创建时间
     */
    private String firstCreateTime;
    /**
     * 表单创建时间
     */
    private String lastCreateTime;

    /**
     * 查询类型
     *  1 按创建时间查询
     *  2 按流程发起时间查询
     */
    private Integer type;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 部门
     */
    private String deptName;
}
