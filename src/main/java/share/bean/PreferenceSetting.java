/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package share.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 首选项配置，不在继承TC中的首选项，应属于废弃部分数据
 */
@Data
@Deprecated
public class PreferenceSetting implements Serializable {
    private static final long serialVersionUID = -8988011687523006271L;
    /**
     * 首选项集合
     */
    private String[] preferences;
    /**
     * 首选项名
     */
    private String preferenceName;
    /**
     * 本地化名
     */
    private String[] preferenceLocal;
}
