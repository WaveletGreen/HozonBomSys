package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.Date;

public class HzDerivativeMaterielDetail {
    private BigDecimal id;

    private BigDecimal dmdDmbId;

    private String dmdCfg0Uid;

    private String dmdCfg0FamilyUid;

    private Object dmdCreator;

    private Date dmdCreateDate;

    private Object dmdUpdater;

    private Date dmdUpdateDate;

    private String dmdReserved1;

    private String dmdReserved2;

    private String dmdReserved3;

    private String dmdReserved4;

    private String dmdReserved5;

    private String dmdFeatureValue;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getDmdDmbId() {
        return dmdDmbId;
    }

    public void setDmdDmbId(BigDecimal dmdDmbId) {
        this.dmdDmbId = dmdDmbId;
    }

    public String getDmdCfg0Uid() {
        return dmdCfg0Uid;
    }

    public void setDmdCfg0Uid(String dmdCfg0Uid) {
        this.dmdCfg0Uid = dmdCfg0Uid == null ? null : dmdCfg0Uid.trim();
    }

    public String getDmdCfg0FamilyUid() {
        return dmdCfg0FamilyUid;
    }

    public void setDmdCfg0FamilyUid(String dmdCfg0FamilyUid) {
        this.dmdCfg0FamilyUid = dmdCfg0FamilyUid == null ? null : dmdCfg0FamilyUid.trim();
    }

    public Object getDmdCreator() {
        return dmdCreator;
    }

    public void setDmdCreator(Object dmdCreator) {
        this.dmdCreator = dmdCreator;
    }

    public Date getDmdCreateDate() {
        return dmdCreateDate;
    }

    public void setDmdCreateDate(Date dmdCreateDate) {
        this.dmdCreateDate = dmdCreateDate;
    }

    public Object getDmdUpdater() {
        return dmdUpdater;
    }

    public void setDmdUpdater(Object dmdUpdater) {
        this.dmdUpdater = dmdUpdater;
    }

    public Date getDmdUpdateDate() {
        return dmdUpdateDate;
    }

    public void setDmdUpdateDate(Date dmdUpdateDate) {
        this.dmdUpdateDate = dmdUpdateDate;
    }

    public String getDmdReserved1() {
        return dmdReserved1;
    }

    public void setDmdReserved1(String dmdReserved1) {
        this.dmdReserved1 = dmdReserved1 == null ? null : dmdReserved1.trim();
    }

    public String getDmdReserved2() {
        return dmdReserved2;
    }

    public void setDmdReserved2(String dmdReserved2) {
        this.dmdReserved2 = dmdReserved2 == null ? null : dmdReserved2.trim();
    }

    public String getDmdReserved3() {
        return dmdReserved3;
    }

    public void setDmdReserved3(String dmdReserved3) {
        this.dmdReserved3 = dmdReserved3 == null ? null : dmdReserved3.trim();
    }

    public String getDmdReserved4() {
        return dmdReserved4;
    }

    public void setDmdReserved4(String dmdReserved4) {
        this.dmdReserved4 = dmdReserved4 == null ? null : dmdReserved4.trim();
    }

    public String getDmdReserved5() {
        return dmdReserved5;
    }

    public void setDmdReserved5(String dmdReserved5) {
        this.dmdReserved5 = dmdReserved5 == null ? null : dmdReserved5.trim();
    }

    public String getDmdFeatureValue() {
        return dmdFeatureValue;
    }

    public void setDmdFeatureValue(String dmdFeatureValue) {
        this.dmdFeatureValue = dmdFeatureValue == null ? null : dmdFeatureValue.trim();
    }
}