/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.change.vwo;

import lombok.Data;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/10/25 13:12
 * @Modified By:
 */
@Data
public class HzVwoFeatureTableResponseDTO {
    /**
     *变更ID
     */
    private Long id;
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

    public HzVwoFeatureTableResponseDTO() {
    }

    public HzVwoFeatureTableResponseDTO(HzFeatureChangeBean bean) {
        this.featureName = bean.getFeatureName();
        this.featureDesc = bean.getFeatureDesc();
        this.h9featureenname = bean.getH9featureenname();
        this.featureValueName = bean.getFeatureValueName();
        this.featureValueDesc = bean.getFeatureValueDesc();
        this.cfgEffectedDate = bean.getCfgEffectedDate();
        this.cfgAbolishDate = bean.getCfgAbolishDate();
        this.headDesc = bean.getHeadDesc();
        this.id = bean.getId();
    }
}
