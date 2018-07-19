package com.connor.hozon.bom.bomSystem.dao.bom;

import org.springframework.context.annotation.Configuration;
import sql.pojo.HzPreferenceSetting;

@Configuration
public interface HzPreferenceSettingDao {
    /**
     * 根据主键删除1个首选项
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(String puid);

    /**
     * 插入1个首选项
     *
     * @param record
     * @return
     */
    int insert(HzPreferenceSetting record);

    /**
     * 根据主键筛选首选项
     *
     * @param puid
     * @return
     */
    HzPreferenceSetting selectByPrimaryKey(String puid);

    /**
     * 根据首选项名个主数据查找首选项
     *
     * @param puid
     * @return
     */
    HzPreferenceSetting selectSettingByNameWithMainRecord(HzPreferenceSetting puid);


    /**
     * 更新首选项数据，包括大对象
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzPreferenceSetting record);
}