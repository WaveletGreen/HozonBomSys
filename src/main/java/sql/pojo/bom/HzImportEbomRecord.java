package sql.pojo.bom;

/**
 * @Author: haozt
 * @Date: 2018/8/23
 * @Description: excel导入EBOM专用
 */
public class HzImportEbomRecord {

    private Integer No;

    private String puid;

    private String parentId;

    private String level;

    private String lineIndex;

    /**
     * 树的高度（深度）
     */
    private int high;

    /**
     * 排序号
     */
    private int orderNum;

    private String bomDigifaxId;

    private String lineId;

    private Integer isHas;

    /**
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 是否零件
     */
    private Integer isPart;

    private String pBomOfWhichDept;


    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    private String pBomLinePartEnName;

    private String pBomLinePartResource;

    private String pFnaInfo;

    private String pFastener;

    private Integer p3cpartFlag;

    private Integer pInOutSideFlag;

    private String pUpc;

    private String pFnaDesc;

    private String pCreateName;

    private String pUpdateName;

    private String pUnit;

    private String pPictureNo;

    private String pPictureSheet;

    private String pMaterialHigh;
    /**
     * 材料1
     */
    private String pMaterial1;
    /**
     * 材料2
     */
    private String pMaterial2;
    /**
     * 材料3
     */
    private String pMaterial3;
    /**
     * 密度
     */
    private String pDensity;
    /**
     * 材料标准
     */
    private String pMaterialStandard;
    /**
     * 表面处理
     */
    private String pSurfaceTreat;
    /**
     * 纹理编号
     */
    private String pTextureColorNum;

    /**
     * 制造工艺
     */
    private String pManuProcess;
    /**
     *  对称
     */
    private String pSymmetry;
    /**
     * 重要度
     */
    private String pImportance;
    /**
     * 法规件标识
     */
    private Integer pRegulationFlag;
    /**
     * 黑白匣子类型
     */
    private String pBwgBoxPart;
    /**
     * 开发类型
     */
    private String pDevelopType;
    /**
     * 数据版本
     */
    private String pDataVersion;
    /**
     * 实际重量
     */
    private String pTargetWeight;
    /**
     * 预估重量
     */
    private String pFeatureWeight;
    /**
     * 实际重量
     */
    private String pActualWeight;
    /**
     * 紧固件标准
     */
    private String pFastenerStandard;
    /**
     * 紧固件等级
     */
    private String pFastenerLevel;
    /**
     * 转矩
     */
    private String pTorque;
    /**
     * 责任工程师
     */
    private String pDutyEngineer;
    /**
     * 供应商
     */
    private String pSupply;
    /**
     * 供应商代码
     */
    private String pSupplyCode;
    /**
     * 备注
     */
    private String pRemark;
    /**
     * 法规件代码
     */
    private String pRegulationCode;

    /**
     * 数量
     */
    private Integer number;
    /**
     * 采购工程师
     */
    private String pBuyEngineer;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public Integer getIs2Y() {
        return is2Y;
    }

    public void setIs2Y(Integer is2Y) {
        this.is2Y = is2Y;
    }

    public Integer getIsPart() {
        return isPart;
    }

    public void setIsPart(Integer isPart) {
        this.isPart = isPart;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }


    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }

    public String getpFnaInfo() {
        return pFnaInfo;
    }

    public void setpFnaInfo(String pFnaInfo) {
        this.pFnaInfo = pFnaInfo;
    }

    public String getpFastener() {
        return pFastener;
    }

    public void setpFastener(String pFastener) {
        this.pFastener = pFastener;
    }

    public Integer getP3cpartFlag() {
        return p3cpartFlag;
    }

    public void setP3cpartFlag(Integer p3cpartFlag) {
        this.p3cpartFlag = p3cpartFlag;
    }

    public Integer getpInOutSideFlag() {
        return pInOutSideFlag;
    }

    public void setpInOutSideFlag(Integer pInOutSideFlag) {
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

    public Integer getpRegulationFlag() {
        return pRegulationFlag;
    }

    public void setpRegulationFlag(Integer pRegulationFlag) {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getpBuyEngineer() {
        return pBuyEngineer;
    }

    public void setpBuyEngineer(String pBuyEngineer) {
        this.pBuyEngineer = pBuyEngineer;
    }
}
