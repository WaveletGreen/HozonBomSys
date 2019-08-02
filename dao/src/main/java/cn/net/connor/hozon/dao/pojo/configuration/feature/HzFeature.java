/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.feature;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性表，每个项目都存储一些特性数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzFeature {
    /**
     * 主键
     */
    private String id;
    /**
     * 主配置外键，可从项目UID获取到
     */
    private String mainConfigUid;
    /**
     * 特性代码
     */
    private String featureCode;
    /**
     * 特性描述
     */
    private String featureDesc;

    /**
     * 配置字典的主键
     */
    private String dicLibUid;

}