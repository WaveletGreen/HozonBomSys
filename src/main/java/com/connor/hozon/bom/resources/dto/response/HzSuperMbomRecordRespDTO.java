package com.connor.hozon.bom.resources.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/6/28
 * @Description:
 */
public class HzSuperMbomRecordRespDTO {
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
     * 名称
     */
    private String object_name;

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

    /**
     * 配置描述
     */
    private String cfg0Desc;
    /**
     * 选项族的名称
     */
    private String cfg0FamilyName;
    /**
     * 选项族的描述
     */
    private String cfg0FamilyDesc;
    /**
     * 车型名称
     */
    private String objectName;
    /**
     * 车型描述
     */
    private String objectDesc;
    /**
     * 基本信息
     */
    private String cfg0ModelBasicDetail;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
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

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
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

    public String getCfg0Desc() {
        return cfg0Desc;
    }

    public void setCfg0Desc(String cfg0Desc) {
        this.cfg0Desc = cfg0Desc;
    }

    public String getCfg0FamilyName() {
        return cfg0FamilyName;
    }

    public void setCfg0FamilyName(String cfg0FamilyName) {
        this.cfg0FamilyName = cfg0FamilyName;
    }

    public String getCfg0FamilyDesc() {
        return cfg0FamilyDesc;
    }

    public void setCfg0FamilyDesc(String cfg0FamilyDesc) {
        this.cfg0FamilyDesc = cfg0FamilyDesc;
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

    public String getCfg0ModelBasicDetail() {
        return cfg0ModelBasicDetail;
    }

    public void setCfg0ModelBasicDetail(String cfg0ModelBasicDetail) {
        this.cfg0ModelBasicDetail = cfg0ModelBasicDetail;
    }
}