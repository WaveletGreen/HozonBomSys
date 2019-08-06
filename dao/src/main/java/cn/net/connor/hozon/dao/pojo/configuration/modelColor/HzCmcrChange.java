/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.modelColor;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配色方案变更主数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzCmcrChange {
    /**
     * 主键，从序列中获得
     */
    private Long cmcrCgId;
    /**
     * VWO号，外键，级联删除
     */
    private Long cmcrCgVwoId;
    /**
     * 车身颜色代码，要从特性中获取，万一特性删除了，还能保留该值，该值与CMCR_CG_MODEL_SHELL保持一致
     */
    private String cmcrCgShellCode;
    /**
     * 变更创建时间
     */
    private Date cmcrCgCreateDate;
    /**
     * 创建者
     */
    private String cmcrCgCreator;
    /**
     * 变更修改时间
     */
    private Date cmcrCgUpdateDate;
    /**
     * 修改者
     */
    private String cmcrCgUpdater;
    /**
     * 保留
     */
    private String cmcrCgReserve1;
    /**
     * 保留
     */
    private String cmcrCgReserve2;
    /**
     * 保留
     */
    private String cmcrCgReserve3;
    /**
     * 保留
     */
    private String cmcrCgReserve4;
    /**
     * 保留
     */
    private String cmcrCgReserve5;
    /**
     * 保留
     */
    private String cmcrCgReserve6;
    /**
     * 保留
     */
    private String cmcrCgReserve7;
    /**
     * 保留
     */
    private String cmcrCgReserve8;
    /**
     * 主配置UID，外键，级联删除
     */
    private String cmcrSrcMainCfg;
    /**
     * 源数据UID，外键，级联删除
     */
    private String cmcrSrcPuid;
    /**
     * 油漆车身总成，保存的是真实的颜色值，创建成功后将不可修改
     */
    private String cmcrSrcModelShell;
    /**
     * 颜色UID，外键，当颜色被删除之后，该值将被set null，其Code值将从CMCR_CG_MODEL_SHELL中获得，
     */
    private String cmcrSrcColorUid;
    /**
     * 特性值的外键，只能是车身颜色的特性值，车身颜色的值与油漆车身总成的值一样
     */
    private String cmcrSrcShellCfg0Puid;
    /**
     * 是否多色
     */
    private String cmcrSrcColorIsMultiply;
    /**
     * 源创建时间
     */
    private Date cmcrSrcCreateDate;
    /**
     * 源创建人
     */
    private String cmcrSrcCreator;
    /**
     * 源修改时间
     */
    private Date cmcrSrcUpdateDate;
    /**
     * 源修改人
     */
    private String cmcrSrcUpdater;
    /**
     * 颜色车型代码
     */
    private String cmcrSrcCodeOfColorMod;
    /**
     * 颜色车型描述
     */
    private String cmcrSrcDescOfColorMod;
    /**
     * 数据库中的table名
     */
    private String whichTable;
    /**
     * 序列名称
     */
    private String seqName;
    /**
     * 变更状态，0为审核中，1为生效
     */
    private Integer cmcrChangeStatus;

    private Integer cmcrSrcStatus;

}