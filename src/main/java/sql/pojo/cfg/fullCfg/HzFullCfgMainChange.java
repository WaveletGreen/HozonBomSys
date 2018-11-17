package sql.pojo.cfg.fullCfg;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgMainChange {
    private Integer id;
    //变更表单ID
    private Integer changeOrderId;
    //项目外键
    private String projectUid;
    //状态
    private Object status;
    //阶段
    private Integer stage;
    //版本
    private Object version;
    //生效时间
    private Date effectiveDate;
    //源数据创建时间
    private Date createDate;
    //源数据更新时间
    private Date updateDate;
    //源数据创建人
    private Object creator;
    //源数据更新人
    private Object updater;
    //创建时间
    private Date createDateChange;
    //更新时间
    private Date updateDateChange;
    //创建人
    private Object creatorChange;
    //更新人
    private Object updaterChange;
    //是否已发布
    private Integer fmIsRelease;

    public Integer getId() {
        return id;
    }

    public Integer getChangeOrderId() {
        return changeOrderId;
    }

    public void setChangeOrderId(Integer changeOrderId) {
        this.changeOrderId = changeOrderId;
    }

    public void setId(Integer id) {
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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
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

    public Date getCreateDateChange() {
        return createDateChange;
    }

    public void setCreateDateChange(Date createDateChange) {
        this.createDateChange = createDateChange;
    }

    public Date getUpdateDateChange() {
        return updateDateChange;
    }

    public void setUpdateDateChange(Date updateDateChange) {
        this.updateDateChange = updateDateChange;
    }

    public Object getCreatorChange() {
        return creatorChange;
    }

    public void setCreatorChange(Object creatorChange) {
        this.creatorChange = creatorChange;
    }

    public Object getUpdaterChange() {
        return updaterChange;
    }

    public void setUpdaterChange(Object updaterChange) {
        this.updaterChange = updaterChange;
    }

    public Integer getFmIsRelease() {
        return fmIsRelease;
    }

    public void setFmIsRelease(Integer fmIsRelease) {
        this.fmIsRelease = fmIsRelease;
    }


    public void srcSetChange(HzFullCfgMain hzFullCfgMain){
        Date date = new Date();
        User user = UserInfo.getUser();

        this.projectUid = hzFullCfgMain.getProjectUid();
        this.status = hzFullCfgMain.getStatus();
        this.stage = hzFullCfgMain.getStage();
        this.version = hzFullCfgMain.getVersion();
        this.effectiveDate = hzFullCfgMain.getEffectiveDate();
        this.createDate = hzFullCfgMain.getCreateDate();
        this.updateDate = hzFullCfgMain.getUpdateDate();
        this.creator = hzFullCfgMain.getCreator();
        this.updater = hzFullCfgMain.getUpdater();
        this.createDateChange = date;
        this.updateDateChange = date;
        this.creatorChange = user.getLogin();
        this.updaterChange = user.getLogin();
        this.fmIsRelease = hzFullCfgMain.getFmIsRelease();
    }
}