package sql.pojo.accessories;

import java.util.Date;

/**
 * 工艺辅料库
 */
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

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if (this == o) {
            return true;
        }
        if(o instanceof  HzAccessoriesLibs){
            HzAccessoriesLibs that = (HzAccessoriesLibs) o;
            if(this.getPuid().equals((that.getPuid()))){
                return true;
            }
            if(this.getpMaterielCode().equals(that.getpMaterielCode())){
                return  true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {

        return this.getpMaterielCode().hashCode();
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(String pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public String getpMaterielName() {
        return pMaterielName;
    }

    public void setpMaterielName(String pMaterielName) {
        this.pMaterielName = pMaterielName;
    }

    public String getpTechnicalConditions() {
        return pTechnicalConditions;
    }

    public void setpTechnicalConditions(String pTechnicalConditions) {
        this.pTechnicalConditions = pTechnicalConditions;
    }

    public String getpMeasuringUnit() {
        return pMeasuringUnit;
    }

    public void setpMeasuringUnit(String pMeasuringUnit) {
        this.pMeasuringUnit = pMeasuringUnit;
    }

    public String getpMaterielPurpose() {
        return pMaterielPurpose;
    }

    public void setpMaterielPurpose(String pMaterielPurpose) {
        this.pMaterielPurpose = pMaterielPurpose;
    }

    public String getpDosageBicycle() {
        return pDosageBicycle;
    }

    public void setpDosageBicycle(String pDosageBicycle) {
        this.pDosageBicycle = pDosageBicycle;
    }

    public String getpNote() {
        return pNote;
    }

    public void setpNote(String pNote) {
        this.pNote = pNote;
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
        this.pCreateName = pCreateName;
    }

    public String getpUpdateName() {
        return pUpdateName;
    }

    public void setpUpdateName(String pUpdateName) {
        this.pUpdateName = pUpdateName;
    }
}