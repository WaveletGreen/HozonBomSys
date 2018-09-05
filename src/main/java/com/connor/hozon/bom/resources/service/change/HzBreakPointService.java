package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.context.annotation.Configuration;
import sql.pojo.integration.HzBreakPoint;

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
