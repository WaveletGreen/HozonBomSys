/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.modelColor;

import lombok.Data;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配色方案主数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
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

    //VWO变更id
    private Long cmcrVwoId;

    //VWO变更号
    private String cmcrVwoNum;
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
    /**
     * 流程状态
     */

    private String cmcrStatus;

    private Date cmcrEffectedDate;

    /**
     * 颜色名称
     */

    private String colorName;
    /**
     * 这个是级联查询出来的颜色库的UID，有一些历史数据没有存储颜色可的UID
     */
    private String orgColorUid;

    public HzCfg0ModelColor() {
        mapOfCfg0 = new LinkedHashMap<>();
    }

    public String getpModelShellOfColorfulModel() {
        return pModelShellOfColorfulModel;
    }

    public void setpModelShellOfColorfulModel(String pModelShellOfColorfulModel) {
        this.pModelShellOfColorfulModel = pModelShellOfColorfulModel;
    }


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

    public void setpCodeOfColorfulModel(String pCodeOfColorfulModel) {
        this.pCodeOfColorfulModel = pCodeOfColorfulModel;
    }

    public String getpDescOfColorfulModel() {
        return pDescOfColorfulModel;
    }

    public void setpDescOfColorfulModel(String pDescOfColorfulModel) {
        this.pDescOfColorfulModel = pDescOfColorfulModel;
    }

    public byte[] getpColorfulMapBlock() {
        return pColorfulMapBlock;
    }

    public void setpColorfulMapBlock(byte[] pColorfulMapBlock) {
        this.pColorfulMapBlock = pColorfulMapBlock;
    }

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

    public void setpShellCfg0Puid(String pShellCfg0Puid) {
        this.pShellCfg0Puid = pShellCfg0Puid;
    }

    public Date getCmcrEffectedDate() {
        return cmcrEffectedDate;
    }

    public void setCmcrEffectedDate(Date cmcrEffectedDate) {
        this.cmcrEffectedDate = cmcrEffectedDate;
    }
}
