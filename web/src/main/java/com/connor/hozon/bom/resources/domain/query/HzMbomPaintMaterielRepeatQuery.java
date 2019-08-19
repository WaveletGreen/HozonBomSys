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
 * @Date: 2018/12/5
 * @Description:
 */
@Data
public class HzMbomPaintMaterielRepeatQuery extends DefaultQuery{
    private String projectId;

    private String colorId;

    private String parentId;

    private String lineId;

}
