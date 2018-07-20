package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.HzPreferenceSetting;

import java.util.List;

@Configuration
public class HzPreferenceSettingDaoImpl implements HzPreferenceSettingDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * 根据主键删除1个首选项
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.deleteByPrimaryKey");
    }

    /**
     * 插入1个首选项
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzPreferenceSetting record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.insert");
    }

    /**
     * 根据主键筛选首选项
     *
     * @param puid
     * @return
     */
    @Override
    public HzPreferenceSetting selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzPreferenceSetting(), puid, "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.selectByPrimaryKey", true);
    }

    /**
     * 根据首选项名个主数据查找首选项
     *
     * @param puid
     * @return
     */
    @Override
    public HzPreferenceSetting selectSettingByNameWithMainRecord(HzPreferenceSetting puid) {
        return baseSQLUtil.executeQueryById(puid, "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.selectSettingByNameWithMainRecord");
    }

    /**
     * 更新首选项数据，包括大对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzPreferenceSetting record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.updateByPrimaryKey");
    }

    @Override
    public List<HzPreferenceSetting> selectSettingByTemplateName(HzPreferenceSetting setting) {
        return baseSQLUtil.executeQuery(setting, "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.selectSettingByTemplateName");
    }
}
