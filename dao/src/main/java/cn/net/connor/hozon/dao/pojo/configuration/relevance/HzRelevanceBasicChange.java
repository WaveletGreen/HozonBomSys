/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.relevance;

import lombok.Data;

import java.util.Date;

@Data
public class HzRelevanceBasicChange {
    private Long id;

    private String rbColorCode;

    private String rbColorUid;

    private String rbFeatureCode;

    private String rbFeatureUid;

    private String rbFeatureValueCode;

    private String rbFeatureValueUid;

    private String rbRelevance;

    private String rbRelevanceDesc;

    private String rbRelevanceCode;

    private Integer relevanceStatus;

    private Date relevanceCreateDate;

    private String relevanceCreator;

    private Date relevanceUpdateDate;

    private String relevanceUpdater;

    private String rbReserved1;

    private String rbReserved2;

    private String rbReserved3;

    private String rbReserved4;

    private String rbReserved5;

    private String rbReserved6;

    private String rbReserved7;

    private String rbReserved8;

    private String rbReserved9;

    private String rbReserved10;

    private String rbReserved11;

    private String rbReserved12;

    private String rbReserved13;

    private String rbReserved14;

    private String rbReserved15;

    private Long changeOrderId;

    private String rbProjectUid;

    private Integer isSent;

    private Long srcId;

    private Integer changeStatus;

    private Integer changeVersion;

    public HzRelevanceBasicChange(){

    }

    public HzRelevanceBasicChange(HzRelevanceBasic hzRelevanceBasic) {
        this.rbColorCode = hzRelevanceBasic.getRbColorCode();
        this.rbColorUid = hzRelevanceBasic.getRbColorUid();
        this.rbFeatureCode = hzRelevanceBasic.getRbFeatureCode();
        this.rbFeatureUid = hzRelevanceBasic.getRbFeatureUid();
        this.rbFeatureValueCode = hzRelevanceBasic.getRbFeatureValueCode();
        this.rbFeatureValueUid = hzRelevanceBasic.getRbFeatureValueUid();
        this.rbRelevance = hzRelevanceBasic.getRbRelevance();
        this.rbRelevanceDesc = hzRelevanceBasic.getRbRelevanceDesc();
        this.rbRelevanceCode = hzRelevanceBasic.getRbRelevanceCode();
        this.relevanceStatus = hzRelevanceBasic.getRelevanceStatus();
        this.relevanceCreateDate = hzRelevanceBasic.getRelevanceCreateDate();
        this.relevanceCreator = hzRelevanceBasic.getRelevanceCreator();
        this.relevanceUpdateDate = hzRelevanceBasic.getRelevanceUpdateDate();
        this.relevanceUpdater = hzRelevanceBasic.getRelevanceUpdater();
        this.rbReserved1 = hzRelevanceBasic.getRbReserved1();
        this.rbReserved2 = hzRelevanceBasic.getRbReserved2();
        this.rbReserved3 = hzRelevanceBasic.getRbReserved3();
        this.rbReserved4 = hzRelevanceBasic.getRbReserved4();
        this.rbReserved5 = hzRelevanceBasic.getRbReserved5();
        this.rbReserved6 = hzRelevanceBasic.getRbReserved6();
        this.rbReserved7 = hzRelevanceBasic.getRbReserved7();
        this.rbReserved8 = hzRelevanceBasic.getRbReserved8();
        this.rbReserved9 = hzRelevanceBasic.getRbReserved9();
        this.rbReserved10 = hzRelevanceBasic.getRbReserved10();
        this.rbReserved11 = hzRelevanceBasic.getRbReserved11();
        this.rbReserved12 = hzRelevanceBasic.getRbReserved12();
        this.rbReserved13 = hzRelevanceBasic.getRbReserved13();
        this.rbReserved14 = hzRelevanceBasic.getRbReserved14();
        this.rbReserved15 = hzRelevanceBasic.getRbReserved15();
        this.changeOrderId = hzRelevanceBasic.getRbVwoId();
        this.rbProjectUid = hzRelevanceBasic.getRbProjectUid();
        this.isSent = hzRelevanceBasic.getIsSent();
        this.srcId = hzRelevanceBasic.getId();
        this.changeStatus = 0;
    }

}