package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomLineRecordMapper;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;

public class HzBomLineRecordDaoImpl extends BaseSQLUtil {

    int insert(HzBomLineRecord record){
        return super.insert("HzBomLineRecordDaoImpl_insert",record);
    }
}
