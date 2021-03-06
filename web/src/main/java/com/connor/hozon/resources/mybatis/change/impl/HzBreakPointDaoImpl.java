//package com.connor.hozon.resources.mybatis.change.impl;
//
//import BasicDaoImpl;
//import cn.net.connor.hozon.common.entity.QueryBase;
//import cn.net.connor.hozon.dao.dao.change.breakpoint.HzBreakPointDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import sql.BaseSQLUtil;
//import cn.net.connor.hozon.dao.pojo.integration.HzBreakPoint;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description:
// * @Date: Created in 2018/9/3 14:02
// * @Modified By:
// */
//@Configuration
//public class HzBreakPointDaoImpl extends BasicDaoImpl<HzBreakPoint> implements HzBreakPointDao {
//    private static final  HzBreakPoint POINT = new HzBreakPoint();
//
//    @Autowired
//    BaseSQLUtil baseSQLUtil;
//
//    public HzBreakPointDaoImpl() {
//        clz = HzBreakPointDao.class;
//    }
//
//    /**
//     * 查找当前页断点信息数据
//     *
//     * @return
//     */
//    @Override
//    public List<HzBreakPoint> selectByQueryObject( QueryBase queryBase) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("param", queryBase);
//        return baseSQLUtil.executeQueryByPass(POINT, params, clz.getCanonicalName() + ".selectByQueryObject");
//    }
//
//    /**
//     * 查找所有断点信息数据（总数量）
//     *
//     * @return
//     */
//    @Override
//    public Long count() {
//        return baseSQLUtil.executeQueryById(new Long(0),clz.getCanonicalName()+".count");
//    }
//
//}
