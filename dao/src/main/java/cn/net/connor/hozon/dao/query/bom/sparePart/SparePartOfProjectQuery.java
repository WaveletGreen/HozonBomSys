/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.bom.sparePart;

import cn.net.connor.hozon.common.entity.QueryBase;
import lombok.Data;
import lombok.ToString;

/**
 * 项目的备件查询对象
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:08
 * @Modified By:
 */
@Data
@ToString
public class SparePartOfProjectQuery extends QueryBase {
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 层级
     */
    private String level;
}
