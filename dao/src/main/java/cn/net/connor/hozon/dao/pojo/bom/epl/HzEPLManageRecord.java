package cn.net.connor.hozon.dao.pojo.bom.epl;

import org.apache.commons.lang.StringUtils;
import cn.net.connor.hozon.dao.pojo.BaseChangeDO;

import java.util.Date;
import java.util.Objects;

/**
 * Created by haozt on 2018/06/05
 * EPL管理全字段 E+P+M BOM 字段统称
 * 后期需求做过变更 EPL字段又改为零部件数据字段
 * 为了与之前代码做兼容性 EBOM 的字段取值未做改动
 * 依然使用这里的字段
 */
public class HzEPLManageRecord extends BaseChangeDO {

    private static final long serialVersionUID = 8252971773184175221L;
    /**
     * EPL 表主键
     */
    private Long eplId;
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
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 是否零件
     */
    private Integer isPart;
    /**
     * 2Y层归属哪个部门
     */
    private String pBomOfWhichDept;
    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    /**
     * bom 状态
     */
    private Integer status;


    /**
     * 颜色件
     */
    private Integer colorPart;

    /**
     * FNA信息
     */
    private String pFnaInfo;

    private String fna;

    private Integer maxLineIndexFirstNum;

    private String pBomLinePartEnName;

    private String pBomLinePartResource;

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

    private Integer pRegulationFlag;

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

    private Integer pLouaFlag;

    private String sortNum;

    /**
     * 项目
     */
    private String projectId;
    /**
     * 单车用量
     */
    private String vehNum;

    /**
     * 备件
     */
    private String sparePart;
    /**
     * 备件编号
     */
    private String sparePartNum;


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

    public String getVehNum() {
        return vehNum;
    }

    public void setVehNum(String vehNum) {
        this.vehNum = vehNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if(o instanceof HzEPLManageRecord){
            final HzEPLManageRecord that = (HzEPLManageRecord) o;
            if(StringUtils.isNotEmpty(this.puid)){
                return this.puid.equals(that.getPuid());
            }
            return false;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(puid);
    }

    public Long getEplId() {
        return eplId;
    }

    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public Integer getIsDept() {
        return isDept;
    }

    public void setIsDept(Integer isDept) {
        this.isDept = isDept;
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getColorPart() {
        return colorPart;
    }

    public void setColorPart(Integer colorPart) {
        this.colorPart = colorPart;
    }

    public String getpFnaInfo() {
        return pFnaInfo;
    }

    public void setpFnaInfo(String pFnaInfo) {
        this.pFnaInfo = pFnaInfo;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public Integer getMaxLineIndexFirstNum() {
        return maxLineIndexFirstNum;
    }

    public void setMaxLineIndexFirstNum(Integer maxLineIndexFirstNum) {
        this.maxLineIndexFirstNum = maxLineIndexFirstNum;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getpBuyEngineer() {
        return pBuyEngineer;
    }

    public void setpBuyEngineer(String pBuyEngineer) {
        this.pBuyEngineer = pBuyEngineer;
    }

    public Integer getpLouaFlag() {
        return pLouaFlag;
    }

    public void setpLouaFlag(Integer pLouaFlag) {
        this.pLouaFlag = pLouaFlag;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
