package com.connor.hozon.bom.resources.service.change.impl;

import cn.net.connor.hozon.common.entity.QueryBase;
import com.connor.hozon.bom.resources.mybatis.change.breakpoint.HzBreakPointDao;
import com.connor.hozon.bom.resources.service.change.HzBreakPointService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;

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
    public List<HzBreakPoint> selectAll(QueryBase queryBase) {
        List<HzBreakPoint> list = new ArrayList<>();
        List<HzBreakPoint> breakPointList = breakPointDao.selectAll(queryBase);

        if(ListUtil.isNotEmpty(breakPointList)){
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
    public Long doTellMeHowManyOfIt() {
        return breakPointDao.count();
    }

}
