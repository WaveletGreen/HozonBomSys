package com.connor.hozon.bom.resources.dto.response;

/**
 * Created by haozt on 2018/5/24
 */
public class HzMbomRecordRespDTO {
    /**
     * id
     */
    private String puid;

    /**
     * 层级
     */
    private String level;
    /**
     * 专业
     */
    private String pBomOfWhichDept;
    /**
     * 零件号
     */
    private String lineId;
    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;

    /**
     * 英文名称
     */
    private String pBomLinePartEnName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * bom排列序号
     */
    private Integer No;

    /**
     * 外键id
     */
    private String eBomPuid;
    /**
     * 备件
     */
    private String sparePart;
    /**
     *备件编号
     */
    private String sparePartNum;
    /**
     * 工艺路线
     */
    private String processRoute;
    /**
     * 人工工时
     */
    private String laborHour;
    /**
     *节拍
     */
    private String rhythm;
    /**
     * 焊点
     */
    private String solderJoint;
    /**
     * 机物料
     */
    private String machineMaterial;
    /**
     * 标准件
     */
    private String standardPart;
    /**
     * 工具
     */
    private String tools;
    /**
     * 废品
     */
    private String wasterProduct;
    /**
     * 变更
     */
    private String change;
    /**
     * 变更编号
     */
    private String changeNum;

    private String object_name;

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String geteBomPuid() {
        return eBomPuid;
    }

    public void seteBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }

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

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

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

    public String getProcessRoute() {
        return processRoute;
    }

    public void setProcessRoute(String processRoute) {
        this.processRoute = processRoute;
    }

    public String getLaborHour() {
        return laborHour;
    }

    public void setLaborHour(String laborHour) {
        this.laborHour = laborHour;
    }

    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm;
    }

    public String getSolderJoint() {
        return solderJoint;
    }

    public void setSolderJoint(String solderJoint) {
        this.solderJoint = solderJoint;
    }

    public String getMachineMaterial() {
        return machineMaterial;
    }

    public void setMachineMaterial(String machineMaterial) {
        this.machineMaterial = machineMaterial;
    }

    public String getStandardPart() {
        return standardPart;
    }

    public void setStandardPart(String standardPart) {
        this.standardPart = standardPart;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getWasterProduct() {
        return wasterProduct;
    }

    public void setWasterProduct(String wasterProduct) {
        this.wasterProduct = wasterProduct;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }


}
