package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.bom.HZBomMainRecord;

/**
 * Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30 16:12
 * <p>
 * Description:
 */
@Service("hzBomMainRecordDao")
public class HzBomMainRecordDaoImpl implements HzBomMainRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public HZBomMainRecord selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HZBomMainRecord(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao.selectByProjectPuid", true);
    }

    @Override
    public int insert(HZBomMainRecord hzBomMainRecord) {
        return baseSQLUtil.executeInsert(hzBomMainRecord, "com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao.insert");
    }
}
