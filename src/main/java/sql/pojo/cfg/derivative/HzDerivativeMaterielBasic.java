/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.derivative;

import lombok.Data;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 衍生物料基础信息
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzDerivativeMaterielBasic {
    /**
     * 主键
     */
    private Long id;
    /**
     * 基础模型外键
     */
    private String dmbModelUid;
    /**
     * 颜色车型外键
     */
    private String dmbColorModelUid;
    /**
     * 创建者
     */
    private String dmbCreator;
    /**
     * 创建日期
     */
    private Date dmbCreateDate;
    /**
     * 更新者
     */
    private String dmbUpdater;
    /**
     * 更新日期
     */
    private Date dmbUpdateDate;
    /**
     * 项目UID
     */
    private String dmbProjectUid;
    /**
     * 预留字段1
     */
    private String dmbModelFeatureUid;
    /**
     * 预留字段2
     */
    private String dmbReserved2;
    /**
     * 预留字段3
     */
    private String dmbReserved3;
    /**
     * 预留字段4
     */
    private String dmbReserved4;
    /**
     * 预留字段5
     */
    private String dmbReserved5;

    private String dmbSpecialFeatureUid;

    private Integer dmbStatus;

    //变更表单ID
    private Long changeOrderId;

    private String changeOrderNo;

    private Date effectedDate;
}