package sql.pojo.accessories;

import io.swagger.models.auth.In;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description: 废除 新工艺辅料库为 {@link HzAccessoriesLibs}
 */
@Deprecated
public class HzAccessoriesLib {

    /**
     * puid
     */
    private String puid;
    /**
     *零件名称
     */
    private String pBomLinePartName;
    /**
     *零件英文名称
     */
    private String pBomLinePartEnName;
    /**
     *创建时间
     */
    private Date pCreateTime;
    /**
     *更改时间
     */
    private Date pUpdateTime;
    /**
     *零件号
     */
    private String pLineId;
    /**
     *单位
     */
    private String pUnit;
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
    private String pSurfaceTreat;
    /**
     *纹理编号/色彩编号
     */
    private String pTextureColorNum;
    /**
     *制造工艺
     */
    private String pManuProcess;
    /**
     *目标重量
     */
    private String pTargetWeight;
    /**
     *预估重量
     */
    private String pFutureWeight;
    /**
     *实际重量
     */
    private String pActualWeight;
    /**
     *责任工程师
     */
    private String pDutyEngineer;
    /**
     *供应商
     */
    private String pSupply;
    /**
     *供应商代码
     */
    private String pSupplyCode;
    /**
     *备注
     */
    private String pRemark;
    /**
     *创建者
     */
    private String pCreateName;
    /**
     *更改者
     */
    private String pUpdateName;

    private String pStatus;

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
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

    public String getpLineId() {
        return pLineId;
    }

    public void setpLineId(String pLineId) {
        this.pLineId = pLineId;
    }

    public String getpUnit() {
        return pUnit;
    }

    public void setpUnit(String pUnit) {
        this.pUnit = pUnit;
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

    public String getpTargetWeight() {
        return pTargetWeight;
    }

    public void setpTargetWeight(String pTargetWeight) {
        this.pTargetWeight = pTargetWeight;
    }

    public String getpFutureWeight() {
        return pFutureWeight;
    }

    public void setpFutureWeight(String pFutureWeight) {
        this.pFutureWeight = pFutureWeight;
    }

    public String getpActualWeight() {
        return pActualWeight;
    }

    public void setpActualWeight(String pActualWeight) {
        this.pActualWeight = pActualWeight;
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
}
