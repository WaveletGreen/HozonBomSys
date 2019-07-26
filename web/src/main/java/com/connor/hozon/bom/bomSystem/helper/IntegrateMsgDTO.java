/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.helper;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Data
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

}
