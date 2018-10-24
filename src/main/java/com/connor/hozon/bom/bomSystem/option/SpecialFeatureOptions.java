/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.option;

import lombok.Getter;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/19 16:21
 * @Modified By:
 */
public enum SpecialFeatureOptions {
    /**
     * 车身特性代码
     */
    CSCODE("HZCSYS"),
    /***
     * 车身特性描述
     */
    CSNAME("车身颜色"),
    /**
     * 内饰颜色代码
     */
    NSCODE("HZNSYS"),
    /***
     * 内饰颜色描述
     */
    NSNAME("内饰颜色"),
    /**
     * 油漆车身总成代码
     */
    YQCSCODE("HZYQCS"),
    /**
     * 油漆车身总成描述
     */
    YQCSNAME("油漆车身总成");

    // 成员变量
    @Getter
    private String desc;

    // 构造方法
    SpecialFeatureOptions(String desc) {
        this.desc = desc;
    }
}
