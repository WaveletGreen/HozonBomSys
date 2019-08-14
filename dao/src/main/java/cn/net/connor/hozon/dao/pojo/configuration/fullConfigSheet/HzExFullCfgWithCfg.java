/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet;

import lombok.Data;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单时间类型数据转换的对象
 * @Date: Created in 2018/9/21 12:20
 * @Modified By:
 */
@Data
public class HzExFullCfgWithCfg {
    private Long id;

    private String cfgCfg0Uid;

    private String cfgBomlineUid;

    private Date flCfgCreateDate;

    private Date flCfgUpdateDate;

    private String flCfgCreator;

    private String flCfgUpdator;

    private String flCfgBomlineName;

    private Long flCfgVersion;
    /**
     * 操作类型,1新增，2更新，3删除
     */
    private Integer flOperationType;
    /**
     * 备注
     */
    private String flComment;
    /***
     * 特性值
     */
    private HzFeatureValue cfg;
    /**
     * 2Y层
     */
    private HzBomLineRecord bomLine;
}
