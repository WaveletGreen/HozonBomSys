/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.breakPoint;

import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;
import cn.net.connor.hozon.dao.query.change.breakPoint.BreakPointQuery;

import java.util.List;

public interface HzBreakPointService {

    /**
     * 查找当前页断点信息数据
     * @param queryBase 包含分页查询，关键字查询等
     * @return
     */
    List<HzBreakPoint> selectByQueryObject(BreakPointQuery queryBase);

    /**
     * 总断点信息总数量
     * @returns
     */
    Long count() ;
}
