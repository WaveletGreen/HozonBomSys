/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.integration;

import lombok.Data;
@Data
public class HzMBomToERPBean {

    private Long id;

    /**
     * 主键
     */
    private String puid;
    /**
     * 父项PUID
     */
    private String parentUID;
    /**
     * 原始的BOMLine PUID
     */
    private String bomUid;
    /**
     * 变更号
     */
    private String changeNum;
    /**
     * 变更描述
     */
    private String change;
    /**
     * 工厂代号
     */
    private String factoryCode;
    /**
     * BOM类型
     */
    private String bomType;
    /**
     * item_ID，当物料编号
     */
    private String bomLineId;
    /**
     * BOM顺序号
     */
    private String bomOrderNum;
    /**
     * 是否是2Y层
     */
    private Integer is2Y;
    /**
     * 库存地点
     */
    private String stockLocation;
    /**
     * 相关性
     */
    private String cfg0Relevance;
    /**
     * FNA，装配位置
     */
    private String FNAInfo;
    /**
     * 车间1，作为使用车间
     */
    private String workShop;
    /**
     * 工位
     */
    private String station;
    /**
     * 大对象
     */
//    private byte[] bomLineBlock;
    /**
     * 真实顺序序号
     */
    private String lineIndex;
    /**
     * 是否已发送到SAP，待定
     */
    private Integer isSended;

    /**
     * 数量
     */
    private Integer number;

    private String unit;

    /**
     * todo 这里先不记录是否有过传输
     * 注:单车BOM传输中 只做增加和删除 无修改操作
     */
    private Integer sent;


}
