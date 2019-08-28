package com.connor.hozon.resources.service.change.impl;

import cn.net.connor.hozon.dao.dao.change.breakpoint.HzBreakPointDao;
import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;
import cn.net.connor.hozon.dao.query.change.breakPoint.BreakPointQuery;
import com.connor.hozon.resources.service.change.HzBreakPointService;
import cn.net.connor.hozon.common.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Service("hzBreakPointService")
@Configuration
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
