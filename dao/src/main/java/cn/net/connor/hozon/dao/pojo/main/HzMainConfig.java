/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.main;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配置管理主数据，即主配置对象
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzMainConfig {
    /**
     * 主键
     */
    private String id;
    /**
     * 主配置名称
     */
    private String itemName;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Object creator;
    /**
     * 原本在TC中的配置名称
     */
    private String configUidFromTC;
    /**
     * 所属项目名
     */
    private String projectName;
    /**
     * 所属项目ID
     */
    private String projectId;
    /**
     * 最后一次更新时间
     */
    private Date lastModifyDate;
    /**
     * 最后一次更新人
     */
    private String updater;
    /**
     * 特性值是否已经从配置字典进行同步
     */
    private Integer featureSynDicFlag;

}