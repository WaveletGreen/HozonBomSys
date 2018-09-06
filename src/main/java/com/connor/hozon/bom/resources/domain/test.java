package com.connor.hozon.bom.resources.domain;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzBomLineRecordFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sql.pojo.bom.HzBomLineRecord;

/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description:
 */
public class test {
    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;
    OperateResultMessageRespDTO addHzEbom(AddHzEbomReqDTO reqDTO){
        HzBomLineRecord record = HzBomLineRecordFactory.addHzEbomDTOHzBomLineDO(reqDTO);
        hzBomLineRecordDao.insert(record);
        return OperateResultMessageRespDTO.getSuccessResult();
    }
}
