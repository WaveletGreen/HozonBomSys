/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性助手
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Data
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

}
