package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao;
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
public class HzVwoInfluenceDeptDaoImpl implements HzVwoInfluenceDeptDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    private static final HzVwoInfluenceDept INFLUENCE_DEPT = new HzVwoInfluenceDept();

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        INFLUENCE_DEPT.setId(id);
        return baseSQLUtil.executeDelete(INFLUENCE_DEPT, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.deleteByPrimaryKey");
    }

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzVwoInfluenceDept record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.insert");
    }

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(HzVwoInfluenceDept record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.insertSelective");
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
        return baseSQLUtil.executeQueryById(INFLUENCE_DEPT, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.selectByPrimaryKey");
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
        return baseSQLUtil.executeQueryById(INFLUENCE_DEPT, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.selectByVwoId");
    }


    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(HzVwoInfluenceDept record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.updateByPrimaryKeySelective");
    }

    /**
     * 主键全更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzVwoInfluenceDept record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao.updateByPrimaryKey");
    }
}
