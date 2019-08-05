package com.connor.hozon.bom.resources.mybatis.change.breakpoint;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.common.entity.QueryBase;
import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;

import java.util.List;

@Configuration
public interface HzBreakPointDao extends BasicDao<HzBreakPoint> {
    /**
     * 查找当前页断点信息数据
     * @return
     */
    List<HzBreakPoint> selectAll( QueryBase queryBase);

    /**
     * 查找所有断点信息总数据
     * @return
     */
    Long count();
}