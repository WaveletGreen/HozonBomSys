/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.vwo;

import lombok.Getter;
import lombok.Setter;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性变更bean
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzFeatureChangeBean {
    /**
     * 数据库中的puid
     */
    private Long id;
    /**
     * 选项值的objectid
     */
    private String featureValueName;
    /**
     * 配置描述
     */
    private String featureValueDesc;
    /**
     * 选项族的名称，也是选项族的objectid
     */
    private String featureName;
    /**
     * 选项族的数据库puid
     */
    private String featurePuid;
    /**
     * 主配置的puid，用这个可以找到主配置的对象
     */
    private String cfg0MainItemPuid;
    /**
     * 特性英文名
     */
    private String h9featureenname;
    /**
     * 族描述
     */
    private String featureDesc;
    /**
     * 相关性，并不需要在初次导入到Bom系统的时候进行值写入，是在Bom系统进行手动维护，存储规则并传到ERP中
     */
    private String cfg0Relevance;
    /**
     * 创建人
     */
    private String featureCreator;
    /**
     * 创建日期
     */
    private Date featureCreateDate;
    /**
     * 最近一次修改者
     */
    private String featureLastModifier;
    /**
     * 最近一次修改日期
     */
    private Date featureLastModifyDate;
    /**
     * 特性是否已成功发送到SAP
     */
    private Integer isFeatureSent;
    /**
     * 相关性是否已成功发送到SAP
     */
    private Integer isRelevanceSent;
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
     * 当前的特性值的主键，作为外键
     */
    private String cfgPuid;

    /**
     * 流程发起的时间
     */
    private Date processStartDate;
    /**
     * 流程发起人
     */
    private String processStarter;
    /**
     * 流程发起的时间
     */
    private Date processFinishDate;
    /**
     * 流程发起人
     */
    private String processFinisher;
    /**
     * 流程状态
     */
    private Integer processStatus;

    /**
     * 表名，bean中特有，用于识别变更前还是变更后的表
     */
    private String tableName;


    /**
     * 变更创建时间
     */
    private Date changeCreateDate;
    /**
     * vwo变更号ID
     */

    private Long vwoId;
    /**
     * 序列名称
     */
    private String seqName;
    /**
     * 变更的头部描述，分别是变更前和变更后
     */
    @Setter
    @Getter
    private String headDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureValueName() {
        return featureValueName;
    }

    public void setFeatureValueName(String featureValueName) {
        this.featureValueName = featureValueName;
    }

    public String getFeatureValueDesc() {
        return featureValueDesc;
    }

    public void setFeatureValueDesc(String featureValueDesc) {
        this.featureValueDesc = featureValueDesc == null ? null : featureValueDesc.trim();
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName == null ? null : featureName.trim();
    }

    public String getFeaturePuid() {
        return featurePuid;
    }

    public void setFeaturePuid(String featurePuid) {
        this.featurePuid = featurePuid == null ? null : featurePuid.trim();
    }

    public String getCfg0MainItemPuid() {
        return cfg0MainItemPuid;
    }

    public void setCfg0MainItemPuid(String cfg0MainItemPuid) {
        this.cfg0MainItemPuid = cfg0MainItemPuid == null ? null : cfg0MainItemPuid.trim();
    }

    public String getH9featureenname() {
        return h9featureenname;
    }

    public void setH9featureenname(String h9featureenname) {
        this.h9featureenname = h9featureenname == null ? null : h9featureenname.trim();
    }

    public String getFeatureDesc() {
        return featureDesc;
    }

    public void setFeatureDesc(String featureDesc) {
        this.featureDesc = featureDesc == null ? null : featureDesc.trim();
    }

    public String getCfg0Relevance() {
        return cfg0Relevance;
    }

    public void setCfg0Relevance(String cfg0Relevance) {
        this.cfg0Relevance = cfg0Relevance == null ? null : cfg0Relevance.trim();
    }

    public String getFeatureCreator() {
        return featureCreator;
    }

    public void setFeatureCreator(String featureCreator) {
        this.featureCreator = featureCreator;
    }

    public Date getFeatureCreateDate() {
        return featureCreateDate;
    }

    public void setFeatureCreateDate(Date featureCreateDate) {
        this.featureCreateDate = featureCreateDate;
    }

    public String getFeatureLastModifier() {
        return featureLastModifier;
    }

    public void setFeatureLastModifier(String featureLastModifier) {
        this.featureLastModifier = featureLastModifier;
    }

    public Date getFeatureLastModifyDate() {
        return featureLastModifyDate;
    }

    public void setFeatureLastModifyDate(Date featureLastModifyDate) {
        this.featureLastModifyDate = featureLastModifyDate;
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

    public String getCfgPuid() {
        return cfgPuid;
    }

    public void setCfgPuid(String cfgPuid) {
        this.cfgPuid = cfgPuid == null ? null : cfgPuid.trim();
    }

    public Date getProcessStartDate() {
        return processStartDate;
    }

    public void setProcessStartDate(Date processStartDate) {
        this.processStartDate = processStartDate;
    }

    public String getProcessStarter() {
        return processStarter;
    }

    public void setProcessStarter(String processStarter) {
        this.processStarter = processStarter;
    }

    public Date getProcessFinishDate() {
        return processFinishDate;
    }

    public void setProcessFinishDate(Date processFinishDate) {
        this.processFinishDate = processFinishDate;
    }

    public String getProcessFinisher() {
        return processFinisher;
    }

    public void setProcessFinisher(String processFinisher) {
        this.processFinisher = processFinisher;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getChangeCreateDate() {
        return changeCreateDate;
    }

    public void setChangeCreateDate(Date changeCreateDate) {
        this.changeCreateDate = changeCreateDate;
    }

    public Long getVwoId() {
        return vwoId;
    }

    public void setVwoId(Long vwoId) {
        this.vwoId = vwoId;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public HzCfg0Record getHzCfg0Record() {
        HzCfg0Record hzCfg0Record = new HzCfg0Record();
        hzCfg0Record.setPuid(this.getCfgPuid());
        hzCfg0Record.setpCfg0ObjectId(this.getFeatureValueName());
        hzCfg0Record.setpCfg0Desc(this.getFeatureValueDesc());
        hzCfg0Record.setpCfg0FamilyName(this.getFeatureName());
        hzCfg0Record.setpCfg0FamilyPuid(this.getFeaturePuid());
        hzCfg0Record.setpCfg0MainItemPuid(this.getCfg0MainItemPuid());
        hzCfg0Record.setpH9featureenname(this.getH9featureenname());
        hzCfg0Record.setpCfg0FamilyDesc(this.getFeatureDesc());
        hzCfg0Record.setpCfg0Relevance(this.getCfg0Relevance());
        hzCfg0Record.setCfgStatus(0);
        hzCfg0Record.setCfgIsInProcess(0);
        hzCfg0Record.setVwoId(null);
        return hzCfg0Record;
    }
}