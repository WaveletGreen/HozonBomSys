/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.request;


import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
public class EditHzMaterielReqDTO extends BaseDTO {

    private String puid;

    private String projectId;

    /**
     * 备件&原材料双属性标示
     */
    private String pSpareMaterial;
    /**
     * VIN前置号
     */
    private String pVinPerNo;

    /**
     * MRP控制者
     */
    private String pMrpController;
    /**
     * 散件标志
     */
    private String pLoosePartFlag;
    /**
     * 物料类型
     * （11 整车超级物料主数据
     * 21 整车衍生物料主数据
     * 31 虚拟件物料主数据
     * 41半成品物料主数据
     * 51 生产性外购物料主数据
     * 61自制备件物料主数据）
     */
    private Integer pMaterielDataType;

    /**
     * 工厂代码
     */
    private String factoryCode;

    // 以下字段本不应该在物料中进行维护；，暂时先放开
    /**
     * 基本计量单位
     */
    private String pBasicUnitMeasure;
    /**
     * 虚拟件标识(1 ,0 )
     */
    private String pInventedPart;

    /**
     * 颜色件标识
     */
    private String pColorPart;
    /**
     * 毛重
     */
    private String pHeight;
    /**
     * 内外饰标识
     */
    private String pInOutSideFlag;
    /**
     * 3C件标识
     */
    private String p3cPartFlag;

    /**
     * 零件重要度
     */
    private String pPartImportantDegree;

    /**
     * 采购类型
     */
    private String resource;

    private  String pMaterielDesc;


    public String getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(String pMaterielDesc) {
        this.pMaterielDesc = pMaterielDesc;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getpSpareMaterial() {
        return pSpareMaterial;
    }

    public void setpSpareMaterial(String pSpareMaterial) {
        this.pSpareMaterial = pSpareMaterial;
    }

    public String getpVinPerNo() {
        return pVinPerNo;
    }

    public void setpVinPerNo(String pVinPerNo) {
        this.pVinPerNo = pVinPerNo;
    }

    public String getpMrpController() {
        return pMrpController;
    }

    public void setpMrpController(String pMrpController) {
        this.pMrpController = pMrpController;
    }

    public String getpLoosePartFlag() {
        return pLoosePartFlag;
    }

    public void setpLoosePartFlag(String pLoosePartFlag) {
        this.pLoosePartFlag = pLoosePartFlag;
    }

    public Integer getpMaterielDataType() {
        return pMaterielDataType;
    }

    public void setpMaterielDataType(Integer pMaterielDataType) {
        this.pMaterielDataType = pMaterielDataType;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getpBasicUnitMeasure() {
        return pBasicUnitMeasure;
    }

    public void setpBasicUnitMeasure(String pBasicUnitMeasure) {
        this.pBasicUnitMeasure = pBasicUnitMeasure;
    }

    public String getpInventedPart() {
        return pInventedPart;
    }

    public void setpInventedPart(String pInventedPart) {
        this.pInventedPart = pInventedPart;
    }

    public String getpColorPart() {
        return pColorPart;
    }

    public void setpColorPart(String pColorPart) {
        this.pColorPart = pColorPart;
    }

    public String getpHeight() {
        return pHeight;
    }

    public void setpHeight(String pHeight) {
        this.pHeight = pHeight;
    }

    public String getpInOutSideFlag() {
        return pInOutSideFlag;
    }

    public void setpInOutSideFlag(String pInOutSideFlag) {
        this.pInOutSideFlag = pInOutSideFlag;
    }

    public String getP3cPartFlag() {
        return p3cPartFlag;
    }

    public void setP3cPartFlag(String p3cPartFlag) {
        this.p3cPartFlag = p3cPartFlag;
    }

    public String getpPartImportantDegree() {
        return pPartImportantDegree;
    }

    public void setpPartImportantDegree(String pPartImportantDegree) {
        this.pPartImportantDegree = pPartImportantDegree;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
