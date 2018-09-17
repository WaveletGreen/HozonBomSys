package com.connor.hozon.bom.resources.domain.dto.request;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/22
 * @Description: 新增一条EBOM
 */
public class AddHzEbomReqDTO {

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
     * 专业
     */
    private String pBomOfWhichDept;//1

    /**
     * 零件分类
     */
    private String pBomLinePartClass;

    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;
    /**
     * 英文名称
     */
    private String pBomLinePartEnName;

    private String fastener;

    private String fna;

    private String pFastener;

    private String p3cpartFlag;

    private String pInOutSideFlag;

    private String pUpc;

    private String pFnaDesc;

    private String pCreateName;

    private String pUpdateName;

    private String pUnit;

    private String pPictureNo;

    private String pPictureSheet;

    private String pMaterialHigh;

    private String pMaterial1;

    private String pMaterial2;

    private String pMaterial3;

    private String pDensity;

    private String pMaterialStandard;

    private String pSurfaceTreat;

    private String pTextureColorNum;

    private String pManuProcess;

    private String pSymmetry;

    private String pImportance;

    private String pRegulationFlag;

    private String pBwgBoxPart;

    private String pDevelopType;

    private String pDataVersion;

    private String pTargetWeight;

    private String pFeatureWeight;

    private String pActualWeight;

    private String pFastenerStandard;

    private String pFastenerLevel;

    private String pTorque;

    private String pDutyEngineer;

    private String pSupply;

    private String pSupplyCode;

    private String pRemark;

    private String pRegulationCode;

    private String number;

    private String pBuyEngineer;

    private String colorPart;

    public String getColorPart() {
        return colorPart;
    }

    public void setColorPart(String colorPart) {
        this.colorPart = colorPart;
    }

    public String getpBuyEngineer() {
        return pBuyEngineer;
    }

    public void setpBuyEngineer(String pBuyEngineer) {
        this.pBuyEngineer = pBuyEngineer;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }

    public String getFastener() {
        return fastener;
    }

