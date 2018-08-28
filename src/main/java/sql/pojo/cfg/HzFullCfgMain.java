package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgMain {
    private BigDecimal id;

    private String projectUid;

    private Object status;

    private Object stage;

    private Object version;

    private Date effectiveDate;

    private Date createDate;

    private Date updateDate;

    private Object creator;

    private Object updater;

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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getStage() {
        return stage;
    }

    public void setStage(Object stage) {
        this.stage = stage;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
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

    public Object getCreator() {
        return creator;
    }

    public void setCreator(Object creator) {
        this.creator = creator;
    }

    public Object getUpdater() {
        return updater;
    }

    public void setUpdater(Object updater) {
        this.updater = updater;
    }
}