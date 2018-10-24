package com.connor.hozon.bom.resources.domain.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
public class HzSingleVehiclesBomRespDTO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * Bom行对应的零件号
     */
    private String lineId;

    /**
     * 2Y层归属哪个部门
     */
    private String pBomOfWhichDept;
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
     * 采购件类型
     */
    private String resource;

    /**
     * 发货料库存地点
     */
    private String pStockLocation;

    /**
     * 对应于单车清单的主键
     */
    private Long singleVehiclesId;

    /**
     * 层级
     */
    private String level;

    /**
     * bom排列序号
     */
    private Integer No;

    /**
     * 工厂
     */
    private String pFactoryCode;

    /**
     * eBom表主键
     */
    private String eBomPuid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getPBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setPBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }
    public String getPBomLinePartName() {
        return pBomLinePartName;
    }

    public void setPBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }
    public String getPBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setPBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }
    public String getPBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setPBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }
    public String getPBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setPBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getpStockLocation() {
        return pStockLocation;
    }

    public void setpStockLocation(String pStockLocation) {
        this.pStockLocation = pStockLocation;
    }

    public String getPStockLocation() {
        return pStockLocation;
    }

    public void setPStockLocation(String pStockLocation) {
        this.pStockLocation = pStockLocation;
    }

    public Long getSingleVehiclesId() {
        return singleVehiclesId;
    }

    public void setSingleVehiclesId(Long singleVehiclesId) {
        this.singleVehiclesId = singleVehiclesId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getpFactoryCode() {
        return pFactoryCode;
    }

    public void setpFactoryCode(String pFactoryCode) {
        this.pFactoryCode = pFactoryCode;
    }
    public String getPFactoryCode() {
        return pFactoryCode;
    }

    public void setPFactoryCode(String pFactoryCode) {
        this.pFactoryCode = pFactoryCode;
    }

    public String geteBomPuid() {
        return eBomPuid;
    }

    public void seteBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }
    public String getEBomPuid() {
        return eBomPuid;
    }

    public void setEBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }
}
