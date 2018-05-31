package com.connor.hozon.bom.bomSystem.dao.bom;

import sql.pojo.bom.HZBomMainRecord;

public interface HzBomMainRecordDao {
    HZBomMainRecord selectByProjectPuid(HZBomMainRecord record);
}