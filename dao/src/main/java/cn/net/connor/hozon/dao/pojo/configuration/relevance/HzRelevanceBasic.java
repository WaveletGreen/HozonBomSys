/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.relevance;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性主数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzRelevanceBasic {
    /**
     * 主键
     */
    private Long id;
    /**
     * 颜色代码
     */
    private String rbColorCode;
    /**
     * 颜色UID，外键
     */
    private String rbColorUid;
    /**
     * 特性代码
     */
    private String rbFeatureCode;
    /**
     * 特性UID，外键
     */
    private String rbFeatureUid;
    /**
     * 特性值代码
     */
    private String rbFeatureValueCode;
    /**
     * 特性值UID
     */
    private String rbFeatureValueUid;
    /**
     * 相关性
     */
    private String rbRelevance;
    /**
     * 相关性描述
     */
    private String rbRelevanceDesc;
    /**
     * 相关性代码
     */
    private String rbRelevanceCode;
    /**
     * 状态，分别为发布状态99，草稿状态0，删除状态10，其他状态null/其他数值
     */
    private Integer relevanceStatus;
    /**
     * 创建时间
     */
    private Date relevanceCreateDate;
    /**
     * 创建者
     */
    private String relevanceCreator;
    /**
     * 更新时间
     */
    private Date relevanceUpdateDate;
    /**
     * 更新人
     */
    private String relevanceUpdater;
    /**
     * 预留字段1
     */
    private String rbReserved1;
    /**
     * 预留字段1
     */
    private String rbReserved2;
    /**
     * 预留字段1
     */
    private String rbReserved3;
    /**
     * 预留字段1
     */
    private String rbReserved4;
    /**
     * 预留字段1
     */
    private String rbReserved5;
    /**
     * 预留字段1
     */
    private String rbReserved6;
    /**
     * 预留字段1
     */
    private String rbReserved7;
    /**
     * 预留字段1
     */
    private String rbReserved8;
    /**
     * 预留字段1
     */
    private String rbReserved9;
    /**
     * 预留字段1
     */
    private String rbReserved10;
    /**
     * 预留字段1
     */
    private String rbReserved11;
    /**
     * 预留字段1
     */
    private String rbReserved12;
    /**
     * 预留字段1
     */
    private String rbReserved13;
    /**
     * 预留字段1
     */
    private String rbReserved14;
    /**
     * 预留字段1
     */
    private String rbReserved15;
    /**
     * VWO变更ID
     */
    private Long rbVwoId;
    /**
     * 项目UID
     */
    private String rbProjectUid;
    /**
     * 是否已发送到SAP
     */
    private Integer isSent;

    /**
     * 生效时间
     */
    private Date effectedDate;

    public HzRelevanceBasic(){}

    public HzRelevanceBasic(HzRelevanceBasicChange hzRelevanceBasicChange,String username){
        Date date = new Date();
//        User user = UserInfo.getUser();
        this.rbColorCode = hzRelevanceBasicChange.getRbColorCode();
        this.rbColorUid = hzRelevanceBasicChange.getRbColorUid();
        this.rbFeatureCode = hzRelevanceBasicChange.getRbFeatureCode();
        this.rbFeatureUid = hzRelevanceBasicChange.getRbFeatureUid();
        this.rbFeatureValueCode = hzRelevanceBasicChange.getRbFeatureValueCode();
        this.rbFeatureValueUid = hzRelevanceBasicChange.getRbFeatureValueUid();
        this.rbRelevance = hzRelevanceBasicChange.getRbRelevance();
        this.rbRelevanceDesc = hzRelevanceBasicChange.getRbRelevanceDesc();
        this.rbRelevanceCode = hzRelevanceBasicChange.getRbRelevanceCode();
        this.relevanceStatus = hzRelevanceBasicChange.getRelevanceStatus();
        this.relevanceCreateDate = date;
        this.relevanceCreator = username;
        this.relevanceUpdateDate = date;
        this.relevanceUpdater = username;
        this.rbReserved1 = hzRelevanceBasicChange.getRbReserved1();
        this.rbReserved2 = hzRelevanceBasicChange.getRbReserved2();
        this.rbReserved3 = hzRelevanceBasicChange.getRbReserved3();
        this.rbReserved4 = hzRelevanceBasicChange.getRbReserved4();
        this.rbReserved5 = hzRelevanceBasicChange.getRbReserved5();
        this.rbReserved6 = hzRelevanceBasicChange.getRbReserved6();
        this.rbReserved7 = hzRelevanceBasicChange.getRbReserved7();
        this.rbReserved8 = hzRelevanceBasicChange.getRbReserved8();
        this.rbReserved9 = hzRelevanceBasicChange.getRbReserved9();
        this.rbReserved10 = hzRelevanceBasicChange.getRbReserved10();
        this.rbReserved11 = hzRelevanceBasicChange.getRbReserved11();
        this.rbReserved12 = hzRelevanceBasicChange.getRbReserved12();
        this.rbReserved13 = hzRelevanceBasicChange.getRbReserved13();
        this.rbReserved14 = hzRelevanceBasicChange.getRbReserved14();
        this.rbReserved15 = hzRelevanceBasicChange.getRbReserved15();
        this.rbVwoId = hzRelevanceBasicChange.getChangeOrderId();
        this.rbProjectUid = hzRelevanceBasicChange.getRbProjectUid();
        this.isSent = hzRelevanceBasicChange.getIsSent();
    }
}