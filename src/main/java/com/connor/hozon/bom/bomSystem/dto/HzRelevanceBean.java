/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 相关性助手
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class HzRelevanceBean {
    /**
     * 序号
     */
    private Integer index;
    /**
     * 相关性
     */
    private String relevance;
    /***相关性描述*/
    private String relevanceDesc;
    /**
     * 相关性代码
     */
    private String relevanceCode;
    /**
     * puid，用于标识是哪个配置值
     */
    private String puid;
    /**
     * 用于标识是哪张table的
     */
    private String _table;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 相关性是否已发送过
     */
    private Integer isRelevanceSended;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getRelevanceDesc() {
        return relevanceDesc;
    }

    public void setRelevanceDesc(String relevanceDesc) {
        this.relevanceDesc = relevanceDesc;
    }

    public String getRelevanceCode() {
        return relevanceCode;
    }

    public void setRelevanceCode(String relevanceCode) {
        this.relevanceCode = relevanceCode;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String get_table() {
        return _table;
    }

    public void set_table(String _table) {
        this._table = _table;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getIsRelevanceSended() {
        return isRelevanceSended;
    }

    public void setIsRelevanceSended(Integer isRelevanceSended) {
        this.isRelevanceSended = isRelevanceSended;
    }
}
