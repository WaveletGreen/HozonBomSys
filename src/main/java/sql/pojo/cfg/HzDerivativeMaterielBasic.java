package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.Date;

public class HzDerivativeMaterielBasic {
    private BigDecimal id;

    private String dmbModelUid;

    private String dmbColorModelUid;

    private Object dmbCreator;

    private Date dmbCreateDate;

    private Object dmbUpdater;

    private Date dmbUpdateDate;

    private String dmbReserved1;

    private String dmbReserved2;

    private String dmbReserved3;

    private String dmbReserved4;

    private String dmbReserved5;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDmbModelUid() {
        return dmbModelUid;
    }

    public void setDmbModelUid(String dmbModelUid) {
        this.dmbModelUid = dmbModelUid == null ? null : dmbModelUid.trim();
    }

    public String getDmbColorModelUid() {
        return dmbColorModelUid;
    }

    public void setDmbColorModelUid(String dmbColorModelUid) {
        this.dmbColorModelUid = dmbColorModelUid == null ? null : dmbColorModelUid.trim();
    }

    public Object getDmbCreator() {
        return dmbCreator;
    }

    public void setDmbCreator(Object dmbCreator) {
        this.dmbCreator = dmbCreator;
    }

    public Date getDmbCreateDate() {
        return dmbCreateDate;
    }

    public void setDmbCreateDate(Date dmbCreateDate) {
        this.dmbCreateDate = dmbCreateDate;
    }

    public Object getDmbUpdater() {
        return dmbUpdater;
    }

    public void setDmbUpdater(Object dmbUpdater) {
        this.dmbUpdater = dmbUpdater;
    }

    public Date getDmbUpdateDate() {
        return dmbUpdateDate;
    }

    public void setDmbUpdateDate(Date dmbUpdateDate) {
        this.dmbUpdateDate = dmbUpdateDate;
    }

    public String getDmbReserved1() {
        return dmbReserved1;
    }

    public void setDmbReserved1(String dmbReserved1) {
        this.dmbReserved1 = dmbReserved1 == null ? null : dmbReserved1.trim();
    }

    public String getDmbReserved2() {
        return dmbReserved2;
    }

    public void setDmbReserved2(String dmbReserved2) {
        this.dmbReserved2 = dmbReserved2 == null ? null : dmbReserved2.trim();
    }

    public String getDmbReserved3() {
        return dmbReserved3;
    }

    public void setDmbReserved3(String dmbReserved3) {
        this.dmbReserved3 = dmbReserved3 == null ? null : dmbReserved3.trim();
    }

    public String getDmbReserved4() {
        return dmbReserved4;
    }

    public void setDmbReserved4(String dmbReserved4) {
        this.dmbReserved4 = dmbReserved4 == null ? null : dmbReserved4.trim();
    }

    public String getDmbReserved5() {
        return dmbReserved5;
    }

    public void setDmbReserved5(String dmbReserved5) {
        this.dmbReserved5 = dmbReserved5 == null ? null : dmbReserved5.trim();
    }
}