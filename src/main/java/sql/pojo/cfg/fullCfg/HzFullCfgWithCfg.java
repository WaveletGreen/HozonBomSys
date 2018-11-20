/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.fullCfg;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 全配置BOM一级清单2Y与特性值关联
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzFullCfgWithCfg {
    private Long id;

    private String cfgCfg0Uid;

    private String cfgBomlineUid;

    private Date flCfgCreateDate;

    private Date flCfgUpdateDate;

    private String flCfgCreator;

    private String flCfgUpdator;

    private String flCfgBomlineName;

    private Long flCfgVersion;
    /**
     * 操作类型,1新增，2更新，3删除
     */
    private Integer flOperationType;
    /**
     * 备注
     */
    private String flComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCfgCfg0Uid() {
        return cfgCfg0Uid;
    }

    public void setCfgCfg0Uid(String cfgCfg0Uid) {
        this.cfgCfg0Uid = cfgCfg0Uid == null ? null : cfgCfg0Uid.trim();
    }

    public String getCfgBomlineUid() {
        return cfgBomlineUid;
    }

    public void setCfgBomlineUid(String cfgBomlineUid) {
        this.cfgBomlineUid = cfgBomlineUid == null ? null : cfgBomlineUid.trim();
    }

    public Date getFlCfgCreateDate() {
        return flCfgCreateDate;
    }

    public void setFlCfgCreateDate(Date flCfgCreateDate) {
        this.flCfgCreateDate = flCfgCreateDate;
    }

    public Date getFlCfgUpdateDate() {
        return flCfgUpdateDate;
    }

    public void setFlCfgUpdateDate(Date flCfgUpdateDate) {
        this.flCfgUpdateDate = flCfgUpdateDate;
    }

    public String getFlCfgCreator() {
        return flCfgCreator;
    }

    public void setFlCfgCreator(String flCfgCreator) {
        this.flCfgCreator = flCfgCreator;
    }

    public String getFlCfgUpdator() {
        return flCfgUpdator;
    }

    public void setFlCfgUpdator(String flCfgUpdator) {
        this.flCfgUpdator = flCfgUpdator;
    }

    public String getFlCfgBomlineName() {
        return flCfgBomlineName;
    }

    public void setFlCfgBomlineName(String flCfgBomlineName) {
        this.flCfgBomlineName = flCfgBomlineName;
    }

    public Long getFlCfgVersion() {
        return flCfgVersion;
    }

    public void setFlCfgVersion(Long flCfgVersion) {
        this.flCfgVersion = flCfgVersion;
    }

    public Integer getFlOperationType() {
        return flOperationType;
    }

    public void setFlOperationType(Integer flOperationType) {
        this.flOperationType = flOperationType;
    }

    public String getFlComment() {
        return flComment;
    }

    public void setFlComment(String flComment) {
        this.flComment = flComment;
    }
}