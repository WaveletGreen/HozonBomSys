package com.connor.hozon.bom.resources.dto.response;

/**
 * Created by haozt on 2018/06/06
 */
public class HzEbomRespDTO {
    /**
     * bom表的puid
     */
    private String puid;
    /**
     * 级别 以2Y层为相对 第1级
     */
    private String rank;

    /**
     * 层级
     */
    private String level;
    /**
     * 零件号
     */
    private String lineId;

    /**
     * 专业
     */
    private String pBomOfWhichDept;

    /**
     * 分组号
     */
    private String groupNum;

    /**
     * 名称
     */
    private String nameZh;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     *单位
     */
    private String pUnit;
    /**
     *分时租赁低配
     */
    private String pRentLow;
    /**
     *分时租赁高配
     */
    private String pRentHigh;
    /**
     *图号
     */
    private String pPictureNo;
    /**
     *安装图号
     */
    private String pInstallPictureNo;
    /**
     *图幅
     */
    private String pMap;
    /**
     *料厚
     */
    private String pMaterialHigh;
    /**
     *材料1
     */
    private String pMaterial1;
    /**
     *材料2
     */
    private String pMaterial2;
    /**
     *材料3
     */
    private String pMaterial3;
    /**
     *密度
     */
    private String pDensity;
    /**
     *材料标准
     */
    private String pMaterialStandard;
    /**
     *表面处理
     */
    private String pSurfaceManage;
    /**
     *纹理编号/色彩编号
     */
    private String pTextureNo;
    /**
     *制造工艺
     */
    private String pMadeArt;
    /**
     *对称
     */
    private String pSymmetric;
    /**
     *重要度
     */
    private String pImportance;
    /**
     *是否法规件
     */
    private String pIsRulePart;
    /**
     *法规件型号
     */
    private String pRulePartNo;

    /**
     *黑白灰匣子件
     */
    private String pCasketPart;
    /**
     *开发类型
     */
    private String pDevelopType;
    /**
     *数据版本
     */
    private String pDataVersion;
    /**
     *目标重量
     */
    private String pTargetHeight;
    /**
     *预估重量
     */
    private String pEstimateHeight;
    /**
     *实际重量
     */
    private String pActualHeight;
    /**
     *紧固件
     */
    private String pFixture;
    /**
     *紧固件规格
     */
    private String pFixtureSpec;
    /**
     *紧固件性能等级
     */
    private String pFixtureLevel;
    /**
     *扭矩
     */
    private String pTorque;
    /**
     *专业部门
     */
    private String pMajorDept;
    /**
     *责任工程师
     */
    private String pDutyEngineer;
    /**
     *供应商
     */
    private String pSupplier;
    /**
     *供应商代码
     */
    private String pSupplierNo;
    /**
     *采购工程师
     */
    private String pBuyEngineer;
    /**
     *备注
     */
    private String pRemark;
    /**
     *零件分类
     */
    private String pItemClassification;
    /**
     *零部件来源
     */
    private String pItemResource;
    /**
     *供货状态(0,1 Y/N)
     */
    private String pSupplyState;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getpUnit() {
        return pUnit;
    }

    public void setpUnit(String pUnit) {
        this.pUnit = pUnit;
    }

    public String getpRentLow() {
        return pRentLow;
    }

    public void setpRentLow(String pRentLow) {
        this.pRentLow = pRentLow;
    }

    public String getpRentHigh() {
        return pRentHigh;
    }

    public void setpRentHigh(String pRentHigh) {
        this.pRentHigh = pRentHigh;
    }

    public String getpPictureNo() {
        return pPictureNo;
    }

    public void setpPictureNo(String pPictureNo) {
        this.pPictureNo = pPictureNo;
    }

    public String getpInstallPictureNo() {
        return pInstallPictureNo;
    }

    public void setpInstallPictureNo(String pInstallPictureNo) {
        this.pInstallPictureNo = pInstallPictureNo;
    }

    public String getpMap() {
        return pMap;
    }

