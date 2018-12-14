package sql.pojo.accessories;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/12
 * Time: 14:55
 */

import java.util.Date;
import java.util.Objects;

/**
 * excel导入工艺辅料专用
 */
public class HzImportAccessoriesLibs {

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HzImportAccessoriesLibs that = (HzImportAccessoriesLibs) o;
        return  Objects.equals(puid, that.puid) &&
                Objects.equals(pMaterielCode, that.pMaterielCode) &&
                Objects.equals(pMaterielName, that.pMaterielName) &&
                Objects.equals(pTechnicalConditions, that.pTechnicalConditions) &&
                Objects.equals(pMeasuringUnit, that.pMeasuringUnit) &&
                Objects.equals(pMaterielPurpose, that.pMaterielPurpose) &&
                Objects.equals(pDosageBicycle, that.pDosageBicycle) &&
                Objects.equals(pNote, that.pNote) &&
                Objects.equals(pStatus, that.pStatus) &&
                Objects.equals(pCreateTime, that.pCreateTime) &&
                Objects.equals(pUpdateTime, that.pUpdateTime) &&
                Objects.equals(pCreateName, that.pCreateName) &&
                Objects.equals(pUpdateName, that.pUpdateName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(puid, pMaterielCode, pMaterielName, pTechnicalConditions, pMeasuringUnit, pMaterielPurpose, pDosageBicycle, pNote, pStatus, pCreateTime, pUpdateTime, pCreateName, pUpdateName);
    }

    @Override
    public String toString() {
        return "HzImportAccessoriesLibs{" +
                "  puid='" + puid + '\'' +
                ", pMaterielCode='" + pMaterielCode + '\'' +
                ", pMaterielName='" + pMaterielName + '\'' +
                ", pTechnicalConditions='" + pTechnicalConditions + '\'' +
                ", pMeasuringUnit='" + pMeasuringUnit + '\'' +
                ", pMaterielPurpose='" + pMaterielPurpose + '\'' +
                ", pDosageBicycle='" + pDosageBicycle + '\'' +
                ", pNote='" + pNote + '\'' +
                ", pStatus=" + pStatus +
                ", pCreateTime=" + pCreateTime +
                ", pUpdateTime=" + pUpdateTime +
                ", pCreateName='" + pCreateName + '\'' +
                ", pUpdateName='" + pUpdateName + '\'' +
                '}';
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

