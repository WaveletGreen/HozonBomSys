package com.connor.hozon.bom.bomSystem.bean;

import lombok.Data;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
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

    private BigDecimal flCfgVersion;
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
    private HzCfg0Record cfg;
    /**
     * 2Y层
     */
    private HzBomLineRecord bomLine;
}
