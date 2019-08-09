//package com.connor.hozon.bom.interaction.impl;
//
//import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomColorBean;
//import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomLineBean;
//import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
//import cn.net.connor.hozon.dao.dao.interaction.HzConfigBomColorDao;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author: Fancyears·Maylos·Malvis
// * @Description:
// * @Date: Created in 2018/9/19 12:14
// * @Modified By:
// */
//@Configuration
//public class HzConfigBomColorDaoImpl extends BasicDaoImpl<HzConfigBomColorBean> implements HzConfigBomColorDao {
//
//    private final static HzConfigBomColorBean BEAN = new HzConfigBomColorBean();
//    private static Map<String, Object> params = new HashMap<>();
//
//    public HzConfigBomColorDaoImpl() {
//        clz = HzConfigBomColorDao.class;
//    }
//
//    /**
//     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
//     *
//     * @param bomLineUid 2Y主键
//     * @param projectUid 项目主键
//     * @return
//     */
//    @Override
//    public List<HzConfigBomColorBean> selectBy2YUidWithProject(String bomLineUid, String projectUid) {
//        params.put("bomLineUid", bomLineUid);
//        params.put("projectUid", projectUid);
//        return baseSQLUtil.executeQueryByPass(BEAN, params, clz.getCanonicalName() + ".selectBy2YUidWithProject");
//    }
//
//
//    public List<HzConfigBomColorBean> selectPaintColorSet(String projectId) {
//        return baseSQLUtil.findForList(clz.getCanonicalName() + ".selectPaintColorSet", projectId);
//    }
//
//    @Override
//    public List<HzConfigBomColorBean> selectPaintBomLinePuidFormConfig(String projectId) {
//        return baseSQLUtil.findForList(clz.getCanonicalName() + ".selectPaintBomLinePuidFormConfig", projectId);
//    }
//
//    @Override
//    public List<HzConfigBomLineBean> selectAllConfigToBomline(String projectId) {
//        return baseSQLUtil.findForList(clz.getCanonicalName() + ".selectAllConfigToBomline", projectId);
//    }
//}
