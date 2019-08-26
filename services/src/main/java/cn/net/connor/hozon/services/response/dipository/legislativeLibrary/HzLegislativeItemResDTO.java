package cn.net.connor.hozon.services.response.dipository.legislativeLibrary;

import java.util.Date;

public class HzLegislativeItemResDTO {
    /**
     * 序号
     */
    private Integer no;
    /**
     * 主键
     */
    private String puid;
    /**
     * 法规件名称
     */
    private String legislativeName;
    /**
     * 法规件型号
     */
    private String legislativeNo;
    /**
     * 适用车型
     */
    private String applicableModels;
    /**
     * 公告号
     */
    private String  noticeNo;
    /**
     * EPL ID
     */
    private String  eplId;
    /**
     * 零件号
     */
    private String partId;
    /**
     * 零件名称
     */
    private String partName;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 供应商代码
     */
    private String supplierNo;
    /**
     * 技术特性描述
     */
    private String technologyDesc;
    /**
     * 申请部门
     */
    private String applyDepa;
    /**
     * 是否需要强检报告
     */
    private String isHaveTest;
    /**
     * 是否需要3C证书
     */
    private String isHaveCcc;
    /**
     * 责任工程师
     */
    private String dutyEngineer;
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
    private Integer status;
    /**
     * 备注
     */
    private String  remarks;

    /**
     * 关联法规件puid
     */
    private String legislativeUid;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLegislativeName() {
        return legislativeName;
    }

    public void setLegislativeName(String legislativeName) {
        this.legislativeName = legislativeName;
    }

    public String getLegislativeNo() {
        return legislativeNo;
    }

    public void setLegislativeNo(String legislativeNo) {
        this.legislativeNo = legislativeNo;
    }

    public String getApplicableModels() {
        return applicableModels;
    }

    public void setApplicableModels(String applicableModels) {
        this.applicableModels = applicableModels;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getEplId() {
        return eplId;
    }

    public void setEplId(String eplId) {
        this.eplId = eplId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getTechnologyDesc() {
        return technologyDesc;
    }

    public void setTechnologyDesc(String technologyDesc) {
        this.technologyDesc = technologyDesc;
    }

    public String getApplyDepa() {
        return applyDepa;
    }

    public void setApplyDepa(String applyDepa) {
        this.applyDepa = applyDepa;
    }

    public String getIsHaveTest() {
        return isHaveTest;
    }

    public void setIsHaveTest(String isHaveTest) {
        this.isHaveTest = isHaveTest;
    }

    public String getIsHaveCcc() {
        return isHaveCcc;
    }

    public void setIsHaveCcc(String isHaveCcc) {
        this.isHaveCcc = isHaveCcc;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDutyEngineer() {
        return dutyEngineer;
    }

    public void setDutyEngineer(String dutyEngineer) {
        this.dutyEngineer = dutyEngineer;
    }

    public String getLegislativeUid() {
        return legislativeUid;
    }

    public void setLegislativeUid(String legislativeUid) {
        this.legislativeUid = legislativeUid;
    }
}
