package com.connor.hozon.bom.resources.dto.response;

/**
 * Created by haozt on 2018/06/05
 * EPL管理全字段
 */
public class HzEPLRecordRespDTO {
    /* EBOM*/
    /**
     * bom表的puid
     */
    private String puid;

    private Integer No;
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
     * 状态值 0 1 2->A U D
     */
    private String pState;
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
     *是否供货级(0,1 Y/N)
     */
    private String pSupplyState;

    /**PBOM*/
    /**
     * 来源（自制/采购）
     */
    private String resource;
    /**
     * 类型 是否为焊接/装配（0是  1不是 2不明确）
     */
    private String type;
    /**
     * 是否采购单元 （0是  1不是 2不明确）
     */
    private String buyUnit;
    /**
     *车间1
     */
    private String workShop1;
    /**
     *车间2
     */
    private String workShop2;
    /**
     *生产线
     */
    private String productLine;
    /**
     * 模具类型
     */
    private String mouldType;
    /**
     * 外委件
     */
    private String outerPart;
    /**
     * 颜色件
     */
    private String colorPart;

    /**
     * 工位
     */
    private String station;

    /**MBOM*/

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

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public void setpSupplyState(String pSupplyState) {
        this.pSupplyState = pSupplyState;
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

    public String getpSupplyState() {
        return pSupplyState;
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



    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuyUnit() {
        return buyUnit;
    }

    public void setBuyUnit(String buyUnit) {
        this.buyUnit = buyUnit;
    }

    public String getWorkShop1() {
        return workShop1;
    }

    public void setWorkShop1(String workShop1) {
        this.workShop1 = workShop1;
    }

    public String getWorkShop2() {
        return workShop2;
    }

    public void setWorkShop2(String workShop2) {
        this.workShop2 = workShop2;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getMouldType() {
        return mouldType;
    }

    public void setMouldType(String mouldType) {
        this.mouldType = mouldType;
    }

    public String getOuterPart() {
        return outerPart;
    }

    public void setOuterPart(String outerPart) {
        this.outerPart = outerPart;
    }

    public String getColorPart() {
        return colorPart;
    }

    public void setColorPart(String colorPart) {
        this.colorPart = colorPart;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public String getProcessRoute() {
        return processRoute;
    }

    public void setProcessRoute(String processRoute) {
        this.processRoute = processRoute;
    }

    public String getLaborHour() {
        return laborHour;
    }

    public void setLaborHour(String laborHour) {
        this.laborHour = laborHour;
    }

    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    public String getSolderJoint() {
        return solderJoint;
    }

    public void setSolderJoint(String solderJoint) {
        this.solderJoint = solderJoint;
    }

    public String getMachineMaterial() {
        return machineMaterial;
    }

    public void setMachineMaterial(String machineMaterial) {
        this.machineMaterial = machineMaterial;
    }

    public String getStandardPart() {
        return standardPart;
    }

    public void setStandardPart(String standardPart) {
        this.standardPart = standardPart;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getWasterProduct() {
        return wasterProduct;
    }

    public void setWasterProduct(String wasterProduct) {
        this.wasterProduct = wasterProduct;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }
}
