package sql.pojo.cfg.fullCfg;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;

import java.math.BigDecimal;
import java.util.Date;

public class HzFullCfgWithCfgChange {
    private Long id;

    //变更表单ID
    private Integer changeOrderId;

    //变更主表id
    private Integer mainID;

    private String cfgCfg0Uid;

    private String cfgBomlineUid;

    private Date flCfgCreateDate;

    private Date flCfgUpdateDate;

    private String flCfgCreator;

    private String flCfgUpdator;

    private Date flCfgCreateDateChange;

    private Date flCfgUpdateDateChange;

    private String flCfgCreatorChange;

    private String flCfgUpdatorChange;

    private String flCfgBomlineName;

    private Long flCfgVersion;

    private Integer flOperationType;

    private String flComment;

    private String cfgDesc;
    private String cfgCode;
    private String isColor;


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

    public String getCfgCfg0Uid() {
        return cfgCfg0Uid;
    }

    public void setCfgCfg0Uid(String cfgCfg0Uid) {
        this.cfgCfg0Uid = cfgCfg0Uid;
    }

    public String getCfgBomlineUid() {
        return cfgBomlineUid;
    }

    public void setCfgBomlineUid(String cfgBomlineUid) {
        this.cfgBomlineUid = cfgBomlineUid;
    }

    public Date getFlCfgCreateDate() {
        return flCfgCreateDate;
    }

    public void setFlCfgCreateDate(Date flCfgCreateDate) {
        this.flCfgCreateDate = flCfgCreateDate;
    }

    public Date getFlCfgUpdateDate() {
        return flCfgUpdateDate;
    }

    public void setFlCfgUpdateDate(Date flCfgUpdateDate) {
        this.flCfgUpdateDate = flCfgUpdateDate;
    }

    public String getFlCfgCreator() {
        return flCfgCreator;
    }

    public void setFlCfgCreator(String flCfgCreator) {
        this.flCfgCreator = flCfgCreator;
    }

    public String getFlCfgUpdator() {
        return flCfgUpdator;
    }

    public void setFlCfgUpdator(String flCfgUpdator) {
        this.flCfgUpdator = flCfgUpdator;
    }

    public Date getFlCfgCreateDateChange() {
        return flCfgCreateDateChange;
    }

    public void setFlCfgCreateDateChange(Date flCfgCreateDateChange) {
        this.flCfgCreateDateChange = flCfgCreateDateChange;
    }

    public Date getFlCfgUpdateDateChange() {
        return flCfgUpdateDateChange;
    }

    public void setFlCfgUpdateDateChange(Date flCfgUpdateDateChange) {
        this.flCfgUpdateDateChange = flCfgUpdateDateChange;
    }

    public String getFlCfgCreatorChange() {
        return flCfgCreatorChange;
    }

    public void setFlCfgCreatorChange(String flCfgCreatorChange) {
        this.flCfgCreatorChange = flCfgCreatorChange;
    }

    public String getFlCfgUpdatorChange() {
        return flCfgUpdatorChange;
    }

    public void setFlCfgUpdatorChange(String flCfgUpdatorChange) {
        this.flCfgUpdatorChange = flCfgUpdatorChange;
    }

    public String getFlCfgBomlineName() {
        return flCfgBomlineName;
    }

    public void setFlCfgBomlineName(String flCfgBomlineName) {
        this.flCfgBomlineName = flCfgBomlineName;
    }

    public Long getFlCfgVersion() {
        return flCfgVersion;
    }

    public void setFlCfgVersion(Long flCfgVersion) {
        this.flCfgVersion = flCfgVersion;
    }

    public Integer getFlOperationType() {
        return flOperationType;
    }

    public void setFlOperationType(Integer flOperationType) {
        this.flOperationType = flOperationType;
    }

    public String getFlComment() {
        return flComment;
    }

    public void setFlComment(String flComment) {
        this.flComment = flComment;
    }

    public String getCfgDesc() {
        return cfgDesc;
    }

    public void setCfgDesc(String cfgDesc) {
        this.cfgDesc = cfgDesc;
    }

    public String getCfgCode() {
        return cfgCode;
    }

    public void setCfgCode(String cfgCode) {
        this.cfgCode = cfgCode;
    }

    public String getIsColor() {
        return isColor;
    }

    public void setIsColor(String isColor) {
        this.isColor = isColor;
    }

    public void srcSetChange(HzFullCfgWithCfg hzFullCfgWithCfg) {
        Date date = new Date();
        User user = UserInfo.getUser();

        this.cfgCfg0Uid = hzFullCfgWithCfg.getCfgCfg0Uid();
        this.cfgBomlineUid = hzFullCfgWithCfg.getCfgBomlineUid();
        this.flCfgCreateDate = hzFullCfgWithCfg.getFlCfgCreateDate();
        this.flCfgUpdateDate = hzFullCfgWithCfg.getFlCfgUpdateDate();
        this.flCfgCreator = hzFullCfgWithCfg.getFlCfgCreator();
        this.flCfgUpdator = hzFullCfgWithCfg.getFlCfgUpdator();
        this.flCfgCreateDateChange = date;
        this.flCfgUpdateDateChange = date;
        this.flCfgCreatorChange = user.getLogin();
        this.flCfgUpdatorChange = user.getLogin();
        this.flCfgBomlineName = hzFullCfgWithCfg.getFlCfgBomlineName();
        this.flCfgVersion = hzFullCfgWithCfg.getFlCfgVersion();
        this.flOperationType = hzFullCfgWithCfg.getFlOperationType();
        this.flComment = hzFullCfgWithCfg.getFlComment();
    }

    public String getFlOperationTypeString(){
        switch (this.getFlOperationType()){
            case 1 : return "新增";
            case 2 : return  "跟新";
            case 3 : return  "删除";
            default: return  "未知状态";
        }
    }

    public String getFlCommentString(){
        if("1".equals(flComment)){
            return "标配";
        }else {
            return "选配";
        }
    }
}