/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.helper;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 狗日的
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class IntegrateMsgDTO {
    /**
     * 从SAP传回的消息
     */
    private String msg;
    /**
     * 对应数据库的puid
     */
    private String puid;
    /**
     * item_id
     *
     */
    private String itemId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
