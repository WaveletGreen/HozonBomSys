/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.sparePart;

import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:15
 * @Modified By:
 */
public interface SparePartsBomService {
    SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectQuery query);
}
