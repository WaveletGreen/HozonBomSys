package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import sql.IBaseSQLUtil;
import sql.pojo.bom.HZBomMainRecord;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30 16:12
 *
 * Description:
 *
 * ***********************************************************************************************************************/
public class HzBomMainRecordDaoImpl implements HzBomMainRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public HZBomMainRecord selectByProjectPuid(HZBomMainRecord record) {
        return baseSQLUtil.executeQueryById(record, "");
    }
}
