/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.cfg0;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 起名为Cfg0是由于TC中配置管理中前缀是Cfg0，沿用TC的命名，到后来不再沿用TC，改采用英文Feature来标识特性
 * <p>
 * 值：Cfg0=featureValue来标识特性值
 * <p>
 * 族：Cfg0OptionFamily=feature依旧沿用TC的命名，用来标识族，在BOM系统中为特性(=族)
 * <p>
 * 组：在TC中组为更上一层的结构，是特性的父层
 * <p>
 * 一阶段的时候没有为组单独建一张表造成的组为游离态的尴尬地步
 * 二阶段为特性建立了字典库，所有项目下的特性都来源于字典库。
 * 字典库存在一个弊端：字典库可以已特性值为基础随意创建字典项目，即所有的数据都以特性值为标准，特性值是唯一的，
 * 但是特性值依旧没有父层的约束，有可能造成'组->特性->特性值'不在一个相对由约束的属性结构树下
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzCfg0Record {
    /**
     * PUId，主键
     */
    private String puid;
    /**
     * 配置值
     */
    private String pCfg0ObjectId;
    /**
     * 配置值的描述
     */
    private String pCfg0Desc;
    /**
     * 组值，也是组的ID
     */
    private String pCfg0FamilyName;
    /**
     * 对应数据库的puid
     */
    private String pCfg0FamilyPuid;
    /**
     * 产品配置器的数据库puid
     */
    private String pCfg0MainItemPuid;
    /**
     * 特性英文名称
     */
    private String pH9featureenname;
    /**
     * 组的描述
     */
    private String pCfg0FamilyDesc;
    /**
     * 类中特有的，对应是新加进来的特性值还是原有的那张值表，目前有2个值：HZ_CFG0_RECORD和HZ_CFG0_ADD_CFG_RECORD
     */
    private String whichTable;
    /**
     * 归属项目，类中特有的
     */
    private String projectPuid;
    /**
     * 相关性代码，目前手输入
     */
    private String pCfg0Relevance;
    /**
     * 特性是否已发送到ERP
     */
    private Integer isFeatureSent;
    /**
     * 相关性是否已发送到ERP
     */
    private Integer isRelevanceSent;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后一次修改时间
     */
    private Date lastModifyDate;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 修改者
     */
    private String lastModifier;
    /**
     * 生效时间
     */
    private Date cfgEffectedDate;
    /**
     * 废止时间
     */
    private Date cfgAbolishDate;
    /**
     * 状态
     */
    private Integer cfgStatus;
    /**
     * 是否在流程中
     */
    private Integer cfgIsInProcess;
    /**
     * vwo号的ID
     */
    private Long vwoId;
    /**
     * vwo号
     */
    private String vwoNum;
    /**
     * 配置字典对应的特性值UID
     */
    @Getter
    @Setter
    private String cfgDicLibUid;
    /**
     * 预留字段
     */
    @Getter
    @Setter
    private String cfgReverse2;
    /**
     * 预留字段
     */
    @Getter
    @Setter
    private String cfgReverse3;
    /**
     * 预留字段
     */
    @Getter
    @Setter
    private String cfgReverse4;
    /**
     * 预留字段
     */
    @Getter
    @Setter
    private String cfgReverse5;
    /**
     * 预留字段
     */
    @Getter
    @Setter
    private String cfgReverse6;


    public static String reflectToDBField(String property) {
        switch (property) {
            /**
             * 主键
             */
            case "puid":
                return "PUID";
            /**
             * 颜色集
             */
            case "pCfg0ObjectId":
                return "P_CFG0_OBJECT_ID";
            /**
             * 颜色名称
             */
            case "pCfg0Desc":
                return "P_CFG0_DESC";
            /**
             * 颜色代码
             */
            case "pCfg0FamilyName":
                return "P_CFG0_FAMILY_NAME";
            /**
             * 备注
             */
            case "pCfg0FamilyDesc":
                return "P_CFG0_FAMILY_DESC";
            /**
             * 生效时间
             */
            case "cfgEffectedDate":
                return "CFG_EFFECTED_DATE";
            /**
             * 废止时间
             */
            case "cfgAbolishDate":
                return "CFG_ABOLISH_DATE";
            default:
                return null;
        }
    }


    public String getPuid() {
        return puid;
    }

    private Integer length = 10;

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpCfg0ObjectId() {
        return pCfg0ObjectId;
    }

    public void setpCfg0ObjectId(String pCfg0ObjectId) {
        this.pCfg0ObjectId = pCfg0ObjectId == null ? null : pCfg0ObjectId.trim();
    }

    public String getpCfg0Desc() {
        return pCfg0Desc;
    }

    public void setpCfg0Desc(String pCfg0Desc) {
        this.pCfg0Desc = pCfg0Desc == null ? null : pCfg0Desc.trim();
    }

    public String getpCfg0FamilyName() {
        return pCfg0FamilyName;
    }

    public void setpCfg0FamilyName(String pCfg0FamilyName) {
        this.pCfg0FamilyName = pCfg0FamilyName == null ? null : pCfg0FamilyName.trim();
    }

    public String getpCfg0FamilyPuid() {
        return pCfg0FamilyPuid;
    }

    public void setpCfg0FamilyPuid(String pCfg0FamilyPuid) {
        this.pCfg0FamilyPuid = pCfg0FamilyPuid == null ? null : pCfg0FamilyPuid.trim();
    }

    public String getpCfg0MainItemPuid() {
        return pCfg0MainItemPuid;
    }

    public void setpCfg0MainItemPuid(String pCfg0MainItemPuid) {
        this.pCfg0MainItemPuid = pCfg0MainItemPuid == null ? null : pCfg0MainItemPuid.trim();
    }

    public String getpH9featureenname() {
        return pH9featureenname;
    }

    public void setpH9featureenname(String pH9featureenname) {
        this.pH9featureenname = pH9featureenname == null ? null : pH9featureenname.trim();
    }


    public String getpCfg0FamilyDesc() {
        return pCfg0FamilyDesc;
    }

    public void setpCfg0FamilyDesc(String pCfg0FamilyDesc) {
        this.pCfg0FamilyDesc = pCfg0FamilyDesc == null ? null : pCfg0FamilyDesc.trim();
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getWhichTable() {
        return whichTable;
    }

    public void setWhichTable(String whichTable) {
        this.whichTable = whichTable;
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public String getpCfg0Relevance() {
        return pCfg0Relevance;
    }

    public void setpCfg0Relevance(String pCfg0Relevance) {
        this.pCfg0Relevance = pCfg0Relevance;
    }

    public Integer getIsFeatureSent() {
        return isFeatureSent;
    }

    public void setIsFeatureSent(Integer isFeatureSent) {
        this.isFeatureSent = isFeatureSent;
    }

    public Integer getIsRelevanceSent() {
        return isRelevanceSent;
    }

    public void setIsRelevanceSent(Integer isRelevanceSent) {
        this.isRelevanceSent = isRelevanceSent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Date getCfgEffectedDate() {
        return cfgEffectedDate;
    }

    public void setCfgEffectedDate(Date cfgEffectedDate) {
        this.cfgEffectedDate = cfgEffectedDate;
    }

    public Date getCfgAbolishDate() {
        return cfgAbolishDate;
    }

    public void setCfgAbolishDate(Date cfgAbolishDate) {
        this.cfgAbolishDate = cfgAbolishDate;
    }

    public Integer getCfgStatus() {
        return cfgStatus;
    }

    public void setCfgStatus(Integer cfgStatus) {
        this.cfgStatus = cfgStatus;
    }

    public Integer getCfgIsInProcess() {
        return cfgIsInProcess;
    }

    public void setCfgIsInProcess(Integer cfgIsInProcess) {
        this.cfgIsInProcess = cfgIsInProcess;
    }

    public Long getVwoId() {
        return vwoId;
    }

    public void setVwoId(Long vwoId) {
        this.vwoId = vwoId;
    }

    public String getVwoNum() {
        return vwoNum;
    }

    public void setVwoNum(String vwoNum) {
        this.vwoNum = vwoNum;
    }
}