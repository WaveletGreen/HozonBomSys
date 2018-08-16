package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/15 16:26
 * @Modified By:
 */
@Configuration
public class HzVwoInformChangesDaoImpl implements HzVwoInformChangesDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    private static final HzVwoInformChanges CHANGES = new HzVwoInformChanges();

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        CHANGES.setId(id);
        return baseSQLUtil.executeDelete(CHANGES, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.deleteByPrimaryKey");
    }

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzVwoInformChanges record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.insert");
    }

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(HzVwoInformChanges record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.insertSelective");
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInformChanges selectByPrimaryKey(Long id) {
        CHANGES.setId(id);
        return baseSQLUtil.executeQueryById(CHANGES, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.selectByPrimaryKey");
    }

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    @Override
    public List<HzVwoInformChanges> selectByVwoId(Long vwoId) {
        CHANGES.setVwoId(vwoId);
        return baseSQLUtil.executeQuery(CHANGES, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.selectByVwoId");
    }

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(HzVwoInformChanges record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.updateByPrimaryKeySelective");
    }

    /**
     * 主键更新，无选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzVwoInformChanges record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.updateByPrimaryKey");
    }

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    @Override
    public Long tellMeHowManyOfIt(Long vwoId) {
        return baseSQLUtil.executeQueryById(vwoId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzVwoInformChangesDao.tellMeHowManyOfIt");
    }
}
