package sql.pojo.cfg.fullCfg;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全配置BOM一级清单基本车型模型数据
 */
public class HzFullCfgModel {
    private BigDecimal id;

    private String modModelUid;

    private String modCfg0Uid;

    private Short modPointType;

    private Date flModCreateDate;

    private Date flModUpdateDate;

    private String flModCreator;

    private String flModLastUpdater;

    private BigDecimal flModVersion;
    /**
     * 2Y层主键
     */
    private String flModelBomlineUid;

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

    public String getFlModCreator() {
        return flModCreator;
    }

    public void setFlModCreator(String flModCreator) {
        this.flModCreator = flModCreator;
    }

    public String getFlModLastUpdater() {
        return flModLastUpdater;
    }

    public void setFlModLastUpdater(String flModLastUpdater) {
        this.flModLastUpdater = flModLastUpdater;
    }

    public BigDecimal getFlModVersion() {
        return flModVersion;
    }

    public void setFlModVersion(BigDecimal flModVersion) {
        this.flModVersion = flModVersion;
    }

    public String getFlModelBomlineUid() {
        return flModelBomlineUid;
    }

    public void setFlModelBomlineUid(String flModelBomlineUid) {
        this.flModelBomlineUid = flModelBomlineUid;
    }
}