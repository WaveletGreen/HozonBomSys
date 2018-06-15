package com.connor.hozon.bom.bomSystem.service.bom;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzBomLineRecord;

/**
 * @Auther: haozt
 * @Date: 2018/6/14
 * @Description:
 */
@Service("HzBomLineRecordService")
public class HzBomLineRecordService {
    @Autowired
    private HzBomLineRecordDaoImpl recordDao;
    /**
     * 插入一条数据
     * @param record
     * @return
     */
    public int insert(HzBomLineRecord record){
         return recordDao.insert(record);
    }
}
