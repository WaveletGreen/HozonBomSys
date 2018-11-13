package sql.pojo.cfg.derivative;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.Date;

/**
 * 衍生物料变更详情信息
 */
public class HzDMDetailChangeBean {


    /**
     * 主键
     */
    private Long id;
    /**
     * 变更表单ID
     */
    private Long formId;
    /**
     * 基本信息外键
     */
    private Long dmdDmbId;
    /**
     * 特性值外键
     */
    private String dmdCfg0Uid;
    /**
     * 特性外键
     */
    private String dmdCfg0FamilyUid;
    /**
     * 源数据创建者
     */
    private String dmdCreator;
    /**
     * 源数据创建时间
     */
    private Date dmdCreateDate;
    /**
     * 源数据更新人
     */
    private String dmdUpdater;
    /**
     * 源数据更新时间
     */
    private Date dmdUpdateDate;
    /**
     * 创建者
     */
    private String changeCreator;
    /**
     * 创建时间
     */
    private Date changeCreateDate;
    /**
     * 更新人
     */
    private String changeUpdater;
    /**
     * 更新时间
     */
    private Date changeUpdateDate;
    /**
     * 保留字段1
     */
    private String dmdReserved1;
    /**
     * 保留字段2
     */
    private String dmdReserved2;
    /**
     * 保留字段3
     */
    private String dmdReserved3;
    /**
     * 保留字段4
     */
    private String dmdReserved4;
    /**
     * 保留字段5
     */
    private String dmdReserved5;
    /**
     * 特性值,放在最后直接显示在前端
     */
    private String dmdFeatureValue;
    /**
     * 特性对象
     */
    private HzCfg0Record cfg0Record;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getDmdDmbId() {
        return dmdDmbId;
    }

    public void setDmdDmbId(Long dmdDmbId) {
        this.dmdDmbId = dmdDmbId;
    }

    public String getDmdCfg0Uid() {
        return dmdCfg0Uid;
    }

    public void setDmdCfg0Uid(String dmdCfg0Uid) {
        this.dmdCfg0Uid = dmdCfg0Uid;
    }

    public String getDmdCfg0FamilyUid() {
        return dmdCfg0FamilyUid;
    }

    public void setDmdCfg0FamilyUid(String dmdCfg0FamilyUid) {
        this.dmdCfg0FamilyUid = dmdCfg0FamilyUid;
    }

    public String getDmdCreator() {
        return dmdCreator;
    }

    public void setDmdCreator(String dmdCreator) {
        this.dmdCreator = dmdCreator;
    }

    public Date getDmdCreateDate() {
        return dmdCreateDate;
    }

    public void setDmdCreateDate(Date dmdCreateDate) {
        this.dmdCreateDate = dmdCreateDate;
    }

    public String getDmdUpdater() {
        return dmdUpdater;
    }

    public void setDmdUpdater(String dmdUpdater) {
        this.dmdUpdater = dmdUpdater;
    }

    public Date getDmdUpdateDate() {
        return dmdUpdateDate;
    }

    public void setDmdUpdateDate(Date dmdUpdateDate) {
        this.dmdUpdateDate = dmdUpdateDate;
    }

    public String getChangeCreator() {
        return changeCreator;
    }

    public void setChangeCreator(String changeCreator) {
        this.changeCreator = changeCreator;
    }

    public Date getChangeCreateDate() {
        return changeCreateDate;
    }

    public void setChangeCreateDate(Date changeCreateDate) {
        this.changeCreateDate = changeCreateDate;
    }

    public String getChangeUpdater() {
        return changeUpdater;
    }

    public void setChangeUpdater(String changeUpdater) {
        this.changeUpdater = changeUpdater;
    }

    public Date getChangeUpdateDate() {
        return changeUpdateDate;
    }

    public void setChangeUpdateDate(Date changeUpdateDate) {
        this.changeUpdateDate = changeUpdateDate;
    }

    public String getDmdReserved1() {
        return dmdReserved1;
    }

    public void setDmdReserved1(String dmdReserved1) {
        this.dmdReserved1 = dmdReserved1;
    }

    public String getDmdReserved2() {
        return dmdReserved2;
    }

    public void setDmdReserved2(String dmdReserved2) {
        this.dmdReserved2 = dmdReserved2;
    }

    public String getDmdReserved3() {
        return dmdReserved3;
    }

    public void setDmdReserved3(String dmdReserved3) {
        this.dmdReserved3 = dmdReserved3;
    }

    public String getDmdReserved4() {
        return dmdReserved4;
    }

    public void setDmdReserved4(String dmdReserved4) {
        this.dmdReserved4 = dmdReserved4;
    }

    public String getDmdReserved5() {
        return dmdReserved5;
    }

    public void setDmdReserved5(String dmdReserved5) {
        this.dmdReserved5 = dmdReserved5;
    }

    public String getDmdFeatureValue() {
        return dmdFeatureValue;
    }

    public void setDmdFeatureValue(String dmdFeatureValue) {
        this.dmdFeatureValue = dmdFeatureValue;
    }

    public HzCfg0Record getCfg0Record() {
        return cfg0Record;
    }

    public void setCfg0Record(HzCfg0Record cfg0Record) {
        this.cfg0Record = cfg0Record;
    }

    public void srcSetChange(HzDerivativeMaterielDetail hzDerivativeMaterielDetail) {
        Date date = new Date();
        User user = UserInfo.getUser();

        this.setDmdDmbId(hzDerivativeMaterielDetail.getDmdDmbId());
        this.setDmdCfg0Uid(hzDerivativeMaterielDetail.getDmdCfg0Uid());
        this.setDmdCfg0FamilyUid(hzDerivativeMaterielDetail.getDmdCfg0FamilyUid());
        this.setDmdCreator(hzDerivativeMaterielDetail.getDmdCreator());
        this.setDmdCreateDate(hzDerivativeMaterielDetail.getDmdCreateDate());
        this.setDmdUpdater(hzDerivativeMaterielDetail.getDmdUpdater());
        this.setDmdUpdateDate(hzDerivativeMaterielDetail.getDmdUpdateDate());
        this.setChangeCreator(user.getLogin());
        this.setChangeCreateDate(date);
        this.setChangeUpdater(user.getLogin());
        this.setChangeUpdateDate(date);
        this.setDmdReserved1(hzDerivativeMaterielDetail.getDmdReserved1());
        this.setDmdReserved2(hzDerivativeMaterielDetail.getDmdReserved2());
        this.setDmdReserved3(hzDerivativeMaterielDetail.getDmdReserved3());
        this.setDmdReserved4(hzDerivativeMaterielDetail.getDmdReserved4());
        this.setDmdReserved5(hzDerivativeMaterielDetail.getDmdReserved5());
        this.setDmdFeatureValue(hzDerivativeMaterielDetail.getDmdFeatureValue());
        this.setCfg0Record(hzDerivativeMaterielDetail.getCfg0Record());
    }
}
