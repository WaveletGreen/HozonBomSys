package sql.pojo.cfg;

import java.util.Date;

public class HzFullCfgWithCfg {
    private Long id;

    private String cfgCfg0Uid;

    private String cfgBomlineUid;

    private Date flCfgCreateDate;

    private Date flCfgUpdateDate;

    private String flCfgCreator;

    private String flCfgUpdator;

    private String flCfgBomlineName;

    private Long flCfgVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCfgCfg0Uid() {
        return cfgCfg0Uid;
    }

    public void setCfgCfg0Uid(String cfgCfg0Uid) {
        this.cfgCfg0Uid = cfgCfg0Uid == null ? null : cfgCfg0Uid.trim();
    }

    public String getCfgBomlineUid() {
        return cfgBomlineUid;
    }

    public void setCfgBomlineUid(String cfgBomlineUid) {
        this.cfgBomlineUid = cfgBomlineUid == null ? null : cfgBomlineUid.trim();
    }

    public Date getFlCfgCreateDate() {
        return flCfgCreateDate;
    }

    public void setFlCfgCreateDate(Date flCfgCreateDate) {
        this.flCfgCreateDate = flCfgCreateDate;
    }

    public Date getFlCfgUpdateDate() {
        return flCfgUpdateDate;
    }

    public void setFlCfgUpdateDate(Date flCfgUpdateDate) {
        this.flCfgUpdateDate = flCfgUpdateDate;
    }

    public String getFlCfgCreator() {
        return flCfgCreator;
    }

    public void setFlCfgCreator(String flCfgCreator) {
        this.flCfgCreator = flCfgCreator;
    }

    public String getFlCfgUpdator() {
        return flCfgUpdator;
    }

    public void setFlCfgUpdator(String flCfgUpdator) {
        this.flCfgUpdator = flCfgUpdator;
    }

    public String getFlCfgBomlineName() {
        return flCfgBomlineName;
    }

    public void setFlCfgBomlineName(String flCfgBomlineName) {
        this.flCfgBomlineName = flCfgBomlineName;
    }

    public Long getFlCfgVersion() {
        return flCfgVersion;
    }

    public void setFlCfgVersion(Long flCfgVersion) {
        this.flCfgVersion = flCfgVersion;
    }
}