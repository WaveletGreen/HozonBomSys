/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.derivative;

import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 衍生物料详情信息
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
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
    private HzCfg0Record cfg0Record;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDmdDmbId() {
        return dmdDmbId;
    }

    public void setDmdDmbId(Long dmdDmbId) {
        this.dmdDmbId = dmdDmbId;
    }

    public String getDmdCfg0Uid() {
        return dmdCfg0Uid;
    }

    public void setDmdCfg0Uid(String dmdCfg0Uid) {
        this.dmdCfg0Uid = dmdCfg0Uid == null ? null : dmdCfg0Uid.trim();
    }

    public String getDmdCfg0FamilyUid() {
        return dmdCfg0FamilyUid;
    }

    public void setDmdCfg0FamilyUid(String dmdCfg0FamilyUid) {
        this.dmdCfg0FamilyUid = dmdCfg0FamilyUid == null ? null : dmdCfg0FamilyUid.trim();
    }

    public String getDmdCreator() {
        return dmdCreator;
    }

    public void setDmdCreator(String dmdCreator) {
        this.dmdCreator = dmdCreator;
    }

    public Date getDmdCreateDate() {
        return dmdCreateDate;
    }

    public void setDmdCreateDate(Date dmdCreateDate) {
        this.dmdCreateDate = dmdCreateDate;
    }

    public String getDmdUpdater() {
        return dmdUpdater;
    }

    public void setDmdUpdater(String dmdUpdater) {
        this.dmdUpdater = dmdUpdater;
    }

    public Date getDmdUpdateDate() {
        return dmdUpdateDate;
    }

    public void setDmdUpdateDate(Date dmdUpdateDate) {
        this.dmdUpdateDate = dmdUpdateDate;
    }

    public String getDmdReserved1() {
        return dmdReserved1;
    }

    public void setDmdReserved1(String dmdReserved1) {
        this.dmdReserved1 = dmdReserved1 == null ? null : dmdReserved1.trim();
    }

    public String getDmdReserved2() {
        return dmdReserved2;
    }

    public void setDmdReserved2(String dmdReserved2) {
        this.dmdReserved2 = dmdReserved2 == null ? null : dmdReserved2.trim();
    }

    public String getDmdReserved3() {
        return dmdReserved3;
    }

    public void setDmdReserved3(String dmdReserved3) {
        this.dmdReserved3 = dmdReserved3 == null ? null : dmdReserved3.trim();
    }

    public String getDmdReserved4() {
        return dmdReserved4;
    }

    public void setDmdReserved4(String dmdReserved4) {
        this.dmdReserved4 = dmdReserved4 == null ? null : dmdReserved4.trim();
    }

    public String getDmdReserved5() {
        return dmdReserved5;
    }

    public void setDmdReserved5(String dmdReserved5) {
        this.dmdReserved5 = dmdReserved5 == null ? null : dmdReserved5.trim();
    }

    public String getDmdFeatureValue() {
        return dmdFeatureValue;
    }

    public void setDmdFeatureValue(String dmdFeatureValue) {
        this.dmdFeatureValue = dmdFeatureValue == null ? null : dmdFeatureValue.trim();
    }

    public HzCfg0Record getCfg0Record() {
        return cfg0Record;
    }

    public void setCfg0Record(HzCfg0Record cfg0Record) {
        this.cfg0Record = cfg0Record;
    }
}