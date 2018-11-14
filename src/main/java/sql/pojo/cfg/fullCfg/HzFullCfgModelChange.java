package sql.pojo.cfg.fullCfg;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgModelChange {
    private BigDecimal id;

    //变更表单ID
    private Integer changeOrderId;

    //变更主表id
    private Integer mainID;

    private String modModelUid;

    private String modCfg0Uid;

    private Short modPointType;

    private Date flModCreateDate;

    private Date flModUpdateDate;

    private Object flModCreator;

    private Object flModLastUpdater;

    private Date flModCreateDateChange;

    private Date flModUpdateDateChange;

    private Object flModCreatorChange;

    private Object flModLastUpdaterChange;

    private BigDecimal flModVersion;

    private String flModelBomlineUid;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Integer getChangeOrderId() {
        return changeOrderId;
    }

    public void setChangeOrderId(Integer changeOrderId) {
        this.changeOrderId = changeOrderId;
    }

    public Integer getMainID() {
        return mainID;
    }

    public void setMainID(Integer mainID) {
        this.mainID = mainID;
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

    public Date getFlModCreateDateChange() {
        return flModCreateDateChange;
    }

    public void setFlModCreateDateChange(Date flModCreateDateChange) {
        this.flModCreateDateChange = flModCreateDateChange;
    }

    public Date getFlModUpdateDateChange() {
        return flModUpdateDateChange;
    }

    public void setFlModUpdateDateChange(Date flModUpdateDateChange) {
        this.flModUpdateDateChange = flModUpdateDateChange;
    }

    public Object getFlModCreatorChange() {
        return flModCreatorChange;
    }

    public void setFlModCreatorChange(Object flModCreatorChange) {
        this.flModCreatorChange = flModCreatorChange;
    }

    public Object getFlModLastUpdaterChange() {
        return flModLastUpdaterChange;
    }

    public void setFlModLastUpdaterChange(Object flModLastUpdaterChange) {
        this.flModLastUpdaterChange = flModLastUpdaterChange;
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
        this.flModelBomlineUid = flModelBomlineUid == null ? null : flModelBomlineUid.trim();
    }

    public void srcSetChange(HzFullCfgModel hzFullCfgModel) {
        Date date = new Date();
        User user = UserInfo.getUser();

        this.modModelUid = hzFullCfgModel.getModModelUid();
        this.modCfg0Uid = hzFullCfgModel.getModCfg0Uid();
        this.modPointType = hzFullCfgModel.getModPointType();
        this.flModCreateDate = hzFullCfgModel.getFlModCreateDate();
        this.flModUpdateDate = hzFullCfgModel.getFlModUpdateDate();
        this.flModCreator = hzFullCfgModel.getFlModCreator();
        this.flModLastUpdater = hzFullCfgModel.getFlModLastUpdater();
        this.flModCreateDateChange = date;
        this.flModUpdateDateChange = date;
        this.flModCreatorChange = user.getLogin();
        this.flModLastUpdaterChange = user.getLogin();
        this.flModVersion = hzFullCfgModel.getFlModVersion();
        this.flModelBomlineUid = hzFullCfgModel.getFlModelBomlineUid();
    }


}