    public void setFastener(String fastener) {
        this.fastener = fastener;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getpFastener() {
        return pFastener;
    }

    public void setpFastener(String pFastener) {
        this.pFastener = pFastener;
    }

    public String getP3cpartFlag() {
        return p3cpartFlag;
    }

    public void setP3cpartFlag(String p3cpartFlag) {
        this.p3cpartFlag = p3cpartFlag;
    }

    public String getpInOutSideFlag() {
        return pInOutSideFlag;
    }

    public void setpInOutSideFlag(String pInOutSideFlag) {
        this.pInOutSideFlag = pInOutSideFlag;
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

    public String getpCreateName() {
        return pCreateName;
    }

    public void setpCreateName(String pCreateName) {
        this.pCreateName = pCreateName;
    }

    public String getpUpdateName() {
        return pUpdateName;
    }

    public void setpUpdateName(String pUpdateName) {
        this.pUpdateName = pUpdateName;
    }

    public String getpUnit() {
        return pUnit;
    }

    public void setpUnit(String pUnit) {
        this.pUnit = pUnit;
    }

    public String getpPictureNo() {
        return pPictureNo;
    }

    public void setpPictureNo(String pPictureNo) {
        this.pPictureNo = pPictureNo;
    }

    public String getpPictureSheet() {
        return pPictureSheet;
    }

    public void setpPictureSheet(String pPictureSheet) {
        this.pPictureSheet = pPictureSheet;
    }

    public String getpMaterialHigh() {
        return pMaterialHigh;
    }

    public void setpMaterialHigh(String pMaterialHigh) {
        this.pMaterialHigh = pMaterialHigh;
    }

    public String getpMaterial1() {
        return pMaterial1;
    }

    public void setpMaterial1(String pMaterial1) {
        this.pMaterial1 = pMaterial1;
    }

    public String getpMaterial2() {
        return pMaterial2;
    }

    public void setpMaterial2(String pMaterial2) {
        this.pMaterial2 = pMaterial2;
    }

    public String getpMaterial3() {
        return pMaterial3;
    }

    public void setpMaterial3(String pMaterial3) {
        this.pMaterial3 = pMaterial3;
    }

    public String getpDensity() {
        return pDensity;
    }

    public void setpDensity(String pDensity) {
        this.pDensity = pDensity;
    }

    public String getpMaterialStandard() {
        return pMaterialStandard;
    }

    public void setpMaterialStandard(String pMaterialStandard) {
        this.pMaterialStandard = pMaterialStandard;
    }

    public String getpSurfaceTreat() {
        return pSurfaceTreat;
    }

    public void setpSurfaceTreat(String pSurfaceTreat) {
        this.pSurfaceTreat = pSurfaceTreat;
    }

    public String getpTextureColorNum() {
        return pTextureColorNum;
    }

    public void setpTextureColorNum(String pTextureColorNum) {
        this.pTextureColorNum = pTextureColorNum;
    }

    public String getpManuProcess() {
        return pManuProcess;
    }

    public void setpManuProcess(String pManuProcess) {
        this.pManuProcess = pManuProcess;
    }

    public String getpSymmetry() {
        return pSymmetry;
    }

    public void setpSymmetry(String pSymmetry) {
        this.pSymmetry = pSymmetry;
    }

    public String getpImportance() {
        return pImportance;
    }

    public void setpImportance(String pImportance) {
        this.pImportance = pImportance;
    }

    public String getpRegulationFlag() {
        return pRegulationFlag;
    }

    public void setpRegulationFlag(String pRegulationFlag) {
        this.pRegulationFlag = pRegulationFlag;
    }

    public String getpBwgBoxPart() {
        return pBwgBoxPart;
    }

    public void setpBwgBoxPart(String pBwgBoxPart) {
        this.pBwgBoxPart = pBwgBoxPart;
    }

    public String getpDevelopType() {
        return pDevelopType;
    }

    public void setpDevelopType(String pDevelopType) {
        this.pDevelopType = pDevelopType;
    }

    public String getpDataVersion() {
        return pDataVersion;
    }

    public void setpDataVersion(String pDataVersion) {
        this.pDataVersion = pDataVersion;
    }

    public String getpTargetWeight() {
        return pTargetWeight;
    }

    public void setpTargetWeight(String pTargetWeight) {
        this.pTargetWeight = pTargetWeight;
    }

    public String getpFeatureWeight() {
        return pFeatureWeight;
    }

    public void setpFeatureWeight(String pFeatureWeight) {
        this.pFeatureWeight = pFeatureWeight;
    }

    public String getpActualWeight() {
        return pActualWeight;
    }

    public void setpActualWeight(String pActualWeight) {
        this.pActualWeight = pActualWeight;
    }

    public String getpFastenerStandard() {
        return pFastenerStandard;
    }

    public void setpFastenerStandard(String pFastenerStandard) {
        this.pFastenerStandard = pFastenerStandard;
    }

    public String getpFastenerLevel() {
        return pFastenerLevel;
    }

    public void setpFastenerLevel(String pFastenerLevel) {
        this.pFastenerLevel = pFastenerLevel;
    }

    public String getpTorque() {
        return pTorque;
    }

    public void setpTorque(String pTorque) {
        this.pTorque = pTorque;
    }

    public String getpDutyEngineer() {
        return pDutyEngineer;
    }

    public void setpDutyEngineer(String pDutyEngineer) {
        this.pDutyEngineer = pDutyEngineer;
    }

    public String getpSupply() {
        return pSupply;
    }

    public void setpSupply(String pSupply) {
        this.pSupply = pSupply;
    }

    public String getpSupplyCode() {
        return pSupplyCode;
    }

    public void setpSupplyCode(String pSupplyCode) {
        this.pSupplyCode = pSupplyCode;
    }

    public String getpRemark() {
        return pRemark;
    }

    public void setpRemark(String pRemark) {
        this.pRemark = pRemark;
    }

    public String getpRegulationCode() {
        return pRegulationCode;
    }

    public void setpRegulationCode(String pRegulationCode) {
        this.pRegulationCode = pRegulationCode;
    }
}
