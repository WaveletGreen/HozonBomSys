/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.bom;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/10/16
 * @Description:单车用量
 */
@Data
public class HzSingleVehicleDosage {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * bomLine puid
     */
    private String eBomPuid;
    /**
     * 车型信息puid
     */
    private String cfg0ModelPuid;

    /**
     * 单车用量
     */
    private String dosage;

    private Date createTime;

    private Date updateTime;

    private String createName;

    private String updateName;

    /**
     * 项目信息
     */
    private String projectId;
}
