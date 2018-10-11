package sql.pojo.cfg;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 11:23
 */
public class HzCfg0ModelColor {
    /**
     * 模型主键
     */
    private String puid;

    private String pCfg0MainRecordOfMC;
    private String pCodeOfColorfulModel;
    private String pDescOfColorfulModel;
    /**
     * 油漆车身总成
     */
    private String pModelShellOfColorfulModel;
    private byte[] pColorfulMapBlock;
    private LinkedHashMap<String, String> mapOfCfg0;
    //是否多色
    private String pColorIsMultiply;
    /**
     * 作为更新旧数据的标识，为0/null的都是旧数据，需要进行更新
     */
    private Integer updateDefault;
    /**
     * 颜色UID
     */
    private String pColorUid;

    private String pShellCfg0Puid;


    //VWO变更号
    private Long cmcrVwoId;
    //创建时间
    private Date cmcrCreateDate;
    //创建人
    private String cmcrCreator;
    //跟新时间
    private Date cmcrUpdateDate;
    //跟新人
    private String cmcrUpdater;
    //保留字段1-5
    private String cmcrReserve1;
    private String cmcrReserve2;
    private String cmcrReserve3;
    private String cmcrReserve4;
    private String cmcrReserve5;


    public HzCfg0ModelColor() {
        mapOfCfg0 = new LinkedHashMap<>();
    }



    public String getpModelShellOfColorfulModel() {
        return pModelShellOfColorfulModel;
    }

    public void setpModelShellOfColorfulModel(String pModelShellOfColorfulModel) { this.pModelShellOfColorfulModel = pModelShellOfColorfulModel; }

    public String getpCfg0MainRecordOfMC() {
        return pCfg0MainRecordOfMC;
    }

    public void setpCfg0MainRecordOfMC(String pCfg0MainRecordOfMC) {
        this.pCfg0MainRecordOfMC = pCfg0MainRecordOfMC;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpCodeOfColorfulModel() {
        return pCodeOfColorfulModel;
    }

    public void setpCodeOfColorfulModel(String pCodeOfColorfulModel) { this.pCodeOfColorfulModel = pCodeOfColorfulModel; }

    public String getpDescOfColorfulModel() {
        return pDescOfColorfulModel;
    }

    public void setpDescOfColorfulModel(String pDescOfColorfulModel) { this.pDescOfColorfulModel = pDescOfColorfulModel; }

    public byte[] getpColorfulMapBlock() { return pColorfulMapBlock; }

    public void setpColorfulMapBlock(byte[] pColorfulMapBlock) { this.pColorfulMapBlock = pColorfulMapBlock; }

    public LinkedHashMap<String, String> getMapOfCfg0() {
        return mapOfCfg0;
    }

    public void setMapOfCfg0(LinkedHashMap<String, String> mapOfCfg0) {
        this.mapOfCfg0 = mapOfCfg0;
    }

    public String getpColorIsMultiply() {
        return pColorIsMultiply;
    }

    public void setpColorIsMultiply(String pColorIsMultiply) {
        this.pColorIsMultiply = pColorIsMultiply;
    }

    public Integer getUpdateDefault() {
        return updateDefault;
    }

    public void setUpdateDefault(Integer updateDefault) {
        this.updateDefault = updateDefault;
    }

    public String getpColorUid() {
        return pColorUid;
    }

    public void setpColorUid(String pColorUid) {
        this.pColorUid = pColorUid;
    }

    public String getpShellCfg0Puid() {
        return pShellCfg0Puid;
    }

    public void setpShellCfg0Puid(String pShellCfg0Puid) { this.pShellCfg0Puid = pShellCfg0Puid; }

    public Long getCmcrVwoId() { return cmcrVwoId; }

    public void setCmcrVwoId(Long cmcrVwoId) { this.cmcrVwoId = cmcrVwoId; }

    public Date getCmcrCreateDate() { return cmcrCreateDate; }

    public void setCmcrCreateDate(Date cmcrCreateDate) { this.cmcrCreateDate = cmcrCreateDate; }

    public String getCmcrCreator() { return cmcrCreator; }

    public void setCmcrCreator(String cmcrCreator) { this.cmcrCreator = cmcrCreator; }

    public Date getCmcrUpdateDate() { return cmcrUpdateDate; }

    public void setCmcrUpdateDate(Date cmcrUpdateDate) { this.cmcrUpdateDate = cmcrUpdateDate; }

    public String getCmcrUpdater() { return cmcrUpdater; }

    public void setCmcrUpdater(String cmcrUpdater) { this.cmcrUpdater = cmcrUpdater; }

    public String getCmcrReserve1() { return cmcrReserve1; }

    public void setCmcrReserve1(String cmcrReserve1) { this.cmcrReserve1 = cmcrReserve1; }

    public String getCmcrReserve2() { return cmcrReserve2; }

    public void setCmcrReserve2(String cmcrReserve2) { this.cmcrReserve2 = cmcrReserve2; }

    public String getCmcrReserve3() { return cmcrReserve3; }

    public void setCmcrReserve3(String cmcrReserve3) { this.cmcrReserve3 = cmcrReserve3; }

    public String getCmcrReserve4() { return cmcrReserve4; }

    public void setCmcrReserve4(String cmcrReserve4) { this.cmcrReserve4 = cmcrReserve4; }

    public String getCmcrReserve5() { return cmcrReserve5; }

    public void setCmcrReserve5(String cmcrReserve5) { this.cmcrReserve5 = cmcrReserve5; }
}
