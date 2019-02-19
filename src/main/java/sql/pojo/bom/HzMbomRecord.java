package sql.pojo.bom;

import sql.pojo.BaseChangeDO;

import java.util.Date;

/**
 * Created by haozt on 2018/5/24
 */
public class HzMbomRecord extends BaseChangeDO {
    /**
     * 主键id
     */
    private String puid;

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
     * 外键
     */
    private String eBomPuid;

    /**
     * 删除标志位 1未删除 0 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建者
     */
    private String createName;
    /**
     * 更改者
     */
    private String updateName;

    private Integer orderNum;

    /**
     * bom类型
     */
    private Integer pBomType;
    /**
     * 发货料库存地点
     */
    private String pStockLocation;

    /**
     * 工厂
     */
    private String pFactoryId;

    public Integer getpBomType() {
        return pBomType;
    }

    public void setpBomType(Integer pBomType) {
        this.pBomType = pBomType;
    }

    public String getpStockLocation() {
        return pStockLocation;
    }

    public void setpStockLocation(String pStockLocation) {
        this.pStockLocation = pStockLocation;
    }

    public String getpFactoryId() {
        return pFactoryId;
    }

    public void setpFactoryId(String pFactoryId) {
        this.pFactoryId = pFactoryId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getSparePart() {
        return sparePart;
    }

    public void setSparePart(String sparePart) {
        this.sparePart = sparePart == null ? null : sparePart.trim();
    }

    public String getSparePartNum() {
        return sparePartNum;
    }

    public void setSparePartNum(String sparePartNum) {
        this.sparePartNum = sparePartNum == null ? null : sparePartNum.trim();
    }

    public String getProcessRoute() {
        return processRoute;
    }

    public void setProcessRoute(String processRoute) {
        this.processRoute = processRoute == null ? null : processRoute.trim();
    }

    public String getLaborHour() {
        return laborHour;
    }

    public void setLaborHour(String laborHour) {
        this.laborHour = laborHour == null ? null : laborHour.trim();
    }

    public String getRhythm() {
        return rhythm;
    }

    public void setRhythm(String rhythm) {
        this.rhythm = rhythm == null ? null : rhythm.trim();
    }

    public String getSolderJoint() {
        return solderJoint;
    }

    public void setSolderJoint(String solderJoint) {
        this.solderJoint = solderJoint == null ? null : solderJoint.trim();
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
        this.standardPart = standardPart == null ? null : standardPart.trim();
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools == null ? null : tools.trim();
    }

    public String getWasterProduct() {
        return wasterProduct;
    }

    public void setWasterProduct(String wasterProduct) {
        this.wasterProduct = wasterProduct == null ? null : wasterProduct.trim();
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change == null ? null : change.trim();
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum == null ? null : changeNum.trim();
    }

    public String geteBomPuid() {
        return eBomPuid;
    }

    public void seteBomPuid(String eBomPuid) {
        this.eBomPuid = eBomPuid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