    public void setpMap(String pMap) {
        this.pMap = pMap;
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

    public String getpSurfaceManage() {
        return pSurfaceManage;
    }

    public void setpSurfaceManage(String pSurfaceManage) {
        this.pSurfaceManage = pSurfaceManage;
    }

    public String getpTextureNo() {
        return pTextureNo;
    }

    public void setpTextureNo(String pTextureNo) {
        this.pTextureNo = pTextureNo;
    }

    public String getpMadeArt() {
        return pMadeArt;
    }

    public void setpMadeArt(String pMadeArt) {
        this.pMadeArt = pMadeArt;
    }

    public String getpSymmetric() {
        return pSymmetric;
    }

    public void setpSymmetric(String pSymmetric) {
        this.pSymmetric = pSymmetric;
    }

    public String getpImportance() {
        return pImportance;
    }

    public void setpImportance(String pImportance) {
        this.pImportance = pImportance;
    }

    public String getpIsRulePart() {
        return pIsRulePart;
    }

    public void setpIsRulePart(String pIsRulePart) {
        this.pIsRulePart = pIsRulePart;
    }

    public String getpRulePartNo() {
        return pRulePartNo;
    }

    public void setpRulePartNo(String pRulePartNo) {
        this.pRulePartNo = pRulePartNo;
    }

    public String getpCasketPart() {
        return pCasketPart;
    }

    public void setpCasketPart(String pCasketPart) {
        this.pCasketPart = pCasketPart;
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

    public String getpTargetHeight() {
        return pTargetHeight;
    }

    public void setpTargetHeight(String pTargetHeight) {
        this.pTargetHeight = pTargetHeight;
    }

    public String getpEstimateHeight() {
        return pEstimateHeight;
    }

    public void setpEstimateHeight(String pEstimateHeight) {
        this.pEstimateHeight = pEstimateHeight;
    }

    public String getpActualHeight() {
        return pActualHeight;
    }

    public void setpActualHeight(String pActualHeight) {
        this.pActualHeight = pActualHeight;
    }

    public String getpFixture() {
        return pFixture;
    }

    public void setpFixture(String pFixture) {
        this.pFixture = pFixture;
    }

    public String getpFixtureSpec() {
        return pFixtureSpec;
    }

    public void setpFixtureSpec(String pFixtureSpec) {
        this.pFixtureSpec = pFixtureSpec;
    }

    public String getpFixtureLevel() {
        return pFixtureLevel;
    }

    public void setpFixtureLevel(String pFixtureLevel) {
        this.pFixtureLevel = pFixtureLevel;
    }

    public String getpTorque() {
        return pTorque;
    }

    public void setpTorque(String pTorque) {
        this.pTorque = pTorque;
    }

    public String getpMajorDept() {
        return pMajorDept;
    }

    public void setpMajorDept(String pMajorDept) {
        this.pMajorDept = pMajorDept;
    }

    public String getpDutyEngineer() {
        return pDutyEngineer;
    }

    public void setpDutyEngineer(String pDutyEngineer) {
        this.pDutyEngineer = pDutyEngineer;
    }

    public String getpSupplier() {
        return pSupplier;
    }

    public void setpSupplier(String pSupplier) {
        this.pSupplier = pSupplier;
    }

    public String getpSupplierNo() {
        return pSupplierNo;
    }

    public void setpSupplierNo(String pSupplierNo) {
        this.pSupplierNo = pSupplierNo;
    }

    public String getpBuyEngineer() {
        return pBuyEngineer;
    }

    public void setpBuyEngineer(String pBuyEngineer) {
        this.pBuyEngineer = pBuyEngineer;
    }

    public String getpRemark() {
        return pRemark;
    }

    public void setpRemark(String pRemark) {
        this.pRemark = pRemark;
    }

    public String getpItemClassification() {
        return pItemClassification;
    }

    public void setpItemClassification(String pItemClassification) {
        this.pItemClassification = pItemClassification;
    }

    public String getpItemResource() {
        return pItemResource;
    }

    public void setpItemResource(String pItemResource) {
        this.pItemResource = pItemResource;
    }

    public String getpSupplyState() {
        return pSupplyState;
    }

    public void setpSupplyState(String pSupplyState) {
        this.pSupplyState = pSupplyState;
    }
}
