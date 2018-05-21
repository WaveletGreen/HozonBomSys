package com.connor.hozon.bom.banchOfFancyears.dao.impl.bom;

import com.connor.hozon.bom.banchOfFancyears.dao.bom.HzBomDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.bean.PreferenceSetting;
import sql.BaseSQLUtil;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 17:35
 */
@Service("hzBomDataDao")
public class HzBomDataDaoImpl implements HzBomDataDao {
    @Autowired
    BaseSQLUtil baseSQLUtil;

    @Override
    public List<HzBomLineRecord> selectByBomDigifaxId(HzBomLineRecord bomLineRecord) {
        return baseSQLUtil.executeQuery(bomLineRecord, "com.connor.hozon.bom.banchOfFancyears.dao.bom.HzBomLineRecordMapper.selectByBomDigifaxId");
    }

    @Override
    public HzPreferenceSetting loadSetting(HzPreferenceSetting setting) {
        return baseSQLUtil.executeQueryById(setting,
                "sql.mapper.HzPreferenceSettingMapper.selectSettingByNameWithMainRecord");
    }
}
