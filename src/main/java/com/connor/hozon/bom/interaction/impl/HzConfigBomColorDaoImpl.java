package com.connor.hozon.bom.interaction.impl;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.interaction.dao.HzConfigBomColorDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/19 12:14
 * @Modified By:
 */
@Configuration
public class HzConfigBomColorDaoImpl extends BasicDaoImpl<HzConfigBomColorBean> implements HzConfigBomColorDao {

    private final static HzConfigBomColorBean BEAN = new HzConfigBomColorBean();
    private static Map<String, Object> params = new HashMap<>();

    public HzConfigBomColorDaoImpl() {
        clz = HzConfigBomColorDao.class;
    }

    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomLineUid    2Y主键
     * @param projectUid 项目主键
     * @return
     */
    @Override
    public List<HzConfigBomColorBean> selectBy2YUidWithProject(String bomLineUid, String projectUid) {
        params.put("bomLineUid", bomLineUid);
        params.put("projectUid", projectUid);
        return baseSQLUtil.executeQueryByPass(BEAN, params, clz.getCanonicalName() + ".selectBy2YUidWithProject");
    }
}
