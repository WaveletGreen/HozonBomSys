/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.fullCfg;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 全配置BOM一级清单基本车型模型数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzFullCfgModel {
    private Long id;

    private String modModelUid;

    private String modCfg0Uid;

    private Short modPointType;

    private Date flModCreateDate;

    private Date flModUpdateDate;

    private String flModCreator;

    private String flModLastUpdater;

    private Long flModVersion;
    /**
     * 2Y层主键
     */
    private String flModelBomlineUid;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getModModelUid() {
        return modModelUid;
    }

    public void setModModelUid(String modModelUid) {
        this.modModelUid = modModelUid == null ? null : modModelUid.trim();
    }

    public String getModCfg0Uid() {
        return modCfg0Uid;
    }

    public void setModCfg0Uid(String modCfg0Uid) {
        this.modCfg0Uid = modCfg0Uid == null ? null : modCfg0Uid.trim();
    }

    public Short getModPointType() {
        return modPointType;
    }

    public void setModPointType(Short modPointType) {
        this.modPointType = modPointType;
    }

    public Date getFlModCreateDate() {
        return flModCreateDate;
    }

    public void setFlModCreateDate(Date flModCreateDate) {
        this.flModCreateDate = flModCreateDate;
    }

    public Date getFlModUpdateDate() {
        return flModUpdateDate;
    }

    public void setFlModUpdateDate(Date flModUpdateDate) {
        this.flModUpdateDate = flModUpdateDate;
    }

    public String getFlModCreator() {
        return flModCreator;
    }

    public void setFlModCreator(String flModCreator) {
        this.flModCreator = flModCreator;
    }

    public String getFlModLastUpdater() {
        return flModLastUpdater;
    }

    public void setFlModLastUpdater(String flModLastUpdater) {
        this.flModLastUpdater = flModLastUpdater;
    }

    public Long getFlModVersion() {
        return flModVersion;
    }

    public void setFlModVersion(Long flModVersion) {
        this.flModVersion = flModVersion;
    }

    public String getFlModelBomlineUid() {
        return flModelBomlineUid;
    }

    public void setFlModelBomlineUid(String flModelBomlineUid) {
        this.flModelBomlineUid = flModelBomlineUid;
    }
}