/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.query;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
@Data
public class HzEPLQuery extends DefaultQuery {
    /**
     * 零件号
     */
    private String partId;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 项目id
     */
    private String projectId;
}
