/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.derivative;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 模型组，同步到SAP用的模型组，在BOM系统中没有与之对应的操作入口
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzCfg0ModelGroup {
    private String id;

    private String tcUid;

    private Object groupDesc;

    private Object groupName;

    private String mainUid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTcUid() {
        return tcUid;
    }

    public void setTcUid(String tcUid) {
        this.tcUid = tcUid == null ? null : tcUid.trim();
    }

    public Object getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(Object groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public String getMainUid() {
        return mainUid;
    }

    public void setMainUid(String mainUid) {
        this.mainUid = mainUid == null ? null : mainUid.trim();
    }
}