package cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary;

import java.util.Date;
@Deprecated
public class HzLegislativeAutoType {
    /**
     * 主键
     */
    private String puid;
    /**
     * 公告号
     */
    private String noticeNo;
    /**
     * 车型
     */
    private String autoType;
    /**
     * VIN码前8位
     */
    private String vinNo;
    /**
     * 电池厂家
     */
    private String batteryManufacturers;
    /**
     * 储能装置电池包(箱)型号
     */
    private String batteryModel;
    /**
     * 生产方式(厂家)
     */
    private String productionMode;
    /**
     * 电机厂家
     */
    private String motorManufacturers;
    /**
     * 电机型号
     */
    private String motorModel;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 状态
     */
    private Short status;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo == null ? null : noticeNo.trim();
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo == null ? null : vinNo.trim();
    }

    public String getBatteryManufacturers() {
        return batteryManufacturers;
    }

    public void setBatteryManufacturers(String batteryManufacturers) {
        this.batteryManufacturers = batteryManufacturers == null ? null : batteryManufacturers.trim();
    }

    public String getBatteryModel() {
        return batteryModel;
    }

    public void setBatteryModel(String batteryModel) {
        this.batteryModel = batteryModel == null ? null : batteryModel.trim();
    }

    public String getProductionMode() {
        return productionMode;
    }

    public void setProductionMode(String productionMode) {
        this.productionMode = productionMode == null ? null : productionMode.trim();
    }

    public String getMotorManufacturers() {
        return motorManufacturers;
    }

    public void setMotorManufacturers(String motorManufacturers) {
        this.motorManufacturers = motorManufacturers == null ? null : motorManufacturers.trim();
    }

    public String getMotorModel() {
        return motorModel;
    }

    public void setMotorModel(String motorModel) {
        this.motorModel = motorModel == null ? null : motorModel.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}