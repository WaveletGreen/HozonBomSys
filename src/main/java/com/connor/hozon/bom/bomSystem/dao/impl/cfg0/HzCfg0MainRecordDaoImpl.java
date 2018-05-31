package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0MainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0MainRecord;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/31 13:41
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service ("hzCfg0MainRecordDao")
public class HzCfg0MainRecordDaoImpl implements HzCfg0MainRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    @Override
    public HzCfg0MainRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0MainRecord(),puid,"com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0MainRecordDao.selectByPrimaryKey",true);
    }

    @Override
    public HzCfg0MainRecord selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0MainRecord(),projectPuid,"com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0MainRecordDao.selectByProjectPuid",true);
    }
}
