/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.bom;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2019/1/2
 * @Description:
 */
@Data
public class HzBOMScheduleTask implements Serializable {
    private static final long serialVersionUID = 7230992893451814791L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 配置物料特性表是否产生变更
     */
    private Integer configFeatureChanged;

    /**
     * PBOM 是否产生变更
     */
    private Integer pbomChanged;

    /**
     * MBOM 是否产生变更
     */
    private Integer mbomChanged;

    /**
     * 数据审核通过时间
     */
    private Date auditPassedTime;

    /**
     * 变更后数据同步完成时间
     */
    private Date dataSynchronizedTime;

    /**
     * 变更表单id
     */
    private Long orderId;

    /**
     * 是否同步已完成
     */
    private Integer hasSynchronized;

}
