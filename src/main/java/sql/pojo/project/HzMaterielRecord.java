package sql.pojo.project;

import java.util.Date;

public class HzMaterielRecord {
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
     * 工厂 已废弃字段 工厂信息需要从工厂表来读取
     */
    @Deprecated
    private String pMaterielWerk;
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
    private Integer pInventedPart;
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
    private Integer pColorPart;
    /**
     * 毛重
     */
    private String pHeight;
    /**
     * 内外饰标识
     */
    private Integer pInOutSideFlag;
    /**
     * 3C件标识
     */
    private Integer p3cPartFlag;
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
    private Integer pLoosePartFlag;
    /**
     * 物料类型  严格按照注释来读写数据
     * （11 整车超级物料主数据
     * 21 整车衍生物料主数据
     * 31 虚拟件物料主数据
     * 41半成品物料主数据
     * 51 生产性外购物料主数据
     * 61自制备件物料主数据）
     */
    private Integer pMaterielDataType;

    /**
     * 创建者
     */
    private String pCreateName;
    /**
     * 更新者
     */
    private String pUpdateName;

    private Date pCreateTime;

    private Date pUpdateTime;
    /**
     * 工厂id
     */
    private String pFactoryPuid;
    /**
     * 采购类型
     */
    private String resource;

    private Integer pValidFlag;

    /**
     * 类型（1 半成品工艺路线 2整车工艺路线  3总成分总成工艺路线  ）
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getpValidFlag() {
        return pValidFlag;
    }

    public void setpValidFlag(Integer pValidFlag) {
        this.pValidFlag = pValidFlag;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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

    public String getpMaterielWerk() {
        return pMaterielWerk;
    }

    public void setpMaterielWerk(String pMaterielWerk) {
        this.pMaterielWerk = pMaterielWerk;
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

    public Integer getpInventedPart() {
        return pInventedPart;
    }

    public void setpInventedPart(Integer pInventedPart) {
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

    public Integer getpColorPart() {
        return pColorPart;
    }

    public void setpColorPart(Integer pColorPart) {
        this.pColorPart = pColorPart;
    }

    public String getpHeight() {
        return pHeight;
    }

    public void setpHeight(String pHeight) {
        this.pHeight = pHeight;
    }

    public Integer getpInOutSideFlag() {
        return pInOutSideFlag;
    }

    public void setpInOutSideFlag(Integer pInOutSideFlag) {
        this.pInOutSideFlag = pInOutSideFlag;
    }

    public Integer getP3cPartFlag() {
        return p3cPartFlag;
    }

    public void setP3cPartFlag(Integer p3cPartFlag) {
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

    public Integer getpLoosePartFlag() {
        return pLoosePartFlag;
    }

    public void setpLoosePartFlag(Integer pLoosePartFlag) {
        this.pLoosePartFlag = pLoosePartFlag;
    }

    public Integer getpMaterielDataType() {
        return pMaterielDataType;
    }

    public void setpMaterielDataType(Integer pMaterielDataType) {
        this.pMaterielDataType = pMaterielDataType;
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

    public String getpFactoryPuid() {
        return pFactoryPuid;
    }

    public void setpFactoryPuid(String pFactoryPuid) {
        this.pFactoryPuid = pFactoryPuid;
    }
}