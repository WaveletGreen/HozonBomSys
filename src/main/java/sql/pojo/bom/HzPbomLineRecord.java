package sql.pojo.bom;

/**
 * Created by haozt on 2018/5/25
 * EBOM 和PBOM 两张表信息  继承自EBOM  维护PBOM
 */
public class HzPbomLineRecord {
    /**
     * EBOM 表主键
     */
    private String pPuid;
    private String parentUid;
    private String isDept;
    private String bomDigifaxId;
    private String linePuid;
    private byte[] bomLineBlock;
    private Integer isPart;
    private Integer orderNum;
    private String projectPuid;
    private String pBomLinePartName;
    private String pBomLinePartClass;

    /**
     * BOM行编号 根据这个来计算层级
     */
    private String lineIndex;
    /**
     * 零件号
     */
    private String lineId;
    /**
     * 是否有下一层
     */
    private Integer isHas;
    /**
     * 是否为2Y层
     */
    private Integer is2Y;
    /**
     * 专业
     */
    private String pBomOfWhichDept;

    /**
     *主键id
     */
    private String puid;
    /**
     *零件来源（自制总成/采购拆分等）
     */
    private String itemResource;
    /**
     * 来源（自制/采购）
     */
    private String resource;
    /**
     * 类型 是否为焊接/装配（0是  1不是 2不明确）
     */
    private Integer type;
    /**
     * 是否采购单元 （0是  1不是 2不明确）
     */
    private Integer buyUnit;
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
    private Integer colorPart;

    public String getpPuid() {
        return pPuid;
    }

    public void setpPuid(String pPuid) {
        this.pPuid = pPuid;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId= lineId;
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

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getItemResource() {
        return itemResource;
    }

    public void setItemResource(String itemResource) {
        this.itemResource = itemResource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBuyUnit() {
        return buyUnit;
    }

    public void setBuyUnit(Integer buyUnit) {
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

    public Integer getColorPart() {
        return colorPart;
    }

    public void setColorPart(Integer colorPart) {
        this.colorPart = colorPart;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getIsDept() {
        return isDept;
    }

    public void setIsDept(String isDept) {
        this.isDept = isDept;
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId;
    }

    public String getLinePuid() {
        return linePuid;
    }

    public void setLinePuid(String linePuid) {
        this.linePuid = linePuid;
    }


    public byte[] getBomLineBlock() {
        return bomLineBlock;
    }

    public void setBomLineBlock(byte[] bomLineBlock) {
        this.bomLineBlock = bomLineBlock;
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

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
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
}
