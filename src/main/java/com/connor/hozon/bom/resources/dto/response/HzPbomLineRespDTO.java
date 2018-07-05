package com.connor.hozon.bom.resources.dto.response;

/**
 * Created by haozt on 2018/5/25
 * PBOM出参
 */
public class HzPbomLineRespDTO {

    private int No;//1

    /**
     * 级别 以2Y层为相对 第1级
     */
    private String rank;//1

    /**
     * 层级
     */
    private String level;//1
    /**
     * 零件号
     */
    private String lineId;//1

    /**
     * 专业
     */
    private String pBomOfWhichDept;//1

    /**
     * 分组号
     */
    private String groupNum;//1

    /**
     * 零件分类
     */
    private Object  h9_IsCommon;//1

   private String pBomLinePartClass;

    /**
     *PBOM主键id
     */
    private String puid;
    /**
     *零件来源（自制总成/采购拆分等）
     */
    private Object H9_Mat_Status;//1
    /**
     *零件来源（自制总成/采购拆分等）
     */
    private  String pBomLinePartResource;
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
     * 外键id 对应EBOM表的id
     */
    private String eBomPuid;//1
    /**
     * 工位
     */
    private String station;

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }


    public Object getH9_IsCommon() {
        return h9_IsCommon;
    }

    public void setH9_IsCommon(Object h9_IsCommon) {
        this.h9_IsCommon = h9_IsCommon;
    }

    public Object getH9_Mat_Status() {
        return H9_Mat_Status;
    }

    public void setH9_Mat_Status(Object h9_Mat_Status) {
        H9_Mat_Status = h9_Mat_Status;
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

    public void setColorPart(String colorPart) {
        this.colorPart = colorPart;
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

    public String geteBomPuid() {
        return eBomPuid;
    }

    public void seteBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }

    public String getColorPart() {
        return colorPart;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }
}
