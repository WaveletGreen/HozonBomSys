package sql.pojo.cfg.fullCfg;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;

import java.util.Date;

public class HzFullCfgModelChange {
    private Long id;

    //变更表单ID
    private Integer changeOrderId;

    //变更主表id
    private Integer mainID;

    private String modModelUid;

    private String modCfg0Uid;

    private Short modPointType;

    private Date flModCreateDate;

    private Date flModUpdateDate;

    private String flModCreator;

    private String flModLastUpdater;

    private Date flModCreateDateChange;

    private Date flModUpdateDateChange;

    private Object flModCreatorChange;

    private Object flModLastUpdaterChange;

    private Long flModVersion;

    private String flModelBomlineUid;

    //车辆模型详情字段
    private String modelVersion;
    private String modelShape;
    private String modelAnnouncement;
    private String modelCfgDesc;
    private String modelCfgMng;
    private String modelTrailNum;
    private String modelGoodsNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getFlModVersion() {
        return flModVersion;
    }

    public void setFlModVersion(Long flModVersion) {
        this.flModVersion = flModVersion;
    }

    public String getFlModelBomlineUid() {
        return flModelBomlineUid;
    }

    public void setFlModelBomlineUid(String flModelBomlineUid) {
        this.flModelBomlineUid = flModelBomlineUid == null ? null : flModelBomlineUid.trim();
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getModelShape() {
        return modelShape;
    }

    public void setModelShape(String modelShape) {
        this.modelShape = modelShape;
    }

    public String getModelAnnouncement() {
        return modelAnnouncement;
    }

    public void setModelAnnouncement(String modelAnnouncement) {
        this.modelAnnouncement = modelAnnouncement;
    }

    public String getModelCfgDesc() {
        return modelCfgDesc;
    }

    public void setModelCfgDesc(String modelCfgDesc) {
        this.modelCfgDesc = modelCfgDesc;
    }

    public String getModelCfgMng() {
        return modelCfgMng;
    }

    public void setModelCfgMng(String modelCfgMng) {
        this.modelCfgMng = modelCfgMng;
    }

    public String getModelTrailNum() {
        return modelTrailNum;
    }

    public void setModelTrailNum(String modelTrailNum) {
        this.modelTrailNum = modelTrailNum;
    }

    public String getModelGoodsNum() {
        return modelGoodsNum;
    }

    public void setModelGoodsNum(String modelGoodsNum) {
        this.modelGoodsNum = modelGoodsNum;
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


    public HzFullCfgModel getHzFullCfgModel(Long mainId) {
        HzFullCfgModel hzFullCfgModel = new HzFullCfgModel();
        hzFullCfgModel.setModModelUid(this.getModModelUid());
        hzFullCfgModel.setModCfg0Uid(this.getModCfg0Uid());
        hzFullCfgModel.setModPointType(this.getModPointType());
        hzFullCfgModel.setFlModCreateDate(this.getFlModCreateDate());
        hzFullCfgModel.setFlModUpdateDate(this.getFlModUpdateDate());
        hzFullCfgModel.setFlModCreator(this.getFlModCreator());
        hzFullCfgModel.setFlModLastUpdater(this.getFlModLastUpdater());
        hzFullCfgModel.setFlModVersion(mainId);
        hzFullCfgModel.setFlModelBomlineUid(this.getFlModelBomlineUid());
        return hzFullCfgModel;
    }
}