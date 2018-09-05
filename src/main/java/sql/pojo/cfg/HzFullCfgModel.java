package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgModel {
    private BigDecimal id;

    private String modModelUid;

    private String modCfg0Uid;

    private Short modPointType;

    private Date flModCreateDate;

    private Date flModUpdateDate;

    private Object flModCreator;

    private Object flModLastUpdater;

    private BigDecimal flModVersion;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getModModelUid() {
        return modModelUid;
    }

    public void setModModelUid(String modModelUid) {
        this.modModelUid = modModelUid == null ? null : modModelUid.trim();
    }

    public String getModCfg0Uid() {
        return modCfg0Uid;
    }

    public void setModCfg0Uid(String modCfg0Uid) {
        this.modCfg0Uid = modCfg0Uid == null ? null : modCfg0Uid.trim();
    }

    public Short getModPointType() {
        return modPointType;
    }

    public void setModPointType(Short modPointType) {
        this.modPointType = modPointType;
    }

    public Date getFlModCreateDate() {
        return flModCreateDate;
    }

    public void setFlModCreateDate(Date flModCreateDate) {
        this.flModCreateDate = flModCreateDate;
    }

    public Date getFlModUpdateDate() {
        return flModUpdateDate;
    }

    public void setFlModUpdateDate(Date flModUpdateDate) {
        this.flModUpdateDate = flModUpdateDate;
    }

    public Object getFlModCreator() {
        return flModCreator;
    }

    public void setFlModCreator(Object flModCreator) {
        this.flModCreator = flModCreator;
    }

    public Object getFlModLastUpdater() {
        return flModLastUpdater;
    }

    public void setFlModLastUpdater(Object flModLastUpdater) {
        this.flModLastUpdater = flModLastUpdater;
    }

    public BigDecimal getFlModVersion() {
        return flModVersion;
    }

    public void setFlModVersion(BigDecimal flModVersion) {
        this.flModVersion = flModVersion;
    }
}