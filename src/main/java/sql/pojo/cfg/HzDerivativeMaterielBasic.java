package sql.pojo.cfg;

import java.util.Date;

public class HzDerivativeMaterielBasic {
    /**
     * 主键
     */
    private Long id;
    /**
     * 基础模型外键
     */
    private String dmbModelUid;
    /**
     * 颜色车型外键
      */
    private String dmbColorModelUid;
    /**
     * 创建者
     */
    private String dmbCreator;
    /**
     * 创建日期
     */
    private Date dmbCreateDate;
    /**
     * 更新者
     */
    private String dmbUpdater;
    /**
     * 更新日期
     */
    private Date dmbUpdateDate;
    /**
     * 项目UID
     */
    private String dmbProjectUid;
    /**
     * 预留字段1
     */
    private String dmbModelFeatureUid;
    /**
     * 预留字段2
     */
    private String dmbReserved2;
    /**
     * 预留字段3
     */
    private String dmbReserved3;
    /**
     * 预留字段4
     */
    private String dmbReserved4;
    /**
     * 预留字段5
     */
    private String dmbReserved5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDmbCreator() {
        return dmbCreator;
    }

    public void setDmbCreator(String dmbCreator) {
        this.dmbCreator = dmbCreator;
    }

    public Date getDmbCreateDate() {
        return dmbCreateDate;
    }

    public void setDmbCreateDate(Date dmbCreateDate) {
        this.dmbCreateDate = dmbCreateDate;
    }

    public String getDmbUpdater() {
        return dmbUpdater;
    }

    public void setDmbUpdater(String dmbUpdater) {
        this.dmbUpdater = dmbUpdater;
    }

    public Date getDmbUpdateDate() {
        return dmbUpdateDate;
    }

    public void setDmbUpdateDate(Date dmbUpdateDate) {
        this.dmbUpdateDate = dmbUpdateDate;
    }

    public String getDmbModelFeatureUid() {
        return dmbModelFeatureUid;
    }

    public void setDmbModelFeatureUid(String dmbModelFeatureUid) {
        this.dmbModelFeatureUid = dmbModelFeatureUid == null ? null : dmbModelFeatureUid.trim();
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

    public String getDmbProjectUid() {
        return dmbProjectUid;
    }

    public void setDmbProjectUid(String dmbProjectUid) {
        this.dmbProjectUid = dmbProjectUid;
    }
}