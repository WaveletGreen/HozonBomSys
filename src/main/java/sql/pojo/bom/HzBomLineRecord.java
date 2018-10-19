package sql.pojo.bom;

import sql.pojo.BasePOJO;

import java.util.Date;
import java.util.Objects;

public class HzBomLineRecord extends BasePOJO {

    private Long id;
    /**
     * puid
     */
    private String puid;
    /**
     * 父层puid
     */
    private String parentUid;
    /**
     * 是否部门层
     */
    private Integer isDept;
    /**
     * 数模层PUID
     */
    private String bomDigifaxId;
    /**
     * Bom行的层级索引
     */
    private String lineIndex;
    /**
     * 记录TC的BOMLine的Puid，用于快速session.stringToTCObject
     */
    private String linePuid;
    /**
     * Bom行对应的零件号
     */
    private String lineID;
    /**
     * 是否有子层
     */
    private Integer isHas;
    /**
     * 属性集合，是一个LinkedHashMap，需要转换
     */
    private byte[] bomLineBlock;
    /**
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 是否零件
     */
    private Integer isPart;
    /**
     * 在Bom结构中的顺序号
     */
    @Deprecated
    private Integer orderNum;
    /**
     * 2Y层归属哪个部门
     */
    private String pBomOfWhichDept;
    /**
     * 项目的puid，只是方便根据项目查找数据，并不存在数据库中，做项目映射应该使用bomDigifaxId字段对应数模层，再对应项目
     */
    private String projectPuid;
    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;
    /**
     * 状态
     */
    private Integer status;

    private String pBomLinePartEnName;

    private String pBomLinePartResource;


    private String pFnaInfo;

    private Date pCreateTime;

    private Date pUpdateTime;

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

    private String pManuProcess;
    /**
     *
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
     * 项目UID
     */
    private String pProjectPuid;
    /**
     * 数量
     */
    private String number;
    /**
     * 采购工程师
     */
    private String pBuyEngineer;
    /**
     * 针对二级配色方案对应的颜色值的UID
     */
    private String colorUid;
    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * ewo编号
     */
    private String ewoNo;

    private String sortNum;

    private Integer pLouaFlag;

    /**
     * 颜色件 原PBOM移动到EBOM
     */
    private Integer colorPart;

    /**
     * 单车用量
     */
    private byte[] singleVehDosage;

    public byte[] getSingleVehDosage() {
        return singleVehDosage;
    }

    public void setSingleVehDosage(byte[] singleVehDosage) {
        this.singleVehDosage = singleVehDosage;
    }

    public Integer getColorPart() {
        return colorPart;
    }

    public void setColorPart(Integer colorPart) {
        this.colorPart = colorPart;
    }

    /**
     * 根据puid来进行判断重复 重写equals方法
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HzBomLineRecord record = (HzBomLineRecord) o;
        return Objects.equals(puid, record.puid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(puid);
    }


    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getpLouaFlag() {
        return pLouaFlag;
    }

    public void setpLouaFlag(Integer pLouaFlag) {
        this.pLouaFlag = pLouaFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getpBuyEngineer() {
        return pBuyEngineer;
    }

    public void setpBuyEngineer(String pBuyEngineer) {
        this.pBuyEngineer = pBuyEngineer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getpFnaInfo() {
        return pFnaInfo;
    }

    public void setpFnaInfo(String pFnaInfo) {
        this.pFnaInfo = pFnaInfo;
    }

    public Date getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(Date pCreateTime) {
        this.pCreateTime = pCreateTime;
    }

    public Date getpUpdateTime() {
        return pUpdateTime;
    }

    public void setpUpdateTime(Date pUpdateTime) {
        this.pUpdateTime = pUpdateTime;
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

    public String getpProjectPuid() {
        return pProjectPuid;
    }

    public void setpProjectPuid(String pProjectPuid) {
        this.pProjectPuid = pProjectPuid;
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

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid == null ? null : parentUid.trim();
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId == null ? null : bomDigifaxId.trim();
    }

    public byte[] getBomLineBlock() {
        return bomLineBlock;
    }

    public void setBomLineBlock(byte[] bomLineBlock) {
        this.bomLineBlock = bomLineBlock;
    }

    public String getIndex() {
        return lineIndex;
    }

    public void setIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public void setHasChildren(boolean isHas) {
        this.setIsHas(isHas ? 1 : 0);
    }

    public String getLinePuid() {
        return linePuid;
    }

    public void setLinePuid(String linePuid) {
        this.linePuid = linePuid;
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public Integer getIs2Y() {
        return is2Y;
    }

    public void setIs2Y(Integer is2y) {
        is2Y = is2y;
    }

    public Integer getIsPart() {
        return isPart;
    }

    public void setIsPart(Integer isPart) {
        this.isPart = isPart;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public Integer getIsDept() {
        return isDept;
    }

    public void setIsDept(Integer isDept) {
        this.isDept = isDept;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getColorUid() {
        return colorUid;
    }

    public void setColorUid(String colorUid) {
        this.colorUid = colorUid;
    }
}