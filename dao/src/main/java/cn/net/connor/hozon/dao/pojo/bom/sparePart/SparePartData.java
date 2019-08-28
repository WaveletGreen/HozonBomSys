/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.sparePart;

import lombok.Data;
import lombok.ToString;

/**
 * 备件零件对象，dao层对象
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:04
 * @Modified By:
 */
@Data
@ToString
public class SparePartData {
    /**
     * 主键
     */
    private Long id;
    /**
     * 级别
     */
    private String level;
    /**
     * 专业
     */
    private String major;
    /**
     * 层级
     */
    private String hierarchy;
    /**
     * A/D/S
     */
    private String ads;
    /**
     * 生产零件号
     */
    private String productivePartCode;
    /**
     * 生产零件名称
     */
    private String productivePartName;
    /**
     * 备件零件号
     */
    private String sparePartCode;
    /**
     * 备件零件名称
     */
    private String sparePartName;
    /**
     * 是否备件
     */
    private Integer isSparePart;
    /**
     * 单位
     */
    private String unit;
    /**
     * 专业部门
     */
    private String department;
    /**
     * 责任工程师
     */
    private String responsibleEngineer;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 代码
     */
    private String code;
    /**
     * 采购工程师
     */
    private String purchasingEngineer;
    /**
     * 备注
     */
    private String remark;
    /**
     * 零件分类
     */
    private String partClass;
    /**
     * 车间1
     */
    private String workshop1;
    /**
     * 车间2
     */
    private String workshop2;

    /**
     * 关联的EBOM的ID，充当外键
     */
    private String relEbomLineId;
    /**
     * 图号
     */
    private String drawingNum;
    /**
     * 预留字段
     */
    private String reserved2;
    /**
     * 预留字段
     */
    private String reserved3;
    /**
     * 预留字段
     */
    private String reserved4;
    /**
     * 预留字段
     */
    private String reserved5;

}