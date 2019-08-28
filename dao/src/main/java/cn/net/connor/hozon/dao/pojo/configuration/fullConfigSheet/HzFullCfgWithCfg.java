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
 * @Description: 全配置BOM一级清单2Y与特性值关联
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzFullCfgWithCfg {
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


}