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
     * 归属车型
     */
    private String pProjectPertainToVehicle;
    /***
     * 项目编号
     */
    private String pProjectCode;
    /***
     * 创建者
     */
    private String pProjectOwningUser;
    /***
     * 创建时间
     */
    private Date pProjectCreateDate;
    /***
     * 最后一次修时间
     */
    private Date pProjectLastModDate;
    /**
     * 停产时间
     */
    private Date pProjectDiscontinuationDate;
    /**
     * 项目经理
     */
    private String pProjectManager;
    /**
     * 最后一次修改人
     */
    private String pProjectLastModifier;
    /**
     * 项目详细信息
     */
    private String pProjectDetail;

    /**
     * 内饰颜色代码
     */
    private String pInColorCode;
    /**
     * 内饰颜色代码
     */
    private String pInColorName;
    /**
     * 内饰颜色名称
     */
    private String pColorCode;
    /**
     * 颜色名称
     */
    private String pColorName;
    /**
     * 电池型号
     */
    private String pBaterryCode;
    /**
     * 电机型号
     */
    private String pMotorCode;
    /**
     * 车型年代码
     */
    private String pModelYearCode;
    /**
     * 车型年
     */
    private String pModelYear;

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

    public String getpProjectPertainToVehicle() {
        return pProjectPertainToVehicle;
    }

    public void setpProjectPertainToVehicle(String pProjectPertainToVehicle) {
        this.pProjectPertainToVehicle = pProjectPertainToVehicle;
    }

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public String getpProjectManager() {
        return pProjectManager;
    }

    public void setpProjectManager(String pProjectManager) {
        this.pProjectManager = pProjectManager;
    }

    public String getpProjectLastModifier() {
        return pProjectLastModifier;
    }

    public void setpProjectLastModifier(String pProjectLastModifier) {
        this.pProjectLastModifier = pProjectLastModifier;
    }

    public String getpProjectDetail() {
        return pProjectDetail;
    }

    public void setpProjectDetail(String pProjectDetail) {
        this.pProjectDetail = pProjectDetail;
    }

    public String getpInColorCode() {
        return pInColorCode;
    }

    public void setpInColorCode(String pInColorCode) {
        this.pInColorCode = pInColorCode;
    }

    public String getpInColorName() {
        return pInColorName;
    }

    public void setpInColorName(String pInColorName) {
        this.pInColorName = pInColorName;
    }

    public String getpColorCode() {
        return pColorCode;
    }

    public void setpColorCode(String pColorCode) {
        this.pColorCode = pColorCode;
    }

    public String getpColorName() {
        return pColorName;
    }

    public void setpColorName(String pColorName) {
        this.pColorName = pColorName;
    }

    public String getpBaterryCode() {
        return pBaterryCode;
    }

    public void setpBaterryCode(String pBaterryCode) {
        this.pBaterryCode = pBaterryCode;
    }

    public String getpMotorCode() {
        return pMotorCode;
    }

    public void setpMotorCode(String pMotorCode) {
        this.pMotorCode = pMotorCode;
    }

    public String getpModelYearCode() {
        return pModelYearCode;
    }

    public void setpModelYearCode(String pModelYearCode) {
        this.pModelYearCode = pModelYearCode;
    }

    public String getpModelYear() {
        return pModelYear;
    }

    public void setpModelYear(String pModelYear) {
        this.pModelYear = pModelYear;
    }

}