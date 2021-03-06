/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.integration;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Data
public class IntegrateMsgResponseDTO implements Serializable {
    private static final long serialVersionUID = -666971277914055001L;
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
