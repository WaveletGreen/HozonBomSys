package com.connor.hozon.bom.resources.domain.dto.response;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
@Data
public class HzSingleVehiclesBomRespDTO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * Bom行对应的零件号
     */
    private String lineId;

    /**
     * 2Y层归属哪个部门
     */
    private String pBomOfWhichDept;
    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;

    /**
     * 英文名称
     */
    private String pBomLinePartEnName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * 备件
     */
    private String sparePart;
    /**
     *备件编号
     */
    private String sparePartNum;
    /**
     * 工艺路线
     */
    private String processRoute;
    /**
     * 人工工时
     */
    private String laborHour;
    /**
     *节拍
     */
    private String rhythm;
    /**
     * 焊点
     */
    private String solderJoint;
    /**
     * 机物料
     */
    private String machineMaterial;

    /**
     * 标准件
     */
    private String standardPart;
    /**
     * 工具
     */
    private String tools;
    /**
     * 废品
     */
    private String wasterProduct;
    /**
     * 变更
     */
    private String change;
    /**
     * 变更编号
     */
    private String changeNum;

    /**
     * 采购件类型
     */
    private String resource;

    /**
     * 发货料库存地点
     */
    private String pStockLocation;

    /**
     * 对应于单车清单的主键
     */
    private Long singleVehiclesId;

    /**
     * 层级
     */
    private String level;

    /**
     * bom排列序号
     */
    private Integer No;

    /**
     * 工厂
     */
    private String pFactoryCode;
}
