/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzBomDataDao2")
@Configuration
public class HzBomDataDaoImpl extends BasicDaoImpl<HzBomLineRecord> implements HzBomDataDao {
    private static final HzBomLineRecord RECORD = new HzBomLineRecord();

    public HzBomDataDaoImpl() {
        clz = HzBomDataDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public List<HzBomLineRecord> selectByProjectPuid(HzBomLineRecord bomLineRecord) {
        return baseSQLUtil.executeQuery(bomLineRecord, "com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao.selectByProjectPuid");
    }


    @Override
    public HzPreferenceSetting loadSetting(HzPreferenceSetting setting) {
        return baseSQLUtil.executeQueryById(setting,
                "com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao.selectSettingByNameWithMainRecord");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层puid获取到所有的BomLine
     * Date: 2018/5/23 9:59
     *
     * @param projectPuid
     * @return
     */
    @Override
    public List<HzBomLineRecord> select2YByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzBomLineRecord(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao.select2YByProjectPuid");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据部门和2Y层获取到2Y层的下级总成
     * Date: 2018/5/23 9:59
     *
     * @param params 包含部门名称和项目UID
     * @return
     */
    @Override
    public List<HzBomLineRecord> selectVehicleAssembly(Map<String, Object> params) {
        return baseSQLUtil.executeQueryByPass(RECORD, params, "com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao.selectVehicleAssembly");
    }

    /**
     * 查找已经存储好的2级配色方案
     *
     * @param params 包含modelUid
     * @return
     */
    @Override
    public List<HzBomLineRecord> selectVehicleAssembly2(Map<String, Object> params) {
        return baseSQLUtil.executeQueryByPass(RECORD, params, "com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao.selectVehicleAssembly2");
    }

}
