package sql.pojo.accessories;

import java.util.Date;

public class HzAccessoriesLibs {
    private String puid;

    private String pMaterielCode;

    private String pMaterielName;

    private String pTechnicalConditions;

    private String pMeasuringUnit;

    private String pMaterielPurpose;

    private String pDosageBicycle;

    private String pNote;

    private Short pStatus;

    private Date pCreateTime;

    private Date pUpdateTime;

    private String pCreateName;

    private String pUpdateName;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(String pMaterielCode) {
        this.pMaterielCode = pMaterielCode == null ? null : pMaterielCode.trim();
    }

    public String getpMaterielName() {
        return pMaterielName;
    }

    public void setpMaterielName(String pMaterielName) {
        this.pMaterielName = pMaterielName == null ? null : pMaterielName.trim();
    }

    public String getpTechnicalConditions() {
        return pTechnicalConditions;
    }

    public void setpTechnicalConditions(String pTechnicalConditions) {
        this.pTechnicalConditions = pTechnicalConditions == null ? null : pTechnicalConditions.trim();
    }

    public String getpMeasuringUnit() {
        return pMeasuringUnit;
    }

    public void setpMeasuringUnit(String pMeasuringUnit) {
        this.pMeasuringUnit = pMeasuringUnit == null ? null : pMeasuringUnit.trim();
    }

    public String getpMaterielPurpose() {
        return pMaterielPurpose;
    }

    public void setpMaterielPurpose(String pMaterielPurpose) {
        this.pMaterielPurpose = pMaterielPurpose == null ? null : pMaterielPurpose.trim();
    }

    public String getpDosageBicycle() {
        return pDosageBicycle;
    }

    public void setpDosageBicycle(String pDosageBicycle) {
        this.pDosageBicycle = pDosageBicycle == null ? null : pDosageBicycle.trim();
    }

    public String getpNote() {
        return pNote;
    }

    public void setpNote(String pNote) {
        this.pNote = pNote == null ? null : pNote.trim();
    }

    public Short getpStatus() {
        return pStatus;
    }

    public void setpStatus(Short pStatus) {
        this.pStatus = pStatus;
    }

    public Date getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(Date pCreateTime) {
        this.pCreateTime = pCreateTime;
    }

    public Date getpUpdateTime() {
        return pUpdateTime;
    }

    public void setpUpdateTime(Date pUpdateTime) {
        this.pUpdateTime = pUpdateTime;
    }

    public String getpCreateName() {
        return pCreateName;
    }

    public void setpCreateName(String pCreateName) {
        this.pCreateName = pCreateName == null ? null : pCreateName.trim();
    }

    public String getpUpdateName() {
        return pUpdateName;
    }

    public void setpUpdateName(String pUpdateName) {
        this.pUpdateName = pUpdateName == null ? null : pUpdateName.trim();
    }
}