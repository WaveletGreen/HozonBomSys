/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.vwo;

import lombok.Data;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/25 13:12
 * @Modified By:
 */
@Data
public class HzVwoFeatureTableDto {
    /**
     * 特性名称
     */
    private String featureName;
    /**
     * 特性描述
     */
    private String featureDesc;
    /**
     * 特性英文名称
     */
    private String h9featureenname;
    /**
     * featureValueName
     */
    private String featureValueName;
    /**
     * featureValueDesc
     */
    private String featureValueDesc;
    /**
     * 生效时间
     */
    private Date cfgEffectedDate;
    /**
     * 废止时间
     */
    private Date cfgAbolishDate;

    private String headDesc;

    public HzVwoFeatureTableDto() {
    }

    public HzVwoFeatureTableDto(HzFeatureChangeBean bean) {
        this.featureName = bean.getFeatureName();
        this.featureDesc = bean.getFeatureDesc();
        this.h9featureenname = bean.getH9featureenname();
        this.featureValueName = bean.getFeatureValueName();
        this.featureValueDesc = bean.getFeatureValueDesc();
        this.cfgEffectedDate = bean.getCfgEffectedDate();
        this.cfgAbolishDate = bean.getCfgAbolishDate();
        this.headDesc = bean.getHeadDesc();
    }
}
