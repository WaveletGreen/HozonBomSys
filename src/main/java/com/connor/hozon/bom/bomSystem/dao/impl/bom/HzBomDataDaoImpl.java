package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;
import java.util.Map;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 17:35
 */
@Service("hzBomDataDao")
public class HzBomDataDaoImpl implements HzBomDataDao {
    private static final HzBomLineRecord RECORD = new HzBomLineRecord();
    @Autowired
    BaseSQLUtil baseSQLUtil;

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
