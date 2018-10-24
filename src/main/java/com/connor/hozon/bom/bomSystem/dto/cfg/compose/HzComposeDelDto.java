/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.cfg.compose;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/9/4 15:57
 * @Modified By:
 */
public class HzComposeDelDto {
    /**
     * 基本信息ID
     */
    private Long basicId;
    /**
     * 配置物料模型UID
     */
    private String puidOfModelFeature;
    /**
     * 基本车型UID
     */
    private String puid;
    /**
     * 基本信息
     */
    private String modeBasicDetail;
    /**
     * 基本信息描述
     */
    private String modeBasicDetailDesc;

    public Long getBasicId() {
        return basicId;
    }

    public void setBasicId(Long basicId) {
        this.basicId = basicId;
    }

    public String getPuidOfModelFeature() {
        return puidOfModelFeature;
    }

    public void setPuidOfModelFeature(String puidOfModelFeature) {
        this.puidOfModelFeature = puidOfModelFeature;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getModeBasicDetail() {
        return modeBasicDetail;
    }

    public void setModeBasicDetail(String modeBasicDetail) {
        this.modeBasicDetail = modeBasicDetail;
    }

    public String getModeBasicDetailDesc() {
        return modeBasicDetailDesc;
    }

    public void setModeBasicDetailDesc(String modeBasicDetailDesc) {
        this.modeBasicDetailDesc = modeBasicDetailDesc;
    }
}
