/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.breakPoint.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.dao.dao.change.breakpoint.HzBreakPointDao;
import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;
import cn.net.connor.hozon.dao.query.change.breakPoint.BreakPointQuery;
import com.connor.hozon.service.change.breakPoint.HzBreakPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service("hzBreakPointService")
@Service
public class HzBreakPointServiceImpl implements HzBreakPointService {
    @Autowired
    private HzBreakPointDao breakPointDao;

    /**
     * 查询当前页的断点信息
     * @param queryBase
     * @return
     */
    @Override
    public List<HzBreakPoint> selectByQueryObject(BreakPointQuery queryBase) {
        List<HzBreakPoint> list = new ArrayList<>();
        List<HzBreakPoint> breakPointList = breakPointDao.selectByQueryObject(queryBase);

        if(ListUtils.isNotEmpty(breakPointList)){
            breakPointList.forEach(breakPoint -> {
                HzBreakPoint point = breakPoint;
                list.add(point);
            });
        }
        return list;
    }

    /**
     * 查询断点信息总数量
     * @return
     */
    @Override
    public Long count() {
        return breakPointDao.count();
    }

}
