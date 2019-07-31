/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.configuration.model;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzCfg0ModelRecord {
    /**
     * 唯一键
     */
    private String puid;
    /**
     * 车型名称
     */
    private String objectName;
    /**
     * 车型描述
     */
    private String objectDesc;
    /**
     * 属于的配置item的puid
     */
    private String pCfg0ModelOfMainRecord;
    /**
     * 基本信息，在TC中不进行维护，在BOM系统进行维护，并每一个单一车型的基本信息都一样
     */
    private String pCfg0ModelBasicDetail;
    /**
     * 是否已发送到SAP
     */
    private Integer isSent;
    /**
     * 创建时间
     */
    private Date modelCreateDate;
    /**
     * 创建时间
     */
    private Date modelUpdateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(o instanceof  HzCfg0ModelRecord){
            final HzCfg0ModelRecord that = (HzCfg0ModelRecord) o;
            if(StringUtils.isNotBlank(this.objectName)){
                return this.objectName.equals(that.getObjectName());
            }else if(StringUtils.isNotBlank(puid)){
                return this.puid.equals(that.getPuid());
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectName);
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectDesc() {
        return objectDesc;
    }

    public void setObjectDesc(String objectDesc) {
        this.objectDesc = objectDesc;
    }

    public String getpCfg0ModelOfMainRecord() {
        return pCfg0ModelOfMainRecord;
    }

    public void setpCfg0ModelOfMainRecord(String pCfg0ModelOfMainRecord) {
        this.pCfg0ModelOfMainRecord = pCfg0ModelOfMainRecord;
    }

    public String getpCfg0ModelBasicDetail() {
        return pCfg0ModelBasicDetail;
    }

    public void setpCfg0ModelBasicDetail(String pCfg0ModelBasicDetail) {
        this.pCfg0ModelBasicDetail = pCfg0ModelBasicDetail;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public Date getModelCreateDate() {
        return modelCreateDate;
    }

    public void setModelCreateDate(Date modelCreateDate) {
        this.modelCreateDate = modelCreateDate;
    }

    public Date getModelUpdateDate() {
        return modelUpdateDate;
    }

    public void setModelUpdateDate(Date modelUpdateDate) {
        this.modelUpdateDate = modelUpdateDate;
    }
}