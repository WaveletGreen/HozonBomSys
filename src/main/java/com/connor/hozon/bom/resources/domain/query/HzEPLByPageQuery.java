package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/6/27
 * @Description:
 */
@Data
public class HzEPLByPageQuery extends DefaultPageQuery {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 零件号
     */
    private String partId;
    /**
     * 专业
     */
    private String partOfWhichDept;
    /**
     * 零件名称
     */
    private String partName;
    /**
     * 零件分类
     */
    private String partClass;
    /**
     * 零件来源
     */
    private String partResource;

    /**
     * 状态
     */
    private Integer status;
}
