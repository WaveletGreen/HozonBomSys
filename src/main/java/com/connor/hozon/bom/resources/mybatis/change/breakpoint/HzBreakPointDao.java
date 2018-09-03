package com.connor.hozon.bom.resources.mybatis.change.breakpoint;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.integration.HzBreakPoint;

import java.util.List;

@Configuration
public interface HzBreakPointDao extends BasicDao<HzBreakPoint> {
    /**
     * 查找所有断点信息数据
     * @return
     */
    List<HzBreakPoint> selectAll();

}