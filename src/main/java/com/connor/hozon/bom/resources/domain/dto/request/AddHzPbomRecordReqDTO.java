package com.connor.hozon.bom.resources.domain.dto.request;

/**
 * Created by haozt on 2018/5/29
 */
public class AddHzPbomRecordReqDTO {
    /**
     * 合成零件的puid 中间用英文逗号隔开
     */
    private String puids;
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
     * 外键id
     */
    private String eBomPuid;

    /**
     * 工位
     */
    private  String station;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 是否为父层 工艺合件参数
     */
    private Integer isParent;

    /**
     * 专业 工艺合件参数
     */
    private String pBomOfWhichDept;

    /**
     * 零件来源 工艺合件参数
     */
    private String pBomLinePartResource;
    /**
     * 零件分类 工艺合件参数
     */
    private String pBomLinePartClass;
    /**
     * Bom行对应的零件名 工艺合件参数
     */
    private String pBomLinePartName;

    /**
     * 英文名称 工艺合件参数
     */
    private String pBomLinePartEnName;

    /**
     * 零件号 工艺合件参数
     */
    private String lineId;

    public String getPuids() {
        return puids;
    }

    public void setPuids(String puids) {
        this.puids = puids;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
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

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public String geteBomPuid() {
        return eBomPuid;
    }

    public void seteBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }
}
