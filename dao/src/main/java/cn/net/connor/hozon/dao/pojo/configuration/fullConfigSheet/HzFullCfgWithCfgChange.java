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

    public void srcSetChange(HzFullCfgWithCfg hzFullCfgWithCfg,String username) {
        Date date = new Date();

        this.cfgCfg0Uid = hzFullCfgWithCfg.getCfgCfg0Uid();
        this.cfgBomlineUid = hzFullCfgWithCfg.getCfgBomlineUid();
        this.flCfgCreateDate = hzFullCfgWithCfg.getFlCfgCreateDate();
        this.flCfgUpdateDate = hzFullCfgWithCfg.getFlCfgUpdateDate();
        this.flCfgCreator = hzFullCfgWithCfg.getFlCfgCreator();
        this.flCfgUpdator = hzFullCfgWithCfg.getFlCfgUpdator();
        this.flCfgCreateDateChange = date;
        this.flCfgUpdateDateChange = date;
        this.flCfgCreatorChange = username;
        this.flCfgUpdatorChange = username;
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

    public HzFullCfgWithCfg getHzFullCfgWithCfg(Long mainId) {
        HzFullCfgWithCfg hzFullCfgWithCfg = new HzFullCfgWithCfg();
        hzFullCfgWithCfg.setCfgCfg0Uid(this.getCfgCfg0Uid());
        hzFullCfgWithCfg.setCfgBomlineUid(this.getCfgBomlineUid());
        hzFullCfgWithCfg.setFlCfgCreateDate(this.getFlCfgCreateDate());
        hzFullCfgWithCfg.setFlCfgUpdateDate(this.getFlCfgUpdateDate());
        hzFullCfgWithCfg.setFlCfgCreator(this.getFlCfgCreator());
        hzFullCfgWithCfg.setFlCfgUpdator(this.getFlCfgUpdator());
        hzFullCfgWithCfg.setFlCfgBomlineName(this.getFlCfgBomlineName());
        hzFullCfgWithCfg.setFlCfgVersion(mainId);
        hzFullCfgWithCfg.setFlOperationType(this.getFlOperationType());
        hzFullCfgWithCfg.setFlComment(this.getFlComment());
        return hzFullCfgWithCfg;
    }
}