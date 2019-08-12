/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.interaction;

import lombok.Data;

@Data
public class FeatureBomLineRelationHistory {
    /**
     * 主键
     */
    private Long id;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 特性值主键
     */
    private String featureValueId;
    /**
     * 特性描述
     */
    private String featureDesc;
    /**
     * 特性值diamante
     */
    private String featureValueCode;
    /**
     * bomline主键，在hz_Bomline_record表中
     */
    private String bomLineId;
    /**
     * bimline名称
     */
    private String bomLineName;
}