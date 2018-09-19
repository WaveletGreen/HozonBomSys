package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgMain {
    private BigDecimal id;

    private String projectUid;

    private String status;

    private Integer stage;

    private String version;

    private Date effectiveDate;

    private Date createDate;

    private Date updateDate;

    private String creator;

    private String updater;

    public static String parseStage(Integer code) {
        switch (code) {
            case 1:
                return "P0-P1阶段";
            case 2:
                return "P1-P2阶段";
            case 3:
                return "P2-P3阶段";
            case 4:
                return "P3-P4阶段";
            case 5:
                return "P4-P5阶段";
            case 6:
                return "P5-P6阶段";
            case 7:
                return "P6-P7阶段";
            case 8:
                return "P7-P8阶段";
            case 9:
                return "P8-P9阶段";
            case 10:
                return "P9-P10阶段";
            default:
                return "";
        }
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid == null ? null : projectUid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}