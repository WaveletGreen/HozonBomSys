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
 * 衍生物料变更详情信息
 */
@Data
@ToString
public class HzDMDetailChangeBean {
    /**
     * 主键
     */
    private Long id;
    /**
     * 变更表单ID
     */
    private Long formId;
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
     * 源数据创建者
     */
    private String dmdCreator;
    /**
     * 源数据创建时间
     */
    private Date dmdCreateDate;
    /**
     * 源数据更新人
     */
    private String dmdUpdater;
    /**
     * 源数据更新时间
     */
    private Date dmdUpdateDate;
    /**
     * 创建者
     */
    private String changeCreator;
    /**
     * 创建时间
     */
    private Date changeCreateDate;
    /**
     * 更新人
     */
    private String changeUpdater;
    /**
     * 更新时间
     */
    private Date changeUpdateDate;
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

    private Long dmbChangeBasicId;

    private String title;
    /**
     * 源从数据ID
     */
    private Long dbdSrcDetailId;


    public void srcSetChange(HzDerivativeMaterielDetail hzDerivativeMaterielDetail,String username) {
        Date date = new Date();
        this.setDbdSrcDetailId(hzDerivativeMaterielDetail.getId());
        this.setDmdDmbId(hzDerivativeMaterielDetail.getDmdDmbId());
        this.setDmdCfg0Uid(hzDerivativeMaterielDetail.getDmdCfg0Uid());
        this.setDmdCfg0FamilyUid(hzDerivativeMaterielDetail.getDmdCfg0FamilyUid());
        this.setDmdCreator(hzDerivativeMaterielDetail.getDmdCreator());
        this.setDmdCreateDate(hzDerivativeMaterielDetail.getDmdCreateDate());
        this.setDmdUpdater(hzDerivativeMaterielDetail.getDmdUpdater());
        this.setDmdUpdateDate(hzDerivativeMaterielDetail.getDmdUpdateDate());
        this.setChangeCreator(username);
        this.setChangeCreateDate(date);
        this.setChangeUpdater(username);
        this.setChangeUpdateDate(date);
        this.setDmdReserved1(hzDerivativeMaterielDetail.getDmdReserved1());
        this.setDmdReserved2(hzDerivativeMaterielDetail.getDmdReserved2());
        this.setDmdReserved3(hzDerivativeMaterielDetail.getDmdReserved3());
        this.setDmdReserved4(hzDerivativeMaterielDetail.getDmdReserved4());
        this.setDmdReserved5(hzDerivativeMaterielDetail.getDmdReserved5());
        this.setDmdFeatureValue(hzDerivativeMaterielDetail.getDmdFeatureValue());
        this.setCfg0Record(hzDerivativeMaterielDetail.getCfg0Record());
    }

    public HzDerivativeMaterielDetail getHzDerivativeMaterielDetail() {
        HzDerivativeMaterielDetail hzDerivativeMaterielDetail = new HzDerivativeMaterielDetail();
        hzDerivativeMaterielDetail.setId(this.getDbdSrcDetailId());
        hzDerivativeMaterielDetail.setDmdCfg0Uid(this.getDmdCfg0Uid());
        hzDerivativeMaterielDetail.setDmdCfg0FamilyUid(this.getDmdCfg0FamilyUid());
        hzDerivativeMaterielDetail.setDmdFeatureValue(this.getDmdFeatureValue());
        return hzDerivativeMaterielDetail;
    }
}
