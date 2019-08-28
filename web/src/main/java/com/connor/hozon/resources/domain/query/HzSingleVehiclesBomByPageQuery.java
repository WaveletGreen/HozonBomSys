package com.connor.hozon.resources.domain.query;

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

    /**
     * eBom表的ids 用于展示树状结构
     */
    private String eBomPuids;

    /**
     * 是否展示层级（1展示层级结构  0不展示层级结构）
     */
    private Integer showBomStructure;
}
