package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInfluenceUserDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInfluenceUser;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/20 16:15
 * @Modified By:
 */
@Configuration

public class HzVwoInfluenceUserDaoImpl extends BasicDaoImpl<HzVwoInfluenceUser> implements HzVwoInfluenceUserDao {

    private static final HzVwoInfluenceUser USER = new HzVwoInfluenceUser();

    public HzVwoInfluenceUserDaoImpl() {
        clz = HzVwoInfluenceUserDao.class;
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
        USER.setId(id);
        return baseSQLUtil.executeDelete(USER,
                clzName + ".deleteByPrimaryKey");
    }


    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfluenceUser selectByPrimaryKey(Long id) {
        USER.setId(id);
        return baseSQLUtil.executeQueryById(USER,
                clzName + ".selectByPrimaryKey");
    }

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    @Override
    public HzVwoInfluenceUser selectByVwoId(Long vwoId) {
        USER.setVwoId(vwoId);
        return baseSQLUtil.executeQueryById(USER,
                clzName + ".selectByVwoId");
    }
}
