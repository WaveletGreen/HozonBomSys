package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.resources.mybatis.change.breakpoint.HzBreakPointDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.integration.HzBreakPoint;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/3 14:02
 * @Modified By:
 */
@Configuration
public class HzBreakPointDaoImpl extends BasicDaoImpl<HzBreakPoint> implements HzBreakPointDao {
    private final static HzBreakPoint POINT = new HzBreakPoint();

    public HzBreakPointDaoImpl() {
        clz = HzBreakPointDao.class;
    }

    /**
     * 查找所有断点信息数据
     *
     * @return
     */
    @Override
    public List<HzBreakPoint> selectAll() {
        return baseSQLUtil.executeQuery(POINT, clz.getCanonicalName() + ".selectAll");
    }
}
