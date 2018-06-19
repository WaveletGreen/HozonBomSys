package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomLineRecordMapper;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;


@Service("HzBomLineRecordDaoImpl")
public class HzBomLineRecordDaoImpl extends BaseSQLUtil {

    public int insert(HzBomLineRecord record){
        return super.insert("HzBomLineRecordDaoImpl_insert",record);
    }

    public int update(HzBomLineRecord record){
        return super.update("HzBomLineRecordDaoImpl_update",record);
    }
}
