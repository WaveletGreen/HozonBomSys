package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao;
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

public class HzVwoInfluenceUserDaoImpl implements HzVwoInfluenceUserDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    private static final HzVwoInfluenceUser USER = new HzVwoInfluenceUser();

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        USER.setId(id);
        return baseSQLUtil.executeDelete(USER, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.deleteByPrimaryKey");
    }

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzVwoInfluenceUser record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.insert");
    }

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(HzVwoInfluenceUser record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.insertSelective");
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
        return baseSQLUtil.executeQueryById(USER, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.selectByPrimaryKey");
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
        return baseSQLUtil.executeQueryById(USER, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.selectByVwoId");
    }

    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(HzVwoInfluenceUser record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.updateByPrimaryKeySelective");
    }

    /**
     * 全更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzVwoInfluenceUser record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceUserDao.updateByPrimaryKey");
    }
}
