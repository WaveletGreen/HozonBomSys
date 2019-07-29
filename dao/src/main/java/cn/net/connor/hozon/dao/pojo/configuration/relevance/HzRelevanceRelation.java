/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.relevance;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性与各个特性值和颜色值对应关系
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzRelevanceRelation {
    private Long id;
    //特性值id
    private String rrCfg0Uid;
    //配色方案id
    private String rrColorModelUid;
    //颜色id
    private String rrColorUid;
    //相关性id
    private Long rrRelevanceId;
    //特性id
    private String rrCfgFamilyUid;

    private String rrCreator;

    private Date rrCreateDate;

    private String rrUpdater;

    private Date rrUpdatDate;

    private String rrReserve1;

    private String rrReserve2;

    private String rrReserve3;

    private String rrReserve4;

    private String rrReserve5;

    private String rrReserve6;

    private String rrReserve7;

    private String rrReserve8;

    private String rrReserve9;

    private String rrReserve10;

    private String rrReserve11;

    private String rrReserve12;

    private String rrReserve13;

    private String rrReserve14;

    private String rrReserve15;

    private Long rrVwoId;

    private Long rrRevision;
}