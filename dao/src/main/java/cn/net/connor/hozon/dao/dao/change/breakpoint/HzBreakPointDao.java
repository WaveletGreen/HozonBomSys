package cn.net.connor.hozon.dao.dao.change.breakpoint;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;
import cn.net.connor.hozon.dao.query.change.breakPoint.BreakPointQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HzBreakPointDao extends BasicDao<HzBreakPoint> {
    /**
     * 查找当前页断点信息数据
     * @return
     */
    List<HzBreakPoint> selectByQueryObject(BreakPointQuery query);

    /**
     * 查找所有断点信息总数据
     * @return
     */
    Long count();
}