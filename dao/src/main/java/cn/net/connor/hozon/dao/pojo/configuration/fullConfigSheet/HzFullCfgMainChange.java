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
public class HzFullCfgMainChange {
    private Integer id;
    //变更表单ID
    private Integer changeOrderId;
    //项目外键
    private String projectUid;
    //状态
    private Integer status;
    //阶段
    private Integer stage;
    //版本
    private String version;
    //生效时间
    private Date effectiveDate;
    //源数据创建时间
    private Date createDate;
    //源数据更新时间
    private Date updateDate;
    //源数据创建人
    private String creator;
    //源数据更新人
    private String updater;
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
    //源主数据ID
    private Long srcMainId;
    //变更状态
    private Integer changeStatus;



    public void srcSetChange(HzFullCfgMain hzFullCfgMain,String username){
        Date date = new Date();


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
        this.creatorChange = username;
        this.updaterChange = username;
        this.fmIsRelease = hzFullCfgMain.getFmIsRelease();
        this.srcMainId = hzFullCfgMain.getId();
    }


    public String getStageString(){
        if(this.stage==null){
            return "";
        }
        switch (this.stage){
            case 1 :
                return "P0-P1阶段";
            case 2 :
                return "P1-P2阶段";
            case 3 :
                return "P2-P3阶段";
            case 4 :
                return "P3-P4阶段";
            case 5 :
                return "P4-P5阶段";
            case 6 :
                return "P5-P6阶段";
            case 7 :
                return "P6-P7阶段";
            case 8 :
                return "P7-P8阶段";
            case 9 :
                return "P8-P9阶段";
            case 10 :
                return "P9-P10阶段";
                default:
                    return "";
        }
    }

    public HzFullCfgMain geteHzFullCfgMain() {
        HzFullCfgMain hzFullCfgMain = new HzFullCfgMain();
        hzFullCfgMain.setProjectUid(this.getProjectUid());
        hzFullCfgMain.setStatus(0);
        hzFullCfgMain.setStage(this.getStage());
        hzFullCfgMain.setVersion(this.getVersion());
        hzFullCfgMain.setEffectiveDate(this.getEffectiveDate());
        hzFullCfgMain.setCreateDate(this.getCreateDate());
        hzFullCfgMain.setUpdateDate(this.getUpdateDate());
        hzFullCfgMain.setCreator(this.getCreator());
        hzFullCfgMain.setUpdater(this.getUpdater());
        hzFullCfgMain.setFmIsRelease(this.getFmIsRelease());
        return hzFullCfgMain;
    }
}