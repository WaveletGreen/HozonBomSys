/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.modelColor;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配色方案变更详情数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzCmcrDetailChange {
    /**
     * 主键
     */
    private Long cmcrDetailCgId;
    /**
     * 源数据PUID，从源数据中继承过来，外键，级联删除
     */
    private String cmcrDetailSrcPuid;
    /**
     * 特性，从源数据中继承过来，外键，不是特性值外键，删除时set null
     */
    private String cmcrDetailSrcCfgUid;
    /**
     * 颜色外键，从源数据中继承过来，外键，删除时set null
     */
    private String cmcrDetailSrcColorUid;
    /**
     * 主配置UID，从源数据中继承过来，外键，级联删除
     */
    private String cmcrDetailSrcCfgMainUid;
    /**
     * vwo主键，外键，级联删除
     */
    private Long cmcrDetailCgVwoId;
    /**
     * 源数据的创建时间
     */
    private Date cmcrDetailSrcCreateDate;
    /**
     * 源数据的修改时间
     */
    private Date cmcrDetailSrcModifyDate;
    /**
     * 源数据的创建者
     */
    private String cmcrDetailSrcCreator;
    /**
     * 源数据的修改者
     */
    private String cmcrDetailSrcModifier;
    /**
     * 配色方案主数据PUID，从源数据中继承过来，外键，级联删除
     */
    private String cmcrDetailSrcModelPuid;
    /**
     * 特性代码，万一特性删除了，还能知道是哪个特性
     */
    private String cmcrDetailCgFeatureCode;
    /**
     * 特性名，意外同上
     */
    private String cmcrDetailCgFeatureName;
    /**
     * 颜色代码，意外同上
     */
    private String cmcrDetailCgColorCode;
    /**
     * 颜色名称，意外同上
     */
    private String cmcrDetailCgColorName;
    /**
     * 创建时间
     */
    private Date cmcrDetailCgCreateDate;
    /**
     * 创建者，即为操作者
     */
    private String cmcrDetailCgCreator;
    /**
     * 修改时间
     */
    private Date cmcrDetailCgUpdateDate;
    /**
     * 修改者
     */
    private String cmcrDetailCgUpdater;
    /**
     * 保留字段
     */
    private String cmcrDetailCgTitle;
    /**
     * 对应的2Y层是否为颜色件，因为颜色件是动态回传到后台，因此需要该字段标记变更前后的颜色变化，理想情况下的变更不影响表头的变化，则该字段一直是1，否则根据动态变化会将其设置为1/0
     */
    private Integer cmcrDetailCgIsColorful;
    /**
     * 保留字段
     */
    private String cmcrDetailCgReverse3;
    /**
     * 保留字段
     */
    private String cmcrDetailCgReverse4;
    /**
     * 保留字段
     */
    private String cmcrDetailCgReverse5;
    /**
     * 保留字段
     */
    private String cmcrDetailCgReverse6;
    /**
     * 数据库中的table名
     */
    private String whichTable;
    /**
     * 序列名称
     */
    private String seqName;
    /**
     * 颜色代码
     */
    private String colorCode;
}