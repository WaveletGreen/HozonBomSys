/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.derivative;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 衍生物料详情信息
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
@ToString
public class HzDerivativeMaterielDetail {
    /**
     * 主键
     */
    private Long id;
    /**
     * 基本信息外键
     */
    private Long dmdDmbId;
    /**
     * 特性值外键
     */
    private String dmdCfg0Uid;
    /**
     * 特性外键
     */
    private String dmdCfg0FamilyUid;
    /**
     * 创建者
     */
    private String dmdCreator;
    /**
     * 创建时间
     */
    private Date dmdCreateDate;
    /**
     * 更新人
     */
    private String dmdUpdater;
    /**
     * 更新时间
     */
    private Date dmdUpdateDate;
    /**
     * 保留字段1
     */
    private String dmdReserved1;
    /**
     * 保留字段2
     */
    private String dmdReserved2;
    /**
     * 保留字段3
     */
    private String dmdReserved3;
    /**
     * 保留字段4
     */
    private String dmdReserved4;
    /**
     * 保留字段5
     */
    private String dmdReserved5;
    /**
     * 特性值,放在最后直接显示在前端
     */
    private String dmdFeatureValue;
    /**
     * 特性对象
     */
    private HzFeatureValue cfg0Record;

}