package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import cn.net.connor.hozon.common.entity.QueryBase;
import com.connor.hozon.bom.resources.mybatis.change.breakpoint.HzBreakPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.BaseSQLUtil;
import sql.pojo.integration.HzBreakPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/3 14:02
 * @Modified By:
 */
@Configuration
public class HzBreakPointDaoImpl extends BasicDaoImpl<HzBreakPoint> implements HzBreakPointDao {
    private static final  HzBreakPoint POINT = new HzBreakPoint();

    @Autowired
    BaseSQLUtil baseSQLUtil;

    public HzBreakPointDaoImpl() {
        clz = HzBreakPointDao.class;
    }

    /**
     * 查找当前页断点信息数据
     *
     * @return
     */
    @Override
    public List<HzBreakPoint> selectAll( QueryBase queryBase) {
        Map<String, Object> params = new HashMap<>();
       // params.put("pid", pid);
        params.put("whichTable", "HZ_BREAKPOINT");
        params.put("param", queryBase);
        return baseSQLUtil.executeQueryByPass(POINT, params, clz.getCanonicalName() + ".selectAll");
    }

    /**
     * 查找所有断点信息数据（总数量）
     *
     * @return
     */
    @Override
    public Long tellMeHowManyOfIt() {
        return baseSQLUtil.executeQueryById(new Long(0),clz.getCanonicalName()+".tellMeHowManyOfIt");
    }

}
