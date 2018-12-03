/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.relevance;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性主数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
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


    public HzRelevanceBasic(){}

    public HzRelevanceBasic(HzRelevanceBasicChange hzRelevanceBasicChange){
        Date date = new Date();
        User user = UserInfo.getUser();
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
        this.relevanceCreator = user.getLogin();
        this.relevanceUpdateDate = date;
        this.relevanceUpdater = user.getLogin();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRbColorCode() {
        return rbColorCode;
    }

    public void setRbColorCode(String rbColorCode) {
        this.rbColorCode = rbColorCode == null ? null : rbColorCode.trim();
    }

    public String getRbColorUid() {
        return rbColorUid;
    }

    public void setRbColorUid(String rbColorUid) {
        this.rbColorUid = rbColorUid == null ? null : rbColorUid.trim();
    }

    public String getRbFeatureCode() {
        return rbFeatureCode;
    }

    public void setRbFeatureCode(String rbFeatureCode) {
        this.rbFeatureCode = rbFeatureCode == null ? null : rbFeatureCode.trim();
    }

    public String getRbFeatureUid() {
        return rbFeatureUid;
    }

    public void setRbFeatureUid(String rbFeatureUid) {
        this.rbFeatureUid = rbFeatureUid == null ? null : rbFeatureUid.trim();
    }

    public String getRbFeatureValueCode() {
        return rbFeatureValueCode;
    }

    public void setRbFeatureValueCode(String rbFeatureValueCode) {
        this.rbFeatureValueCode = rbFeatureValueCode == null ? null : rbFeatureValueCode.trim();
    }

    public String getRbFeatureValueUid() {
        return rbFeatureValueUid;
    }

    public void setRbFeatureValueUid(String rbFeatureValueUid) {
        this.rbFeatureValueUid = rbFeatureValueUid == null ? null : rbFeatureValueUid.trim();
    }

    public String getRbRelevance() {
        return rbRelevance;
    }

    public void setRbRelevance(String rbRelevance) {
        this.rbRelevance = rbRelevance == null ? null : rbRelevance.trim();
    }

    public String getRbRelevanceDesc() {
        return rbRelevanceDesc;
    }

    public void setRbRelevanceDesc(String rbRelevanceDesc) {
        this.rbRelevanceDesc = rbRelevanceDesc;
    }

    public String getRbRelevanceCode() {
        return rbRelevanceCode;
    }

    public void setRbRelevanceCode(String rbRelevanceCode) {
        this.rbRelevanceCode = rbRelevanceCode == null ? null : rbRelevanceCode.trim();
    }

    public Integer getRelevanceStatus() {
        return relevanceStatus;
    }

    public void setRelevanceStatus(Integer relevanceStatus) {
        this.relevanceStatus = relevanceStatus;
    }

    public Date getRelevanceCreateDate() {
        return relevanceCreateDate;
    }

    public void setRelevanceCreateDate(Date relevanceCreateDate) {
        this.relevanceCreateDate = relevanceCreateDate;
    }

    public String getRelevanceCreator() {
        return relevanceCreator;
    }

    public void setRelevanceCreator(String relevanceCreator) {
        this.relevanceCreator = relevanceCreator;
    }

    public Date getRelevanceUpdateDate() {
        return relevanceUpdateDate;
    }

    public void setRelevanceUpdateDate(Date relevanceUpdateDate) {
        this.relevanceUpdateDate = relevanceUpdateDate;
    }

    public String getRelevanceUpdater() {
        return relevanceUpdater;
    }

    public void setRelevanceUpdater(String relevanceUpdater) {
        this.relevanceUpdater = relevanceUpdater;
    }

    public String getRbReserved1() {
        return rbReserved1;
    }

    public void setRbReserved1(String rbReserved1) {
        this.rbReserved1 = rbReserved1 == null ? null : rbReserved1.trim();
    }

    public String getRbReserved2() {
        return rbReserved2;
    }

    public void setRbReserved2(String rbReserved2) {
        this.rbReserved2 = rbReserved2 == null ? null : rbReserved2.trim();
    }

    public String getRbReserved3() {
        return rbReserved3;
    }

    public void setRbReserved3(String rbReserved3) {
        this.rbReserved3 = rbReserved3 == null ? null : rbReserved3.trim();
    }

    public String getRbReserved4() {
        return rbReserved4;
    }

    public void setRbReserved4(String rbReserved4) {
        this.rbReserved4 = rbReserved4 == null ? null : rbReserved4.trim();
    }

    public String getRbReserved5() {
        return rbReserved5;
    }

    public void setRbReserved5(String rbReserved5) {
        this.rbReserved5 = rbReserved5 == null ? null : rbReserved5.trim();
    }

    public String getRbReserved6() {
        return rbReserved6;
    }

    public void setRbReserved6(String rbReserved6) {
        this.rbReserved6 = rbReserved6 == null ? null : rbReserved6.trim();
    }

    public String getRbReserved7() {
        return rbReserved7;
    }

    public void setRbReserved7(String rbReserved7) {
        this.rbReserved7 = rbReserved7 == null ? null : rbReserved7.trim();
    }

    public String getRbReserved8() {
        return rbReserved8;
    }

    public void setRbReserved8(String rbReserved8) {
        this.rbReserved8 = rbReserved8 == null ? null : rbReserved8.trim();
    }

    public String getRbReserved9() {
        return rbReserved9;
    }

    public void setRbReserved9(String rbReserved9) {
        this.rbReserved9 = rbReserved9 == null ? null : rbReserved9.trim();
    }

    public String getRbReserved10() {
        return rbReserved10;
    }

    public void setRbReserved10(String rbReserved10) {
        this.rbReserved10 = rbReserved10 == null ? null : rbReserved10.trim();
    }

    public String getRbReserved11() {
        return rbReserved11;
    }

    public void setRbReserved11(String rbReserved11) {
        this.rbReserved11 = rbReserved11 == null ? null : rbReserved11.trim();
    }

    public String getRbReserved12() {
        return rbReserved12;
    }

    public void setRbReserved12(String rbReserved12) {
        this.rbReserved12 = rbReserved12 == null ? null : rbReserved12.trim();
    }

    public String getRbReserved13() {
        return rbReserved13;
    }

    public void setRbReserved13(String rbReserved13) {
        this.rbReserved13 = rbReserved13 == null ? null : rbReserved13.trim();
    }

    public String getRbReserved14() {
        return rbReserved14;
    }

    public void setRbReserved14(String rbReserved14) {
        this.rbReserved14 = rbReserved14 == null ? null : rbReserved14.trim();
    }

    public String getRbReserved15() {
        return rbReserved15;
    }

    public void setRbReserved15(String rbReserved15) {
        this.rbReserved15 = rbReserved15 == null ? null : rbReserved15.trim();
    }

    public Long getRbVwoId() {
        return rbVwoId;
    }

    public void setRbVwoId(Long rbVwoId) {
        this.rbVwoId = rbVwoId;
    }

    public String getRbProjectUid() {
        return rbProjectUid;
    }

    public void setRbProjectUid(String rbProjectUid) {
        this.rbProjectUid = rbProjectUid;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }
}