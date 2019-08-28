/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet;

import lombok.Data;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单基本车型模型数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzFullCfgModel {
    private Long id;

    private String modModelUid;

    private String modCfg0Uid;

    private Short modPointType;

    private Date flModCreateDate;

    private Date flModUpdateDate;

    private String flModCreator;

    private String flModLastUpdater;

    private Long flModVersion;
    /**
     * 2Y层主键
     */
    private String flModelBomlineUid;


}