package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInfluenceDeptDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInfluenceDept;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/20 14:01
 * @Modified By:
 */
@Configuration
public class HzVwoInfluenceDeptDaoImpl extends BasicDaoImpl<HzVwoInfluenceDept> implements HzVwoInfluenceDeptDao {

    private static final HzVwoInfluenceDept INFLUENCE_DEPT = new HzVwoInfluenceDept();

    public HzVwoInfluenceDeptDaoImpl() {
        clz = HzVwoInfluenceDeptDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        INFLUENCE_DEPT.setId(id);
        return baseSQLUtil.executeDelete(INFLUENCE_DEPT,
                clzName + ".deleteByPrimaryKey");
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfluenceDept selectByPrimaryKey(Long id) {
        INFLUENCE_DEPT.setId(id);
        return baseSQLUtil.executeQueryById(INFLUENCE_DEPT,
                clzName + ".selectByPrimaryKey");
    }

    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    @Override
    public HzVwoInfluenceDept selectByVwoId(Long vwoId) {
        INFLUENCE_DEPT.setVwoId(vwoId);
        return baseSQLUtil.executeQueryById(INFLUENCE_DEPT,
                clzName + ".selectByVwoId");
    }


}
