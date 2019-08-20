/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.request.bom.sparePart;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import lombok.Data;
import lombok.ToString;

/**
 * 项目的备件前端需求的查询对象，直接与{@link SparePartOfProjectQuery}对象有关系
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:08
 * @Modified By:
 */
@Data
@ToString
public class SparePartOfProjectRequestQueryDTO extends SparePartOfProjectQuery {
}
