package com.connor.hozon.bom.resources.service.change;

import cn.net.connor.hozon.common.entity.QueryBase;
import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;

import java.util.List;

@Configuration
public interface HzBreakPointService {
    /**
     * 查找当前页断点信息数据
     * @return
     */
    List<HzBreakPoint> selectAll(QueryBase queryBase);

    /**
     * 总断点信息总数量
     * @returns
     */
    Long doTellMeHowManyOfIt() ;
}
