/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.depository.project;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 品牌
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */
public class HzBrandRecord implements IProject{
    /**
     * puid
     */
    private String puid;
    /**
     * 品牌代号
     */
    private String pBrandCode;
    /**
     * 品牌名称
     */
    private String pBrandName;
    /**
     * 创建时间
     */
    private Date pBrandCreateDate;
    /**
     * 最后一次修改时间
     */
    private Date pBrandLastModDate;
    /**
     * 品牌备注
     */
    private String pBrandComment;
    /**
     * 最后修改人
     */
    private String pBrandLastModifier;

    @Getter
    @Setter
    private Long projectManagerId;

    @Override
    public String getCode() {
        return this.pBrandCode;
    }

    @Override
    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpBrandCode() {
        return pBrandCode;
    }

    public void setpBrandCode(String pBrandCode) {
        this.pBrandCode = pBrandCode;
    }

    public String getpBrandName() {
        return pBrandName;
    }

    public void setpBrandName(String pBrandName) {
        this.pBrandName = pBrandName;
    }

    public Date getpBrandCreateDate() {
        return pBrandCreateDate;
    }

    public void setpBrandCreateDate(Date pBrandCreateDate) {
        this.pBrandCreateDate = pBrandCreateDate;
    }

    public Date getpBrandLastModDate() {
        return pBrandLastModDate;
    }

    public void setpBrandLastModDate(Date pBrandLastModDate) {
        this.pBrandLastModDate = pBrandLastModDate;
    }

    public String getpBrandComment() {
        return pBrandComment;
    }

    public void setpBrandComment(String pBrandComment) {
        this.pBrandComment = pBrandComment;
    }

    public String getpBrandLastModifier() {
        return pBrandLastModifier;
    }

    public void setpBrandLastModifier(String pBrandLastModifier) {
        this.pBrandLastModifier = pBrandLastModifier;
    }
}