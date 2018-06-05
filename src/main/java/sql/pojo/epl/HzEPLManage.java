package sql.pojo.epl;

/**
 * Created by haozt on 2018/06/04
 */
public class HzEPLManage {
    private String pPuid;
    /**
     * 状态值 0 1 2->A U D
     */
    private Integer pState;
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
     * 外键
     */
    private String pBomPuid;

    public String getpPuid() {
        return pPuid;
    }

    public void setpPuid(String pPuid) {
        this.pPuid = pPuid;
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

    public String getpBomPuid() {
        return pBomPuid;
    }

    public void setpBomPuid(String pBomPuid) {
        this.pBomPuid = pBomPuid;
    }

    public Integer getpState() {
        return pState;
    }

    public void setpState(Integer pState) {
        this.pState = pState;
    }
}
