/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

import java.util.Map;

/**
 * @Author: zhangl
 * @Date: 2019/8/16
 * @Description: 快速添加一条EBOM
 */
public class QuickAddHzEbomReqDTO extends BaseDTO {

    /**
     * 父零件号
     */
    private String  parentId;
    /**
     * 项目id
     */
    private String projectId;

    /**
     * EBOM 信息
     *
     */
    private Map<String,Object> map;

    private String puid;

    private String lineNo;

    private String lineId;
    /**
     * EPL 零部件id
     */
    private Long eplId;
    /**
//     * 专业
//     */
//    private String pBomOfWhichDept;//1
//
//    /**
//     * 零件分类
//     */
//    private String pBomLinePartClass;
//
//    /**
//     * 零件来源
//     */
//    private String pBomLinePartResource;
//
//    /**
//     * Bom行对应的零件名
//     */
//    private String pBomLinePartName;
//    /**
//     * 英文名称
//     */
//    private String pBomLinePartEnName;
//
//    private String fastener;

    private String fna;

//    private String pFastener;
//
//    private String p3cpartFlag;
//
//    private String pInOutSideFlag;

    private String pUpc;

    private String pFnaDesc;
//
//    private String pCreateName;
//
//    private String pUpdateName;

//    private String pUnit;
//
//    private String pPictureNo;
//
//    private String pPictureSheet;
//
//    private String pMaterialHigh;
//
//    private String pMaterial1;
//
//    private String pMaterial2;
//
//    private String pMaterial3;
//
//    private String pDensity;
//
//    private String pMaterialStandard;
//
//    private String pSurfaceTreat;
//
//    private String pTextureColorNum;
//
//    private String pManuProcess;
//
//    private String pSymmetry;
//
//    private String pImportance;
//
//    private String pRegulationFlag;
//
//    private String pBwgBoxPart;
//
//    private String pDevelopType;
//
//    private String pDataVersion;
//
//    private String pTargetWeight;
//
//    private String pFeatureWeight;
//
//    private String pActualWeight;
//
//    private String pFastenerStandard;
//
//    private String pFastenerLevel;
//
//    private String pTorque;
//
//    private String pDutyEngineer;
//
//    private String pSupply;
//
//    private String pSupplyCode;
//
//    private String pRemark;
//
//    private String pRegulationCode;

    private String number;

//    private String pBuyEngineer;

    private String colorPart;

    /**
     * 备件
     */
    private String sparePart;
    /**
     * 备件编号
     */
    private String sparePartNum;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSparePart() {
        return sparePart;
    }

    public void setSparePart(String sparePart) {
        this.sparePart = sparePart;
    }

    public String getSparePartNum() {
        return sparePartNum;
    }

    public void setSparePartNum(String sparePartNum) {
        this.sparePartNum = sparePartNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getpUpc() {
        return pUpc;
    }

    public void setpUpc(String pUpc) {
        this.pUpc = pUpc;
    }

    public String getpFnaDesc() {
        return pFnaDesc;
    }

    public void setpFnaDesc(String pFnaDesc) {
        this.pFnaDesc = pFnaDesc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColorPart() {
        return colorPart;
    }

    public void setColorPart(String colorPart) {
        this.colorPart = colorPart;
    }

    public Long getEplId() {
        return eplId;
    }

    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }
}
