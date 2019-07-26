/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目树结构构建对象
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class HzProjectBean {
    private String puid;
    private String pPuid;
    private String name;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpPuid() {
        return pPuid;
    }

    public void setpPuid(String pPuid) {
        this.pPuid = pPuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
