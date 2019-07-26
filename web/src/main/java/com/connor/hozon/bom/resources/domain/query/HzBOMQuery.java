package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/12/21
 * @Description:
 */
@Data
public class HzBOMQuery extends DefaultQuery {
    private static final long serialVersionUID = -8670366939760370970L;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 查找编号
     */
    private Integer lineNo;

    /**
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 父层puid
     */
    private String parentId;

    /**
     * 零件信息
     */
    private Long eplId;

    /**
     * 零件号
     */
    private String partId;

    /**
     * 颜色件
     */
    private String colorId;

    private String tableName;

    private String puid;
}
