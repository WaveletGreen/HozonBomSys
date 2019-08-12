/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.configuration.relevance;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性查询助手
 * @Date: Created in 2018/9/7 15:16
 * @Modified By:
 */
@Data
public class HzRelevanceQueryResult {
    /**
     * 主键
     */
    private Long id;
    /**
     * 序号
     */
    private Integer index;
    /**
     * 特性值
     */
    private String rbFeatureValueCode;
    /**
     * 相关性
     */
    private String relevance;
    /**
     * 相关性描述
     */
    private String relevanceDesc;
    /**
     * 相关性代码
     */
    private String relevanceCode;
    /**
     * 相关性状态
     */
    private Integer status;
    /**
     * 生效时间
     */
    private Date effectedDate;

}
