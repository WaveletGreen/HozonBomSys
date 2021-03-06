/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/16
 * @Description: 变更需要的字段
 */
@Data
public abstract class BaseChangeDO extends BaseDO {
    /**
     * 变更表单id
     */
    private Long orderId;

    /**
     * 生效时间
     */
    private Date effectTime;
    /**
     * 版本
     */
    private String revision;

    /**
     * 对应操作的表名
     */
    private String tableName;
}
