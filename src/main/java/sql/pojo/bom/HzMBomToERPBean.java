package sql.pojo.bom;

public class HzMBomToERPBean {
    /**
     * 主键
     */
    private String puid;
    /**
     * 父项PUID
     */
    private String parentUID;
    /**
     * 原始的BOMLine PUID
     */
    private String bomUid;
    /**
     * 变更号
     */
    private String changeNum;
    /**
     * 变更描述
     */
    private String change;
    /**
     * 工厂代号
     */
    private String factoryCode;
    /**
     * BOM类型
     */
    private String bomType;
    /**
     * item_ID，当物料编号
     */
    private String bomLineId;
    /**
     * BOM顺序号
     */
    private String bomOrderNum;
    /**
     * 库存地点
     */
    private String stockLocation;
    /**
     * 相关性
     */
    private String cfg0Relevance;
    /**
     * FNA，装配位置
     */
    private String FNAInfo;
    /**
     * 车间1，作为使用车间
     */
    private String workShop;
    /**
     * 工位
     */
    private String station;
    /**
     * 大对象
     */
    private byte[] bomLineBlock;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getParentUID() {
        return parentUID;
    }

    public void setParentUID(String parentUID) {
        this.parentUID = parentUID;
    }

    public String getBomUid() {
        return bomUid;
    }

    public void setBomUid(String bomUid) {
        this.bomUid = bomUid;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getBomType() {
        return bomType;
    }

    public void setBomType(String bomType) {
        this.bomType = bomType;
    }

    public String getBomLineId() {
        return bomLineId;
    }

    public void setBomLineId(String bomLineId) {
        this.bomLineId = bomLineId;
    }

    public String getBomOrderNum() {
        return bomOrderNum;
    }

    public void setBomOrderNum(String bomOrderNum) {
        this.bomOrderNum = bomOrderNum;
    }

    public String getStockLocation() {
        return stockLocation;
    }

    public void setStockLocation(String stockLocation) {
        this.stockLocation = stockLocation;
    }

    public String getCfg0Relevance() {
        return cfg0Relevance;
    }

    public void setCfg0Relevance(String cfg0Relevance) {
        this.cfg0Relevance = cfg0Relevance;
    }

    public String getFNAInfo() {
        return FNAInfo;
    }

    public void setFNAInfo(String FNAInfo) {
        this.FNAInfo = FNAInfo;
    }

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public byte[] getBomLineBlock() {
        return bomLineBlock;
    }

    public void setBomLineBlock(byte[] bomLineBlock) {
        this.bomLineBlock = bomLineBlock;
    }
}
