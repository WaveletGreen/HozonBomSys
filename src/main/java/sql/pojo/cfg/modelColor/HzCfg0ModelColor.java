/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.modelColor;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配色方案主数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
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
    @Getter
    @Setter
    //VWO变更号
    private Long cmcrVwoId;
    //创建时间
    @Getter
    @Setter
    private Date cmcrCreateDate;
    //创建人
    @Getter
    @Setter
    private String cmcrCreator;
    //跟新时间
    @Getter
    @Setter
    private Date cmcrUpdateDate;
    //跟新人
    @Getter
    @Setter
    private String cmcrUpdater;
    //保留字段1-5
    @Getter
    @Setter
    private String cmcrReserve1;
    @Getter
    @Setter
    private String cmcrReserve2;
    @Getter
    @Setter
    private String cmcrReserve3;
    @Getter
    @Setter
    private String cmcrReserve4;
    @Getter
    @Setter
    private String cmcrStatus;

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
}
