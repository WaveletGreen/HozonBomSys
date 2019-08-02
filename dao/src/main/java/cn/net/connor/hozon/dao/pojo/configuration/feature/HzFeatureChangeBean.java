/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.feature;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性变更bean
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
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
    private String headDesc;

    public HzFeatureValue getHzCfg0Record() {
        HzFeatureValue hzFeatureValue = new HzFeatureValue();
        hzFeatureValue.setPuid(this.getCfgPuid());
        hzFeatureValue.setFeatureValueCode(this.getFeatureValueName());
        hzFeatureValue.setpCfg0Desc(this.getFeatureValueDesc());
        hzFeatureValue.setpCfg0FamilyName(this.getFeatureName());
        hzFeatureValue.setpCfg0FamilyPuid(this.getFeaturePuid());
        hzFeatureValue.setpCfg0MainItemPuid(this.getCfg0MainItemPuid());
        hzFeatureValue.setpH9featureenname(this.getH9featureenname());
        hzFeatureValue.setpCfg0FamilyDesc(this.getFeatureDesc());
        hzFeatureValue.setpCfg0Relevance(this.getCfg0Relevance());
        hzFeatureValue.setCfgStatus(0);
        hzFeatureValue.setCfgIsInProcess(0);
        hzFeatureValue.setVwoId(null);
        return hzFeatureValue;
    }
}