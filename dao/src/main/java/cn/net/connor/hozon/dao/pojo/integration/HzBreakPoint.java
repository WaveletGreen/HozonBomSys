/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.integration;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 断点信息
 */
@Data
public class HzBreakPoint implements Serializable {
    private static final long serialVersionUID = 7391744885544086127L;
    /**
     * 主键
     */
    private Long pid;
    /**
     * 数据包号
     */
    private String packNo;
    /**
     * 行号
     */
    private String item;
    /**
     * TC-ECN
     */
    private String tcEcn;
    /**
     * SAP-ECN
     */
    private String ecn;
    /**
     * 断点日期
     */
    private String breakDate;
    /**
     * 断点前ECN
     */
    private String preEcn;
    /**
     * 前ECN结束日期
     */
    private String preEcnFinishDate;
    /**
     * 断点起始vin
     */
    private String startVin;
    /**
     * 断点执行工号
     */
    private String executeUserNo;
    /**
     * 断点处理日期
     */
    private String breakHandleDate;
    /**
     * 断点处理时间
     */
    private String breakHandlerTime;
    /**
     * 预留字段1
     */
    private String reserve1;
    /**
     * 预留字段2
     */
    private String reserve2;
    /**
     * 预留字段3
     */
    private String reserve3;
    /**
     * 预留字段4
     */
    private String reserve4;
    /**
     * 预留字段5
     */
    private String reserve5;
    /**
     * 断点回传到BOM系统时间
     */
    private Date feedbackTime;

    public static String reflectToDBField(String property) {
        switch (property) {
            /**
             * 主键
             */
            case "pid"://代码字段
                return "PID";//对应数据库字段信息
            /**
             * 数据包号
             */
            case "packNo":
                return "P_Packno";
            default:
                return null;
        }
    }
}