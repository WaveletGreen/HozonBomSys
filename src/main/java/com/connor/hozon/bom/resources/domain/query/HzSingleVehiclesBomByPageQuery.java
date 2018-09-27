package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
@Data
public class HzSingleVehiclesBomByPageQuery extends DefaultPageQuery {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 层级
     */
    private String level;

    /**
     * 零件号
     */
    private String lineId;

    /**
     * 专业
     */
    private String pBomOfWhichDept;

    private Integer isHas;

    private String lineIndex;

    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * 零件分类
     */
    private String pBomLinePartClass;

    /**
     * 单车Id
     */
    private Long singleVehiclesId;
}
