/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet;

import lombok.Data;

import java.util.Date;
@Data
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



    public void srcSetChange(HzFullCfgModel hzFullCfgModel,String username) {
        Date date = new Date();

        this.modModelUid = hzFullCfgModel.getModModelUid();
        this.modCfg0Uid = hzFullCfgModel.getModCfg0Uid();
        this.modPointType = hzFullCfgModel.getModPointType();
        this.flModCreateDate = hzFullCfgModel.getFlModCreateDate();
        this.flModUpdateDate = hzFullCfgModel.getFlModUpdateDate();
        this.flModCreator = hzFullCfgModel.getFlModCreator();
        this.flModLastUpdater = hzFullCfgModel.getFlModLastUpdater();
        this.flModCreateDateChange = date;
        this.flModUpdateDateChange = date;
        this.flModCreatorChange = username;
        this.flModLastUpdaterChange = username;
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