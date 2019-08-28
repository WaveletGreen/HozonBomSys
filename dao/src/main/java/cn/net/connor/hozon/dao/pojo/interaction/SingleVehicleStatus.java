/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.interaction;

import lombok.Data;

@Data
public class SingleVehicleStatus {
    /**
     * 主键
     */
    private Long id;
    /**
     * 单车的主键
     */
    private Long svgId;
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 项目ID
     */
    private String projectId;
}