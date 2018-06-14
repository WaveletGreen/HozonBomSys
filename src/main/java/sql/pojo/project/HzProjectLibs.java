package sql.pojo.project;

import java.util.Calendar;
import java.util.Date;

public class HzProjectLibs {
    private String puid;
    /***
     * 项目名
     */
    private String pProjectName;
    /**
     * 归属平台
     */
    private String pProjectPertainToPlatform;
    /***
     * 项目编号
     */
    private String pProjectCode;
    /**
     * 车型代码
     */
    private String pVehicleCode;
    /***
     * 车型名称
     */
    private String pVehicleName;
    /**
     * 车身形式代码
     */
    private String pVehicleShapeCode;
    /**
     * 车身变形名称
     */
    private String pVehicleTranName;
    /**
     * 车型年代码
     */
    private String pVehicleAnnualCode;
    /**
     * 年型年
     */
    private String pVehicleAnnual;
    private String pProjectOwningUser;
    private Date pProjectCreateDate;
    private Date pProjectLastModDate;
    private Date pProjectDiscontinuationDate;

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpProjectName() {
        return pProjectName;
    }

    public void setpProjectName(String pProjectName) {
        this.pProjectName = pProjectName;
    }

    public String getpProjectPertainToPlatform() {
        return pProjectPertainToPlatform;
    }

    public void setpProjectPertainToPlatform(String pProjectPertainToPlatform) {
        this.pProjectPertainToPlatform = pProjectPertainToPlatform;
    }

    public String getpVehicleCode() {
        return pVehicleCode;
    }

    public void setpVehicleCode(String pVehicleCode) {
        this.pVehicleCode = pVehicleCode;
    }

    public String getpVehicleName() {
        return pVehicleName;
    }

    public void setpVehicleName(String pVehicleName) {
        this.pVehicleName = pVehicleName;
    }

    public String getpVehicleShapeCode() {
        return pVehicleShapeCode;
    }

    public void setpVehicleShapeCode(String pVehicleShapeCode) {
        this.pVehicleShapeCode = pVehicleShapeCode;
    }

    public String getpVehicleTranName() {
        return pVehicleTranName;
    }

    public void setpVehicleTranName(String pVehicleTranName) {
        this.pVehicleTranName = pVehicleTranName;
    }

    public String getpVehicleAnnualCode() {
        return pVehicleAnnualCode;
    }

    public void setpVehicleAnnualCode(String pVehicleAnnualCode) {
        this.pVehicleAnnualCode = pVehicleAnnualCode;
    }

    public String getpVehicleAnnual() {
        return pVehicleAnnual;
    }

    public void setpVehicleAnnual(String pVehicleAnnual) {
        this.pVehicleAnnual = pVehicleAnnual;
    }

    public String getpProjectOwningUser() {
        return pProjectOwningUser;
    }

    public void setpProjectOwningUser(String pProjectOwningUser) {
        this.pProjectOwningUser = pProjectOwningUser;
    }

    public Date getpProjectCreateDate() {
        return pProjectCreateDate;
    }

    public void setpProjectCreateDate(Date pProjectCreateDate) {
        this.pProjectCreateDate = pProjectCreateDate;
    }

    public Date getpProjectLastModDate() {
        return pProjectLastModDate;
    }

    public void setpProjectLastModDate(Date pProjectLastModDate) {
        this.pProjectLastModDate = pProjectLastModDate;
    }

    public Date getpProjectDiscontinuationDate() {
        return pProjectDiscontinuationDate;
    }

    public void setpProjectDiscontinuationDate(Date pProjectDiscontinuationDate) {
        if (pProjectDiscontinuationDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(9998, 12, 31, 23, 59, 59);
            this.pProjectDiscontinuationDate = calendar.getTime();
        } else
            this.pProjectDiscontinuationDate = pProjectDiscontinuationDate;
    }

}