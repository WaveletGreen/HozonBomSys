package com.connor.hozon.bom.resources.dto.request;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
public class UpdateHzMaterielReqDTO {
    /**
     * 主键id
     */
    private String puid;
    /**
     * 物料代码
     */
    private Object pMaterielCode;
    /**
     * 物料类型
     */
    private Object pMaterielType;
    /**
     * 物料中文描述
     */
    private Object pMaterielDesc;
    /**
     * 项目id
     */
    private String pPertainToProjectPuid;
    /**
     * 物料描述 英文
     */
    private String pMaterielDescEn;
    /**
     * 基本计量单位
     */
    private String pBasicUnitMeasure;
    /**
     * 虚拟件标识(1 ,0 )
     */
    private Integer pInventedPart;
    /**
     * 备件&原材料双属性标示
     */
    private String pSpareMaterial;
    /**
     * VIN前置号
     */
    private String pVinPerNo;
    /**
     * 颜色件标识
     */
    private Integer pColorPart;
    /**
     * 毛重
     */
    private String pHeight;
    /**
     * 内外饰标识
     */
    private Integer pInOutSideFlag;
    /**
     * 3C件标识
     */
    private Integer p3cPartFlag;
    /**
     * MRP控制者
     */
    private String pMrpController;
    /**
     * 零件重要度
     */
    private String pPartImportantDegree;
    /**
     * 散件标志
     */
    private Integer pLoosePartFlag;
    /**
     * 物料类型  严格按照注释来读写数据
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

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public Object getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(Object pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public Object getpMaterielType() {
        return pMaterielType;
    }

    public void setpMaterielType(Object pMaterielType) {
        this.pMaterielType = pMaterielType;
    }

    public Object getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(Object pMaterielDesc) {
        this.pMaterielDesc = pMaterielDesc;
    }

    public String getpPertainToProjectPuid() {
        return pPertainToProjectPuid;
    }

    public void setpPertainToProjectPuid(String pPertainToProjectPuid) {
        this.pPertainToProjectPuid = pPertainToProjectPuid;
    }

    public String getpMaterielDescEn() {
        return pMaterielDescEn;
    }

    public void setpMaterielDescEn(String pMaterielDescEn) {
        this.pMaterielDescEn = pMaterielDescEn;
    }

    public String getpBasicUnitMeasure() {
        return pBasicUnitMeasure;
    }

    public void setpBasicUnitMeasure(String pBasicUnitMeasure) {
        this.pBasicUnitMeasure = pBasicUnitMeasure;
    }

    public Integer getpInventedPart() {
        return pInventedPart;
    }

    public void setpInventedPart(Integer pInventedPart) {
        this.pInventedPart = pInventedPart;
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

    public Integer getpColorPart() {
        return pColorPart;
    }

    public void setpColorPart(Integer pColorPart) {
        this.pColorPart = pColorPart;
    }

    public String getpHeight() {
        return pHeight;
    }

    public void setpHeight(String pHeight) {
        this.pHeight = pHeight;
    }

    public Integer getpInOutSideFlag() {
        return pInOutSideFlag;
    }

    public void setpInOutSideFlag(Integer pInOutSideFlag) {
        this.pInOutSideFlag = pInOutSideFlag;
    }

    public Integer getP3cPartFlag() {
        return p3cPartFlag;
    }

    public void setP3cPartFlag(Integer p3cPartFlag) {
        this.p3cPartFlag = p3cPartFlag;
    }

    public String getpMrpController() {
        return pMrpController;
    }

    public void setpMrpController(String pMrpController) {
        this.pMrpController = pMrpController;
    }

    public String getpPartImportantDegree() {
        return pPartImportantDegree;
    }

    public void setpPartImportantDegree(String pPartImportantDegree) {
        this.pPartImportantDegree = pPartImportantDegree;
    }

    public Integer getpLoosePartFlag() {
        return pLoosePartFlag;
    }

    public void setpLoosePartFlag(Integer pLoosePartFlag) {
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
}
