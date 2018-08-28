package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgWithCfg {
    private BigDecimal id;

    private String cfgCfg0Uid;

    private String cfgBomlineUid;

    private Date flCfgCreateDate;

    private Date flCfgUpdateDate;

    private Object flCfgCreator;

    private Object flCfgUpdator;

    private Object flCfgBomlineName;

    private BigDecimal flCfgVersion;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
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

    public Object getFlCfgCreator() {
        return flCfgCreator;
    }

    public void setFlCfgCreator(Object flCfgCreator) {
        this.flCfgCreator = flCfgCreator;
    }

    public Object getFlCfgUpdator() {
        return flCfgUpdator;
    }

    public void setFlCfgUpdator(Object flCfgUpdator) {
        this.flCfgUpdator = flCfgUpdator;
    }

    public Object getFlCfgBomlineName() {
        return flCfgBomlineName;
    }

    public void setFlCfgBomlineName(Object flCfgBomlineName) {
        this.flCfgBomlineName = flCfgBomlineName;
    }

    public BigDecimal getFlCfgVersion() {
        return flCfgVersion;
    }

    public void setFlCfgVersion(BigDecimal flCfgVersion) {
        this.flCfgVersion = flCfgVersion;
    }
}