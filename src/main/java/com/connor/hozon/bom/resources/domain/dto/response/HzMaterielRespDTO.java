package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
public class HzMaterielRespDTO extends BaseChangeRespDTO {

    private Integer No;
    /**
     * 主键id
     */
    private String puid;
    /**
     * 物料代码
     */
    private String pMaterielCode;
    /**
     * 物料类型
     */
    private String pMaterielType;
    /**
     * 物料中文描述
     */
    private String pMaterielDesc;
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
    private String pInventedPart;
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
    private String pLoosePartFlag;
    /**
     * 工厂代码
     */
    private String factoryCode;

    /**
     * 采购类型
     */
    private String resource;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(String pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public String getpMaterielType() {
        return pMaterielType;
    }

    public void setpMaterielType(String pMaterielType) {
        this.pMaterielType = pMaterielType;
    }

    public String getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(String pMaterielDesc) {
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

    public String getpInventedPart() {
        return pInventedPart;
    }

    public void setpInventedPart(String pInventedPart) {
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

    public String getpLoosePartFlag() {
        return pLoosePartFlag;
    }

    public void setpLoosePartFlag(String pLoosePartFlag) {
        this.pLoosePartFlag = pLoosePartFlag;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }
}
