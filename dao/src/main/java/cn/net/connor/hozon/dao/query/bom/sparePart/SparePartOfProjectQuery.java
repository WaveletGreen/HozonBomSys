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
 * 项目的备件查询对象，这一层对象属于dao层
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
    protected String projectId;
    /**
     * 层级
     */
    protected String hierarchy;
    /**
     * 生产零件号
     */
    protected String productivePartCode;
    /**
     * 备件零件号
     */
    protected String sparePartCode;
}
