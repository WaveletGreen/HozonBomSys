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
}