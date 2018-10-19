package sql.pojo.cfg.model;

/**
 * 全配置BOM一级清单中用到的基本车型模型
 */
public class HzCfg0ModelDetail {
    /***
     * 主键
     */
    private String puid;
    /**
     * 车型模型的puid
     */
    private String pModelPuid;
    /***
     * 车型模型名
     */
    private String pModelName;
    /***
     * 车型模型描述
     */
    private String pModelDesc;
    /**
     * 品牌
     */
    private String pModelBrand;
    /**
     * 车型
     */
    @Deprecated
    private String pModelVehicle;
    /**
     * 平台
     */
    private String pModelPlatform;

    /**
     * 车身形式
     */
    private String pModelShape;
    /**
     * 公告
     */
    private String pModelAnnouncement;
    /**
     * 配置描述
     */
    private String pModelCfgDesc;
    /**
     * 配置管理
     */
    private String pModelCfgMng;
    /**
     * 销售地区
     */
    @Deprecated
    private String pModelSaleArea;
    /**
     * 车型
     */
    private String pModelMod;
    /**
     * 年型
     */
    @Deprecated
    private String pModelAnnual;
    /**
     * 版型
     */
    @Deprecated
    private String pModelVersion;
    /**
     * 老司机位置
     */
    @Deprecated
    private String pModelDriverPosition;
    /**
     * 成员数
     */
    @Deprecated
    private String pModelMembers;
    /**
     * 动总
     */
    @Deprecated
    private String pModelPowers;
    /**
     * 配置版本
     */
    @Deprecated
    private String pModelCfgVersion;
    /**
     * 试制号
     */
    private String pModelTrailNum;
    /**
     * 商品号
     */
    private String pModelGoodsNum;
    /**
     * 变形
     */
    @Deprecated
    private String pModelTransform;
    /**
     * 模型名
     */
    private String objectName;
    /**
     * 模型描述
     */
    private String objectDesc;
    /**
     * 模型基本信息
     */
    private String pCfg0ModelBasicDetail;
    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpModelPuid() {
        return pModelPuid;
    }

    public void setpModelPuid(String pModelPuid) {
        this.pModelPuid = pModelPuid == null ? null : pModelPuid.trim();
    }

    public String getpModelName() {
        return pModelName;
    }

    public void setpModelName(String pModelName) {
        this.pModelName = pModelName == null ? null : pModelName.trim();
    }

    public String getpModelDesc() {
        return pModelDesc;
    }

    public void setpModelDesc(String pModelDesc) {
        this.pModelDesc = pModelDesc == null ? null : pModelDesc.trim();
    }

    public String getpModelSaleArea() {
        return pModelSaleArea;
    }

    public void setpModelSaleArea(String pModelSaleArea) {
        this.pModelSaleArea = pModelSaleArea == null ? null : pModelSaleArea.trim();
    }

    public String getpModelBrand() {
        return pModelBrand;
    }

    public void setpModelBrand(String pModelBrand) {
        this.pModelBrand = pModelBrand == null ? null : pModelBrand.trim();
    }

    public String getpModelVehicle() {
        return pModelVehicle;
    }

    public void setpModelVehicle(String pModelVehicle) {
        this.pModelVehicle = pModelVehicle == null ? null : pModelVehicle.trim();
    }

    public String getpModelPlatform() {
        return pModelPlatform;
    }

    public void setpModelPlatform(String pModelPlatform) {
        this.pModelPlatform = pModelPlatform == null ? null : pModelPlatform.trim();
    }

    public String getpModelMod() {
        return pModelMod;
    }

    public void setpModelMod(String pModelMod) {
        this.pModelMod = pModelMod == null ? null : pModelMod.trim();
    }

    public String getpModelAnnual() {
        return pModelAnnual;
    }

    public void setpModelAnnual(String pModelAnnual) {
        this.pModelAnnual = pModelAnnual == null ? null : pModelAnnual.trim();
    }

    public String getpModelVersion() {
        return pModelVersion;
    }

    public void setpModelVersion(String pModelVersion) {
        this.pModelVersion = pModelVersion == null ? null : pModelVersion.trim();
    }

    public String getpModelDriverPosition() {
        return pModelDriverPosition;
    }

    public void setpModelDriverPosition(String pModelDriverPosition) {
        this.pModelDriverPosition = pModelDriverPosition == null ? null : pModelDriverPosition.trim();
    }

    public String getpModelMembers() {
        return pModelMembers;
    }

    public void setpModelMembers(String pModelMembers) {
        this.pModelMembers = pModelMembers == null ? null : pModelMembers.trim();
    }

    public String getpModelShape() {
        return pModelShape;
    }

    public void setpModelShape(String pModelShape) {
        this.pModelShape = pModelShape == null ? null : pModelShape.trim();
    }

    public String getpModelAnnouncement() {
        return pModelAnnouncement;
    }

    public void setpModelAnnouncement(String pModelAnnouncement) {
        this.pModelAnnouncement = pModelAnnouncement == null ? null : pModelAnnouncement.trim();
    }

    public String getpModelPowers() {
        return pModelPowers;
    }

    public void setpModelPowers(String pModelPowers) {
        this.pModelPowers = pModelPowers == null ? null : pModelPowers.trim();
    }

    public String getpModelCfgVersion() {
        return pModelCfgVersion;
    }

    public void setpModelCfgVersion(String pModelCfgVersion) {
        this.pModelCfgVersion = pModelCfgVersion == null ? null : pModelCfgVersion.trim();
    }

    public String getpModelCfgDesc() {
        return pModelCfgDesc;
    }

    public void setpModelCfgDesc(String pModelCfgDesc) {
        this.pModelCfgDesc = pModelCfgDesc == null ? null : pModelCfgDesc.trim();
    }

    public String getpModelTrailNum() {
        return pModelTrailNum;
    }

    public void setpModelTrailNum(String pModelTrailNum) {
        this.pModelTrailNum = pModelTrailNum == null ? null : pModelTrailNum.trim();
    }

    public String getpModelGoodsNum() {
        return pModelGoodsNum;
    }

    public void setpModelGoodsNum(String pModelGoodsNum) {
        this.pModelGoodsNum = pModelGoodsNum == null ? null : pModelGoodsNum.trim();
    }

    public String getpModelTransform() {
        return pModelTransform;
    }

    public void setpModelTransform(String pModelTransform) {
        this.pModelTransform = pModelTransform;
    }

    public String getpModelCfgMng() {
        return pModelCfgMng;
    }

    public void setpModelCfgMng(String pModelCfgMng) {
        this.pModelCfgMng = pModelCfgMng;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectDesc() {
        return objectDesc;
    }

    public void setObjectDesc(String objectDesc) {
        this.objectDesc = objectDesc;
    }

    public String getpCfg0ModelBasicDetail() {
        return pCfg0ModelBasicDetail;
    }

    public void setpCfg0ModelBasicDetail(String pCfg0ModelBasicDetail) {
        this.pCfg0ModelBasicDetail = pCfg0ModelBasicDetail;
    }
}