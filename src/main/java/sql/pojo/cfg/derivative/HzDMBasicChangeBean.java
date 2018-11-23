package sql.pojo.cfg.derivative;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;

import java.util.Date;

public class HzDMBasicChangeBean {

    /**
     * 主键
     */
    private Long id;
    /**
     * 变更表单主键
     */
    private Long formId;
    /**
     * 源数据ID
     */
    private Long dmbSrcId;
    /**
     * 基础模型外键
     */
    private String dmbModelUid;
    /**
     * 颜色车型外键
     */
    private String dmbColorModelUid;
    /**
     * 源数据创建者
     */
    private String dmbCreator;
    /**
     * 源数据创建日期
     */
    private Date dmbCreateDate;
    /**
     * 源数据更新者
     */
    private String dmbUpdater;
    /**
     * 源数据更新日期
     */
    private Date dmbUpdateDate;
    /**
     * 创建者
     */
    private String changeCreator;
    /**
     * 创建日期
     */
    private Date changeCreateDate;
    /**
     * 更新者
     */
    private String changeUpdater;
    /**
     * 更新日期
     */
    private Date changeUpdateDate;
    /**
     * 项目UID
     */
    private String dmbProjectUid;

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

    /**
     * 衍生物料的UID
     */
    private String dmbModelFeatureUid;

    /**
     * 特殊特性UID，特别是“车身颜色”
     */
    private String dmbSpecialFeatureUid;
    /**
     * 变更状态，0为流程中，1为生效
     */
    private Integer dmbChangeStatus;

    /**
     * 源数据状态
     */
    private Integer dmbSrcStatus;

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

    public String getDmbModelUid() {
        return dmbModelUid;
    }

    public void setDmbModelUid(String dmbModelUid) {
        this.dmbModelUid = dmbModelUid;
    }

    public String getDmbColorModelUid() {
        return dmbColorModelUid;
    }

    public void setDmbColorModelUid(String dmbColorModelUid) {
        this.dmbColorModelUid = dmbColorModelUid;
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

    public String getDmbProjectUid() {
        return dmbProjectUid;
    }

    public void setDmbProjectUid(String dmbProjectUid) {
        this.dmbProjectUid = dmbProjectUid;
    }

    public String getDmbReserved2() {
        return dmbReserved2;
    }

    public void setDmbReserved2(String dmbReserved2) {
        this.dmbReserved2 = dmbReserved2;
    }

    public String getDmbReserved3() {
        return dmbReserved3;
    }

    public void setDmbReserved3(String dmbReserved3) {
        this.dmbReserved3 = dmbReserved3;
    }

    public String getDmbReserved4() {
        return dmbReserved4;
    }

    public void setDmbReserved4(String dmbReserved4) {
        this.dmbReserved4 = dmbReserved4;
    }

    public String getDmbReserved5() {
        return dmbReserved5;
    }

    public void setDmbReserved5(String dmbReserved5) {
        this.dmbReserved5 = dmbReserved5;
    }

    public String getDmbModelFeatureUid() {
        return dmbModelFeatureUid;
    }

    public void setDmbModelFeatureUid(String dmbModelFeatureUid) {
        this.dmbModelFeatureUid = dmbModelFeatureUid;
    }

    public String getDmbSpecialFeatureUid() {
        return dmbSpecialFeatureUid;
    }

    public void setDmbSpecialFeatureUid(String dmbSpecialFeatureUid) {
        this.dmbSpecialFeatureUid = dmbSpecialFeatureUid;
    }

    public Long getDmbSrcId() {
        return dmbSrcId;
    }

    public void setDmbSrcId(Long dmbSrcId) {
        this.dmbSrcId = dmbSrcId;
    }

    public Integer getDmbChangeStatus() {
        return dmbChangeStatus;
    }

    public void setDmbChangeStatus(Integer dmbChangeStatus) {
        this.dmbChangeStatus = dmbChangeStatus;
    }

    public Integer getDmbSrcStatus() {
        return dmbSrcStatus;
    }

    public void setDmbSrcStatus(Integer dmbSrcStatus) {
        this.dmbSrcStatus = dmbSrcStatus;
    }

    public void srcSetChange(HzDerivativeMaterielBasic hzDerivativeMaterielBasic){
        Date date = new Date();
        User user = UserInfo.getUser();
        this.setDmbSrcId(hzDerivativeMaterielBasic.getId());
        this.setDmbModelUid(hzDerivativeMaterielBasic.getDmbModelUid());
        this.setDmbColorModelUid(hzDerivativeMaterielBasic.getDmbColorModelUid());
        this.setDmbCreator(hzDerivativeMaterielBasic.getDmbCreator());
        this.setDmbCreateDate(hzDerivativeMaterielBasic.getDmbCreateDate());
        this.setDmbUpdater(hzDerivativeMaterielBasic.getDmbUpdater());
        this.setDmbUpdateDate(hzDerivativeMaterielBasic.getDmbUpdateDate());
        this.setChangeCreator(user.getLogin());
        this.setChangeCreateDate(date);
        this.setChangeUpdater(user.getLogin());
        this.setChangeUpdateDate(date);
        this.setDmbProjectUid(hzDerivativeMaterielBasic.getDmbProjectUid());
        this.setDmbReserved2(hzDerivativeMaterielBasic.getDmbReserved2());
        this.setDmbReserved3(hzDerivativeMaterielBasic.getDmbReserved3());
        this.setDmbReserved4(hzDerivativeMaterielBasic.getDmbReserved4());
        this.setDmbReserved5(hzDerivativeMaterielBasic.getDmbReserved5());
        this.setDmbModelFeatureUid(hzDerivativeMaterielBasic.getDmbModelFeatureUid());
        this.setDmbSpecialFeatureUid(hzDerivativeMaterielBasic.getDmbSpecialFeatureUid());
        this.setDmbSrcStatus(hzDerivativeMaterielBasic.getDmbStatus());
    }

    public HzDerivativeMaterielBasic getHzDerivativeMaterielBasic() {
        HzDerivativeMaterielBasic hzDerivativeMaterielBasic = new HzDerivativeMaterielBasic();
        hzDerivativeMaterielBasic.setId(this.getDmbSrcId());
        hzDerivativeMaterielBasic.setDmbModelUid(this.getDmbModelUid());
        hzDerivativeMaterielBasic.setDmbColorModelUid(this.getDmbColorModelUid());
        hzDerivativeMaterielBasic.setDmbSpecialFeatureUid(this.getDmbSpecialFeatureUid());
        hzDerivativeMaterielBasic.setDmbModelFeatureUid(this.getDmbModelFeatureUid());
        return hzDerivativeMaterielBasic;
    }
}
